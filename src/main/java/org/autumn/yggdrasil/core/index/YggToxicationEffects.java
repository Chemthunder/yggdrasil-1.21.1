package org.autumn.yggdrasil.core.index;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public interface YggToxicationEffects {
    List<ToxicationEffect> effects = new ArrayList<>();

    ToxicationEffect TORCHFLOWER = create(Items.TORCHFLOWER, EntityAttributes.GENERIC_SCALE, 1.5F, 0xFFb8800a);
    ToxicationEffect WHEAT = create(Items.WHEAT, EntityAttributes.GENERIC_ARMOR, 0.5F, 0xFFfadb9b);
    ToxicationEffect GUNPOWDER = create(Items.GUNPOWDER, EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE, 1.2F, 0xFF988c74);
    ToxicationEffect GLOW_BERRIES = create(Items.GLOW_BERRIES, EntityAttributes.GENERIC_FOLLOW_RANGE, -1.2F, 0xFF93ad4a);
    ToxicationEffect BEETROOT = create(Items.BEETROOT, EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1.0F, 0xFFad4a70);
    ToxicationEffect GLOW_LICHEN = create(Items.GLOW_LICHEN, EntityAttributes.GENERIC_MAX_HEALTH, 2.0F, 0xFF1f5e39);
    ToxicationEffect FEATHER = create(Items.FEATHER, EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 2.0F, 0xFFcde3f5);
    ToxicationEffect ROTTEN_FLESH = create(Items.ROTTEN_FLESH, EntityAttributes.GENERIC_STEP_HEIGHT, -0.5F, 0xFF623b2a);
    ToxicationEffect MAGMA_CREAM = create(Items.MAGMA_CREAM, EntityAttributes.GENERIC_BURNING_TIME, -2.0F, 0xFFf3c59c);
    ToxicationEffect SCULK_SENSOR = create(Items.SCULK_SENSOR, EntityAttributes.PLAYER_SNEAKING_SPEED, 1.0F, 0xFF00ff7e);

    private static ToxicationEffect create(Item source, RegistryEntry<EntityAttribute> boostedAttribute, float value, int color) {
        ToxicationEffect r = new ToxicationEffect(source, boostedAttribute, value, color);
        effects.add(r);
        return r;
    }

    static void init() {}

    @Nullable
    static ToxicationEffect findEffectFromItemStack(ItemStack stack) {
        Item item = stack.getItem();

        for (ToxicationEffect effect : effects) {
            if (effect.source() == item) {
                return effect;
            }
        }
        return null;
    }

    @Nullable
    static ToxicationEffect findEffectFromItemStack(Item item) {
        for (ToxicationEffect effect : effects) {
            if (effect.source() == item) {
                return effect;
            }
        }
        return null;
    }
}
