package org.autumn.yggdrasil.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.jetbrains.annotations.Nullable;

public class HeartBlock extends Block {
    public HeartBlock(Settings settings) {
        super(settings);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer instanceof PlayerEntity player) {
            WorldComponent w = WorldComponent.KEY.get(world);
            TrustedComponent t = TrustedComponent.KEY.get(player);

            w.setPos(pos.toCenterPos());
            w.reset(true);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        WorldComponent w = WorldComponent.KEY.get(world);
        w.setBurning(true);
        super.onBroken(world, pos, state);
    }

    public static int getLuminance(BlockState state) {
        return 5;
    }
}
