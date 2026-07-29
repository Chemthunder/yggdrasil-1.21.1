package org.autumn.yggdrasil.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import org.autumn.yggdrasil.core.event.YggRenderEvent;
import org.autumn.yggdrasil.core.index.YggModelLayers;
import org.autumn.yggdrasil.core.index.YggParticleTypes;

public class YggdrasilClient implements ClientModInitializer {
    public static int GLOBAL_AGE = 0;

    public void onInitializeClient() {
        YggModelLayers.clientInit();
        YggParticleTypes.clientInit();

        WorldRenderEvents.LAST.register(new YggRenderEvent());

        ClientTickEvents.START_CLIENT_TICK.register(minecraftClient -> GLOBAL_AGE++);
    }
}
