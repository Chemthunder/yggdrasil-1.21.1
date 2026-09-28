package org.chemthunder.yggdrasil.core.cca.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * @author Chemthunder
 */
public class TaprootComponent implements AutoSyncedComponent {
    public static final ComponentKey<TaprootComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("taproot"),
            TaprootComponent.class
    );
    private final World world;

    private @Nullable Vec3d pos = null;
    private boolean active = false;

    public TaprootComponent(World world) {
        this.world = world;
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        active = nbt.getBoolean("Active");

        pos = new Vec3d(
                nbt.getDouble("X"),
                nbt.getDouble("Y"),
                nbt.getDouble("Z")
        );
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putBoolean("Active", active);

        if (this.pos != null) {
            nbt.putDouble("X", pos.x);
            nbt.putDouble("Y", pos.y);
            nbt.putDouble("Z", pos.z);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        sync();
    }

    @Nullable
    public Vec3d getPos() {
        return pos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
        sync();
    }
}
