package org.autumn.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.SimpleParticleType;
import org.autumn.yggdrasil.core.Yggdrasil;

/**
 * @author Chemthunder
 */
public interface YggParticleTypes {
    ParticleTypeRegistrant rant = new ParticleTypeRegistrant(Yggdrasil.MOD_ID);

    SimpleParticleType CONSUME = rant.register("consume", FabricParticleTypes.simple());

    static void init() {}

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(CONSUME, EndRodParticle.Factory::new);
    }
}
