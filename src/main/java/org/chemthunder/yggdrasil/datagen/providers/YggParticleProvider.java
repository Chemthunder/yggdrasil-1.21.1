package org.chemthunder.yggdrasil.datagen.providers;

import net.acoyt.acornlib.data.provider.resources.AcornParticleGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.index.YggParticleTypes;

/**
 * @author Chemthunder
 */
public class YggParticleProvider extends AcornParticleGen {
    public YggParticleProvider(FabricDataOutput output) {
        super(output);
    }

    public void generate(AcornParticleGen.ParticleDataConsumer consumer) {
        consumer.accept(YggParticleTypes.CONSUME, rangeBetween(Yggdrasil.id("consume"), 0, 4));

        consumer.accept(YggParticleTypes.SYLV_DASH, Yggdrasil.id("sylv_dash"));
        consumer.accept(YggParticleTypes.SYLV_BLOCK, Yggdrasil.id("sylv_block"));

        consumer.accept(YggParticleTypes.TAPROOT_EMIT, Yggdrasil.id("taproot_emit"));
    }
}
