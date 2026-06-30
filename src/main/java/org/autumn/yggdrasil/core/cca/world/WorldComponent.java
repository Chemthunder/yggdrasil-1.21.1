package org.autumn.yggdrasil.core.cca.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class WorldComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<WorldComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("world"),
            WorldComponent.class
    );
    private final World world;

    private int age = 0;

    private boolean placed = false;
    private Vec3d pos = Vec3d.ZERO;

    public WorldComponent(World world) {
        this.world = world;
    }

    public void tick() {
        age++;
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (nbt.contains("Pos", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbt.getCompound("Pos");
            pos = Vec3d.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Yggdrasil.LOGGER::error).orElse(Vec3d.ZERO);
        } else {
            pos = Vec3d.ZERO;
        }

        age = nbt.getInt("Age");
        placed = nbt.getBoolean("Placed");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        if (pos != null) {
            nbt.put("Pos", Vec3d.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), pos).getOrThrow());
        }

        nbt.putInt("Age", age);
        nbt.putBoolean("Placed", placed);
    }

    public Vec3d getPos() {
        return pos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
        sync();
    }

    public BlockPos getBPos() {
        return new BlockPos.Mutable(
                pos.x,
                pos.y,
                pos.z
        );
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
        sync();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        sync();
    }
}
