package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import org.autumn.yggdrasil.core.block.en.YggdrasilBlockEntity;

import static org.autumn.yggdrasil.core.Yggdrasil.PROJECT_ID;

public interface YggdrasilBlockEntityTypes {
    BlockEntityTypeRegistrant rant = new BlockEntityTypeRegistrant(PROJECT_ID);

    BlockEntityType<YggdrasilBlockEntity> YGG = rant.register(
            "ygg",
            BlockEntityType.Builder.create(
                    YggdrasilBlockEntity::new,
                    YggdrasilBlocks.YGG
            ).build()
    );

    static void init() {}
}
