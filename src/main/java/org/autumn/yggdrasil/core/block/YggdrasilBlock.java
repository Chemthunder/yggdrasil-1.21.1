package org.autumn.yggdrasil.core.block;

import com.mojang.serialization.MapCodec;
import foundry.veil.platform.VeilEventPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.autumn.yggdrasil.core.block.en.YggdrasilBlockEntity;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.jetbrains.annotations.Nullable;

public class YggdrasilBlock extends BlockWithEntity {
    public static final MapCodec<YggdrasilBlock> CODEC = createCodec(YggdrasilBlock::new);

    public YggdrasilBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new YggdrasilBlockEntity(pos, state);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        WorldComponent c = WorldComponent.KEY.get(world);

        if (c.isPlaced()) {
            world.breakBlock(pos, false);
        } else {
            c.setPlaced(true);
            c.setPos(pos.toCenterPos());
        }
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        WorldComponent c = WorldComponent.KEY.get(world);

        if (c.isPlaced()) {
            if (pos.isWithinDistance(c.getPos(), 15)) {
                c.setPlaced(false);
                c.setPos(Vec3d.ZERO);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return ((world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof YggdrasilBlockEntity y) {
                y.tick(world1, pos, state1);
            }
        });
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
