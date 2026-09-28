package org.chemthunder.yggdrasil.core.cca.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Box;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.cca.world.YggdrasilComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class BoxComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<BoxComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("enclosed"),
            BoxComponent.class
    );
    private final LivingEntity living;

    private boolean inBox = false;

    public BoxComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        YggdrasilComponent w = YggdrasilComponent.KEY.get(living.getWorld());

        if (w.isPlaced()) {
            Box collider = new Box(w.getBPos()).expand(w.getRadius());

            if (collider.contains(living.getPos())) {
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
        } else {
            if (inBox) {
                inBox = false;
                sync();
            }
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
