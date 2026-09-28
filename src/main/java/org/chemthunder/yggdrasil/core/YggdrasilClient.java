package org.chemthunder.yggdrasil.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.chemthunder.yggdrasil.api.ToxicationEffect;
import org.chemthunder.yggdrasil.core.client.event.ImpactFrameEvents;
import org.chemthunder.yggdrasil.core.client.event.ScreenflashEvents;
import org.chemthunder.yggdrasil.core.client.event.TaprootRenderEvent;
import org.chemthunder.yggdrasil.core.client.event.YggRenderEvent;
import org.chemthunder.yggdrasil.core.index.YggComponentTypes;
import org.chemthunder.yggdrasil.core.index.YggItems;
import org.chemthunder.yggdrasil.core.index.YggModelLayers;
import org.chemthunder.yggdrasil.core.index.YggParticleTypes;
import org.chemthunder.yggdrasil.core.networking.YggNetworking;

public class YggdrasilClient implements ClientModInitializer {
    public static int GLOBAL_AGE = 0;

    public void onInitializeClient() {
        YggModelLayers.clientInit();
        YggParticleTypes.clientInit();

        YggNetworking.s2c();

        WorldRenderEvents.LAST.register(new YggRenderEvent());
        WorldRenderEvents.LAST.register(new TaprootRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> GLOBAL_AGE++);

        ImpactFrameEvents.init();
        ScreenflashEvents.init();

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
