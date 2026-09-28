package org.chemthunder.yggdrasil.core.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.particle.SimpleParticleType;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.client.particle.SylvDashParticle;
import org.chemthunder.yggdrasil.core.client.particle.TaprootEmitParticle;

/**
 * @author Chemthunder
 */
public interface YggParticleTypes {
    ParticleTypeRegistrant rant = new ParticleTypeRegistrant(Yggdrasil.MOD_ID);

    SimpleParticleType CONSUME = rant.register("consume", FabricParticleTypes.simple());

    SimpleParticleType SYLV_DASH = rant.register("sylv_dash", FabricParticleTypes.simple(true));
    SimpleParticleType SYLV_BLOCK = rant.register("sylv_block", FabricParticleTypes.simple(true));

    SimpleParticleType TAPROOT_EMIT = rant.register("taproot_emit", FabricParticleTypes.simple(true));

    static void init() {}

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(CONSUME, EndRodParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(SYLV_DASH, SylvDashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SYLV_BLOCK, SylvDashParticle.Factory::new);

        ParticleFactoryRegistry.getInstance().register(TAPROOT_EMIT, TaprootEmitParticle.Factory::new);
    }
}
