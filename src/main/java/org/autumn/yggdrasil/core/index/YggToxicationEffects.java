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
public interface YggToxicationEffects {
    List<ToxicationEffect> effects = new ArrayList<>();

    ToxicationEffect TORCHFLOWER = create(Items.TORCHFLOWER, EntityAttributes.GENERIC_SCALE, 1.5F);
    ToxicationEffect WHEAT = create(Items.WHEAT, EntityAttributes.GENERIC_ARMOR, 0.5F);
    ToxicationEffect SUGAR = create(Items.SUGAR, EntityAttributes.GENERIC_MOVEMENT_SPEED, -1.0F);

    private static ToxicationEffect create(Item source, RegistryEntry<EntityAttribute> boostedAttribute, float value) {
        ToxicationEffect r = new ToxicationEffect(source, boostedAttribute, value);
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
