package org.autumn.yggdrasil.core.cca;

import net.minecraft.entity.LivingEntity;
import org.autumn.yggdrasil.core.cca.entity.EnclosedComponent;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class YggdrasilCCA implements WorldComponentInitializer, EntityComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry module) {
        module.register(
                WorldComponent.KEY,
                WorldComponent::new
        );
    }

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry module) {
        module.beginRegistration(
                LivingEntity.class,
                EnclosedComponent.KEY
        ).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(EnclosedComponent::new);

        module.registerForPlayers(
                TrustedComponent.KEY,
                TrustedComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );
    }
}
