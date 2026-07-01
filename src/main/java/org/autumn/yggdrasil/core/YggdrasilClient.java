package org.autumn.yggdrasil.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.autumn.yggdrasil.core.event.YggRenderEvent;
import org.autumn.yggdrasil.core.index.ModModelLayers;

public class YggdrasilClient implements ClientModInitializer {
    public void onInitializeClient() {
        ModModelLayers.clientInit();

        WorldRenderEvents.LAST.register(new YggRenderEvent());
    }
}
