package org.autumn.yggdrasil.core.cca.world;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class WorldComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<WorldComponent> KEY = ComponentRegistry.getOrCreate(
            Yggdrasil.id("world"),
            WorldComponent.class
    );
    private final World world;

    private static final float MAX_RADIUS = 50.0F;
    private static final float radiusA = 0.1F;

    private int age = 0;
    private int delayToExpand = (5 * 20);

    private float radius = 0.0F;

    private boolean placed = false;
    private boolean burning = false;
    private boolean cracked = false;

    private Vec3d pos = Vec3d.ZERO;

    public WorldComponent(World world) {
        this.world = world;
    }

    public void tick() {
        if (placed) {
            age++;

            if (delayToExpand > 0) {
                delayToExpand--;
                if (delayToExpand == 0) {
                    sync();
                }
            }

            if (delayToExpand <= 0) {
                if (radius < MAX_RADIUS) {
                    radius += radiusA;

                    world.getPlayers().forEach(capture -> {
                        if (capture instanceof ScreenShaker shaker) {
                            shaker.addScreenShake(1.2F, 40);
                        }
                    });
                }
            }
        }
    }

    public void reset(boolean skip) {
        radius = 0.0F;
        if (!skip) delayToExpand = (5 * 20);
        age = 0;
        placed = true;

        sync();
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        pos = new Vec3d(
                nbt.getDouble("X"),
                nbt.getDouble("Y"),
                nbt.getDouble("Z")
        );

        age = nbt.getInt("Age");
        delayToExpand = nbt.getInt("DToExpand");

        radius = nbt.getFloat("Radius");

        placed = nbt.getBoolean("Placed");
        burning = nbt.getBoolean("Burning");
        cracked = nbt.getBoolean("Cracked");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putDouble("X", pos.x);
        nbt.putDouble("Y", pos.y);
        nbt.putDouble("Z", pos.z);

        nbt.putInt("Age", age);
        nbt.putInt("DToExpand", delayToExpand);

        nbt.putFloat("Radius", radius);

        nbt.putBoolean("Placed", placed);
        nbt.putBoolean("Burning", burning);
        nbt.putBoolean("Cracked", cracked);
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

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        sync();
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
        sync();
    }

    public boolean isCracked() {
        return cracked;
    }

    public void setCracked(boolean cracked) {
        this.cracked = cracked;
        sync();
    }
}
