package org.chemthunder.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.block.TaprootBlock;

/**
 * @author Chemthunder
 */
public interface YggBlocks {
    BlockRegistrant rant = new BlockRegistrant(Yggdrasil.MOD_ID);

    Block TAPROOT = rant.registerWithItem("taproot", TaprootBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
            .sounds(BlockSoundGroup.BAMBOO_SAPLING)
            .luminance((value -> 4))
            .emissiveLighting(((state, world, pos) -> true))
            .ticksRandomly()
    );

    static void init() {}
}
