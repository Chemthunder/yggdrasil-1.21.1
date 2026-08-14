package org.autumn.yggdrasil.datagen.providers;

import net.acoyt.acornlib.data.provider.resources.AcornParticleGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.index.YggParticleTypes;

/**
 * @author Chemthunder
 */
public class YggParticleProvider extends AcornParticleGen {
    public YggParticleProvider(FabricDataOutput output) {
        super(output);
    }

    public void generate(AcornParticleGen.ParticleDataConsumer consumer) {
        consumer.accept(YggParticleTypes.CONSUME, rangeBetween(Yggdrasil.id("consume"), 0, 4));
    }
}
