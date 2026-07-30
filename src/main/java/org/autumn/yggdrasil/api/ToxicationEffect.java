package org.autumn.yggdrasil.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * @author Chemthunder
 */
public record ToxicationEffect(Item source, RegistryEntry<EntityAttribute> boostedAttribute, float value, int color) {
    public static final Codec<ToxicationEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registries.ITEM.getCodec().fieldOf("source").forGetter(ToxicationEffect::source),
            EntityAttribute.CODEC.fieldOf("boostedAttribute").forGetter(ToxicationEffect::boostedAttribute),
            Codec.FLOAT.fieldOf("value").forGetter(ToxicationEffect::value),
            Codec.INT.fieldOf("color").forGetter(ToxicationEffect::color)
    ).apply(instance, ToxicationEffect::new));

    public static final PacketCodec<ByteBuf, ToxicationEffect> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
