package org.autumn.yggdrasil.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.autumn.yggdrasil.core.event.AltModRenderer;
import org.autumn.yggdrasil.core.index.YggdrasilModelLayers;

public class YggdrasilClient implements ClientModInitializer {
    public void onInitializeClient() {
        YggdrasilModelLayers.clientInit();

        WorldRenderEvents.LAST.register(new AltModRenderer());
    }
}
