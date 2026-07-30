package org.autumn.yggdrasil.core.cca.entity;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.index.YggParticleTypes;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class IntoxicatedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<IntoxicatedComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("intoxicated"),
            IntoxicatedComponent.class
    );
    private final PlayerEntity player;

    private static final Identifier ATTRIBUTE_MODIFIER_ID = Yggdrasil.id("toxication_effect_modifier");

    private @Nullable ToxicationEffect effect = null;
    private int duration = 0;

    public IntoxicatedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        if (duration > 0) {
            duration--;
            if (duration == 0) {
                if (effect != null) {
                    EntityAttributeInstance ins = player.getAttributeInstance(effect.boostedAttribute());
                    if (ins != null) ins.removeModifier(ATTRIBUTE_MODIFIER_ID);

                    effect = null;
                }
                sync();
            }

            if (player.age % 4 == 0) {
                if (player.getWorld() instanceof ServerWorld serverWorld) {
                    serverWorld.spawnParticles(
                            YggParticleTypes.CONSUME,
                            player.getX(),
                            player.getY() + 1.0F,
                            player.getZ(),
                            2,
                            0.2F,
                            0.5F,
                            0.2F,
                            0.03F
                    );
                }
            }
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (nbt.contains("Effect", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbt.getCompound("Effect");
            effect = ToxicationEffect.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Yggdrasil.LOGGER::error).orElseThrow();
        } else {
            effect = null;
        }
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (effect != null) {
            nbt.put("Effect", ToxicationEffect.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), effect).getOrThrow());
        }
    }

    public @Nullable ToxicationEffect getEffect() {
        return effect;
    }

    public void setEffect(ToxicationEffect effect) {
        this.effect = effect;
        sync();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        sync();
    }

    public void apply() {
        if (effect != null) {
            EntityAttributeInstance ins = player.getAttributeInstance(effect.boostedAttribute());

            if (ins != null) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(
                        ATTRIBUTE_MODIFIER_ID,
                        effect.value(),
                        EntityAttributeModifier.Operation.ADD_VALUE
                );

                ins.addTemporaryModifier(modifier);
            }
        } else {
            Yggdrasil.LOGGER.info("Attempting to apply null attribute!");
        }
    }
}
