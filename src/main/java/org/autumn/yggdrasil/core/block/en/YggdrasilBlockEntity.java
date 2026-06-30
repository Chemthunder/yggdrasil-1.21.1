package org.autumn.yggdrasil.core.block.en;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.autumn.yggdrasil.core.index.YggdrasilBlockEntityTypes;

public class YggdrasilBlockEntity extends BlockEntity {
    public YggdrasilBlockEntity(BlockPos pos, BlockState state) {
        super(YggdrasilBlockEntityTypes.YGG, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {

    }
}
