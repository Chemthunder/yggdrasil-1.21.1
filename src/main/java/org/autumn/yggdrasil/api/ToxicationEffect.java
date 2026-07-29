package org.autumn.yggdrasil.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

/**
 * @author Chemthunder
 */
public record ToxicationEffect(Item source, RegistryEntry<EntityAttribute> boostedAttribute, float value) {
    public static final Codec<ToxicationEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registries.ITEM.getCodec().fieldOf("source").forGetter(ToxicationEffect::source),
            EntityAttribute.CODEC.fieldOf("boostedAttribute").forGetter(ToxicationEffect::boostedAttribute),
            Codec.FLOAT.fieldOf("value").forGetter(ToxicationEffect::value)
    ).apply(instance, ToxicationEffect::new));

    public static final PacketCodec<ByteBuf, ToxicationEffect> PACKET_CODEC = PacketCodecs.codec(CODEC);
}
