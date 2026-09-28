package org.chemthunder.yggdrasil.core.networking.s2c;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.client.event.ScreenflashEvents;

/**
 * @author Chemthunder
 */
public record SpawnScreenflashPayload(float opacity, int color) implements CustomPayload {
    public static final Id<SpawnScreenflashPayload> ID = new Id<>(Yggdrasil.id("spawn_screenflash"));

    public static final PacketCodec<ByteBuf, SpawnScreenflashPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, SpawnScreenflashPayload::opacity,
            PacketCodecs.INTEGER, SpawnScreenflashPayload::color,
            SpawnScreenflashPayload::new
    );

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SpawnScreenflashPayload> {
        public void receive(SpawnScreenflashPayload payload, ClientPlayNetworking.Context context) {
            ScreenflashEvents.opacity = payload.opacity;
            ScreenflashEvents.lastColor = payload.color;
        }
    }
}
