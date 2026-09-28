package org.chemthunder.yggdrasil.core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.chemthunder.yggdrasil.core.networking.c2s.SylvaticusDashPayload;
import org.chemthunder.yggdrasil.core.networking.s2c.SpawnImpactFramePayload;
import org.chemthunder.yggdrasil.core.networking.s2c.SpawnScreenflashPayload;

/**
 * @author Chemthunder
 */
public interface YggNetworking {
    static void init() {
        PayloadTypeRegistry.playC2S().register(SylvaticusDashPayload.ID, SylvaticusDashPayload.CODEC);

        PayloadTypeRegistry.playS2C().register(SpawnImpactFramePayload.ID, SpawnImpactFramePayload.CODEC);
        PayloadTypeRegistry.playS2C().register(SpawnScreenflashPayload.ID, SpawnScreenflashPayload.CODEC);
    }

    static void c2s() {
        ServerPlayNetworking.registerGlobalReceiver(SylvaticusDashPayload.ID, new SylvaticusDashPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void s2c() {
        ClientPlayNetworking.registerGlobalReceiver(SpawnImpactFramePayload.ID, new SpawnImpactFramePayload.Receiver());
        ClientPlayNetworking.registerGlobalReceiver(SpawnScreenflashPayload.ID, new SpawnScreenflashPayload.Receiver());
    }
}
