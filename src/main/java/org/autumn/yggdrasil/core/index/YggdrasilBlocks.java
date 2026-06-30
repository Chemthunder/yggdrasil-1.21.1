package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import org.autumn.yggdrasil.core.block.YggdrasilBlock;

import static org.autumn.yggdrasil.core.Yggdrasil.PROJECT_ID;

public interface YggdrasilBlocks {
    BlockRegistrant rant = new BlockRegistrant(PROJECT_ID);

    Block YGG = rant.registerWithItem("yggdrasil", YggdrasilBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
            .dropsNothing()
            .sounds(BlockSoundGroup.BIG_DRIPLEAF)
    );

    static void init() {}
}
