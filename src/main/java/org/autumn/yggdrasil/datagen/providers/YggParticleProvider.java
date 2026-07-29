package org.autumn.yggdrasil.datagen.providers;

import net.acoyt.acornlib.data.provider.resources.AcornParticleProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.YggdrasilClient;
import org.autumn.yggdrasil.core.index.YggParticleTypes;

/**
 * @author Chemthunder
 */
public class YggParticleProvider extends AcornParticleProvider {
    public YggParticleProvider(FabricDataOutput output) {
        super(output);
    }

    public void generate(ParticleDataConsumer consumer) {
        consumer.accept(YggParticleTypes.CONSUME, rangeBetween(Yggdrasil.id("consume"), 0, 4));
    }
}
