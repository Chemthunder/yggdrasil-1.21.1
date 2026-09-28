package org.chemthunder.yggdrasil.core.cca;

import net.minecraft.entity.LivingEntity;
import org.chemthunder.yggdrasil.core.cca.entity.BoxComponent;
import org.chemthunder.yggdrasil.core.cca.entity.IntoxicatedComponent;
import org.chemthunder.yggdrasil.core.cca.entity.SylvaticusComponent;
import org.chemthunder.yggdrasil.core.cca.entity.TrustedComponent;
import org.chemthunder.yggdrasil.core.cca.world.TaprootComponent;
import org.chemthunder.yggdrasil.core.cca.world.YggdrasilComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class YggdrasilCCA implements WorldComponentInitializer, EntityComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry module) {
        module.register(
                YggdrasilComponent.KEY,
                YggdrasilComponent::new
        );

        module.register(
                TaprootComponent.KEY,
                TaprootComponent::new
        );
    }

    public void registerEntityComponentFactories(EntityComponentFactoryRegistry module) {
        module.beginRegistration(
                LivingEntity.class,
                BoxComponent.KEY
        ).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(BoxComponent::new);

        module.registerForPlayers(
                TrustedComponent.KEY,
                TrustedComponent::new,
                RespawnCopyStrategy.ALWAYS_COPY
        );

        module.registerForPlayers(
                IntoxicatedComponent.KEY,
                IntoxicatedComponent::new,
                RespawnCopyStrategy.NEVER_COPY
        );

        module.registerForPlayers(SylvaticusComponent.KEY, SylvaticusComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
