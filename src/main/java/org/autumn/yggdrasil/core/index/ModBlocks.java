package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import org.autumn.yggdrasil.core.block.HeartBlock;

import static org.autumn.yggdrasil.core.Yggdrasil.PROJECT_ID;

public interface ModBlocks {
    BlockRegistrant rant = new BlockRegistrant(PROJECT_ID);

    Block HEART = rant.registerWithItem("heart_of_yggdrasil", HeartBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
            .sounds(BlockSoundGroup.WOOD)
            .dropsNothing()
            .emissiveLighting((state, world, pos) -> true)
            .luminance(HeartBlock::getLuminance)
    );

    static void init() {}
}
