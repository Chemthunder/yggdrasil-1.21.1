package org.autumn.yggdrasil.core.cca;

import net.minecraft.entity.LivingEntity;
import org.autumn.yggdrasil.core.cca.entity.IntoxicatedComponent;
import org.autumn.yggdrasil.core.cca.entity.LivingEntityComponent;
import org.autumn.yggdrasil.core.cca.entity.PlayerComponent;
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
                LivingEntityComponent.KEY
        ).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LivingEntityComponent::new);

        module.registerForPlayers(
                PlayerComponent.KEY,
                PlayerComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );

        module.registerForPlayers(
                IntoxicatedComponent.KEY,
                IntoxicatedComponent::new,
                RespawnCopyStrategy.NEVER_COPY
        );
    }
}
