package org.autumn.yggdrasil.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.event.YggRenderEvent;
import org.autumn.yggdrasil.core.index.YggComponentTypes;
import org.autumn.yggdrasil.core.index.YggItems;
import org.autumn.yggdrasil.core.index.YggModelLayers;
import org.autumn.yggdrasil.core.index.YggParticleTypes;

public class YggdrasilClient implements ClientModInitializer {
    public static int GLOBAL_AGE = 0;

    public void onInitializeClient() {
        YggModelLayers.clientInit();
        YggParticleTypes.clientInit();

        WorldRenderEvents.LAST.register(new YggRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> GLOBAL_AGE++);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (stack.contains(YggComponentTypes.TOX_EFFECT)) {
                ToxicationEffect effect = stack.get(YggComponentTypes.TOX_EFFECT);

                if (effect != null) {
                    if (tintIndex == 1) {
                        return effect.color();
                    }
                }
            }

            return -1;
        }, YggItems.BOTTLED_SAP);
    }
}
