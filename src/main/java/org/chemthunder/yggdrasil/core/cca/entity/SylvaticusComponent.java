package org.chemthunder.yggdrasil.core.cca.entity;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.index.YggParticleTypes;
import org.chemthunder.yggdrasil.core.networking.s2c.SpawnImpactFramePayload;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class SylvaticusComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SylvaticusComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("sylvaticus"),
            SylvaticusComponent.class
    );
    private final PlayerEntity player;

    private int dashTicks = 0;

    public SylvaticusComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        World world = player.getWorld();

        if (dashTicks > 0) {
            dashTicks--;
            if (dashTicks == 0) {
                sync();
            }
        }

        if (dashTicks > 0) {
            Box detect = new Box(player.getBlockPos()).expand(1.5F);

            for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, detect, entity -> entity != player)) {
                living.damage(living.getDamageSources().generic(), 3.0F);

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    ServerPlayNetworking.send(serverPlayer, new SpawnImpactFramePayload());
                }
            }
        }

        if (dashTicks > 0) {
            for (int i = 0; i < 2; i++) {
                Random random = new Random();

                world.addParticle(
                        YggParticleTypes.SYLV_DASH,
                        player.getX() + random.nextFloat(-0.5F, 0.5F),
                        player.getY() + random.nextFloat(0.0F, 1.0F),
                        player.getZ() + random.nextFloat(-0.5F, 0.5F),
                        0,
                        0,
                        0
                );
            }

            player.setVelocity(player.getRotationVec(0).multiply(2));
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        dashTicks = nbt.getInt("DashTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("DashTicks", dashTicks);
    }

    public int getDashTicks() {
        return dashTicks;
    }

    public void setDashTicks(int dashTicks) {
        this.dashTicks = dashTicks;
        sync();
    }
}
