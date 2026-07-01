package org.autumn.yggdrasil.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class EnclosedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<EnclosedComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("enclosed"),
            EnclosedComponent.class
    );
    private final LivingEntity living;

    private boolean inBox = false;

    public EnclosedComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        WorldComponent w = WorldComponent.KEY.get(living.getWorld());

        if (living.getBlockPos().isWithinDistance(w.getPos(), 75)) {
            if (!inBox) {
                inBox = true;
                sync();
            }
        } else {
            if (inBox) {
                inBox = false;
                sync();
            }
        }

        if (living instanceof PlayerEntity player) {
            player.sendMessage(Text.literal("In Box" + inBox), true);
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        inBox = nbt.getBoolean("InBox");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putBoolean("InBox", inBox);
    }

    public boolean isInBox() {
        return inBox;
    }

    public void setInBox(boolean inBox) {
        this.inBox = inBox;
        sync();
    }
}
