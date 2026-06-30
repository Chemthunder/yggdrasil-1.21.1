package org.autumn.yggdrasil.core.cca;

import org.autumn.yggdrasil.core.cca.world.WorldComponent;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class YggdrasilCCA implements WorldComponentInitializer {
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry module) {
        module.register(
                WorldComponent.KEY,
                WorldComponent::new
        );
    }
}
