package org.autumn.yggdrasil.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
public class YggToxicationEffectProvider extends FabricCodecDataProvider<ToxicationEffect> {
    public YggToxicationEffectProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, "toxication_effects", ToxicationEffect.CODEC);
    }

    protected void configure(BiConsumer<Identifier, ToxicationEffect> consumer, RegistryWrapper.WrapperLookup wrapperLookup) {
        consumer.accept(
                Yggdrasil.id("torchflower"),
                new ToxicationEffect(
                        Items.TORCHFLOWER,
                        EntityAttributes.GENERIC_SCALE,
                        1.5F,
                        0xFFb8800a
                )
        );

        consumer.accept(
                Yggdrasil.id("wheat"),
                new ToxicationEffect(
                        Items.WHEAT,
                        EntityAttributes.GENERIC_ARMOR,
                        0.5F,
                        0xFFfadb9b
                )
        );

        consumer.accept(
                Yggdrasil.id("gunpowder"),
                new ToxicationEffect(
                        Items.GUNPOWDER,
                        EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE,
                        1.2F,
                        0xFF988c74
                )
        );

        consumer.accept(
                Yggdrasil.id("glow_berries"),
                new ToxicationEffect(
                        Items.GLOW_BERRIES,
                        EntityAttributes.GENERIC_FOLLOW_RANGE,
                        -1.2F,
                        0xFF93ad4a
                )
        );

        consumer.accept(
                Yggdrasil.id("beetroot"),
                new ToxicationEffect(
                        Items.BEETROOT,
                        EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                        1.0F,
                        0xFFad4a70
                )
        );

        consumer.accept(
                Yggdrasil.id("glow_lichen"),
                new ToxicationEffect(
                        Items.GLOW_LICHEN,
                        EntityAttributes.GENERIC_MAX_HEALTH,
                        2.0F,
                        0xFF1f5e39
                )
        );

        consumer.accept(
                Yggdrasil.id("feather"),
                new ToxicationEffect(
                        Items.FEATHER,
                        EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,
                        2.0F,
                        0xFFcde3f5
                )
        );

        consumer.accept(
                Yggdrasil.id("rotten_flesh"),
                new ToxicationEffect(
                        Items.ROTTEN_FLESH,
                        EntityAttributes.GENERIC_STEP_HEIGHT,
                        -0.5F,
                        0xFF623b2a
                )
        );

        consumer.accept(
                Yggdrasil.id("magma_cream"),
                new ToxicationEffect(
                        Items.MAGMA_CREAM,
                        EntityAttributes.GENERIC_BURNING_TIME,
                        -2.0F,
                        0xFFf3c59c
                )
        );

        consumer.accept(
                Yggdrasil.id("sculk_sensor"),
                new ToxicationEffect(
                        Items.SCULK_SENSOR,
                        EntityAttributes.PLAYER_SNEAKING_SPEED,
                        1.0F,
                        0xFF00ff7e
                )
        );

        consumer.accept(
                Yggdrasil.id("honeycomb"),
                new ToxicationEffect(
                        Items.HONEYCOMB,
                        EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,
                        2.5F,
                        0xFFffedcd
                )
        );

        consumer.accept(
                Yggdrasil.id("sweet_berries"),
                new ToxicationEffect(
                        Items.SWEET_BERRIES,
                        EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,
                        -0.5F,
                        0xFFff0068
                )
        );

        consumer.accept(
                Yggdrasil.id("nautilus_shell"),
                new ToxicationEffect(
                        Items.NAUTILUS_SHELL,
                        EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY,
                        2.5F,
                        0xFF50bcff
                )
        );
    }

    public String getName() {
        return "Toxication Effects";
    }
}

