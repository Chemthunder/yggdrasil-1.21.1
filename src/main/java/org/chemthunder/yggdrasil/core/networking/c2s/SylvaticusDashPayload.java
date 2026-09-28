package org.chemthunder.yggdrasil.core.networking.c2s;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.chemthunder.yggdrasil.core.Yggdrasil;
import org.chemthunder.yggdrasil.core.cca.entity.SylvaticusComponent;
import org.chemthunder.yggdrasil.core.item.SylvaticusItem;

/**
 * @author Chemthunder
 */
public record SylvaticusDashPayload() implements CustomPayload {
    public static final Id<SylvaticusDashPayload> ID = new Id<>(Yggdrasil.id("sylvaticus_dash"));

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<ByteBuf, SylvaticusDashPayload> CODEC = PacketCodec.unit(new SylvaticusDashPayload());

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SylvaticusDashPayload> {
        public void receive(SylvaticusDashPayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();

            SylvaticusComponent component = SylvaticusComponent.KEY.get(player);
            component.setDashTicks(20);

            if (player.getOffHandStack().getItem() instanceof SylvaticusItem sylvaticusItem) {
                sylvaticusItem.serverOnUse(player.getOffHandStack(), player);
            }
        }
    }
}
