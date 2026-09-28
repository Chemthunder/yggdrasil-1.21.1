package org.chemthunder.yggdrasil.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.client.event.ImpactFrameEvents;

/**
 * @author Chemthunder
 */
public record SpawnImpactFramePayload() implements CustomPayload {
    public static final Id<SpawnImpactFramePayload> ID = new Id<>(Yggdrasil.id("spawn_impact_frame"));
    public static final PacketCodec<ByteBuf, SpawnImpactFramePayload> CODEC = PacketCodec.unit(new SpawnImpactFramePayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SpawnImpactFramePayload> {
        public void receive(SpawnImpactFramePayload payload, ClientPlayNetworking.Context context) {
//            ImpactFrameEvents.active = true;
//            ImpactFrameEvents.tickAge = 0;
        }
    }
}
