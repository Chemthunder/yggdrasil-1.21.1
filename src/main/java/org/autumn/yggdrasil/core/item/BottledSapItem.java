package org.autumn.yggdrasil.core.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.cca.entity.IntoxicatedComponent;
import org.autumn.yggdrasil.core.index.YggComponentTypes;
import org.autumn.yggdrasil.core.index.YggToxicationEffects;

import java.util.List;

/**
 * @author Chemthunder
 */
public class BottledSapItem extends Item {
    public BottledSapItem(Settings settings) {
        super(settings);
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!stack.contains(YggComponentTypes.TOX_EFFECT)) {
            if (clickType == ClickType.RIGHT) {
                if (YggToxicationEffects.findEffectFromItemStack(otherStack.getItem()) != null) {
                    ItemStack toSet = otherStack.split(1);
                    ToxicationEffect effect = YggToxicationEffects.findEffectFromItemStack(toSet);

                    stack.set(YggComponentTypes.TOX_EFFECT, effect);

                    if (player.getWorld().isClient()) {
                        player.playSoundToPlayer(
                                SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE,
                                SoundCategory.PLAYERS,
                                1,
                                1
                        );
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (stack.contains(YggComponentTypes.TOX_EFFECT)) {
            ToxicationEffect effect = stack.get(YggComponentTypes.TOX_EFFECT);

            if (effect != null) {
                if (user instanceof PlayerEntity player) {
                    IntoxicatedComponent tox = IntoxicatedComponent.KEY.get(player);

                    tox.setEffect(effect);
                    tox.setDuration(60);
                    tox.apply();
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.contains(YggComponentTypes.TOX_EFFECT)) {
            ToxicationEffect effect = stack.get(YggComponentTypes.TOX_EFFECT);

            if (effect != null) {
                tooltip.add(Text.literal(effect.source().getName().getString()).withColor(0xFFfff3bd).append(Text.literal(" Mixture").formatted(Formatting.DARK_GRAY)));
                tooltip.add(Text.empty()
                        .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                        .append(Text.translatable(effect.boostedAttribute().value().getTranslationKey()).withColor(0xFFfff3bd))
                );

                if (effect.value() < 0) {
                    tooltip.add(Text.empty()
                            .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal("Lowered by ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal(effect.value() + "").withColor(0xFFfff3bd))
                    );
                } else {
                    tooltip.add(Text.empty()
                            .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal("Raised by ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal(effect.value() + "").withColor(0xFFfff3bd))
                    );
                }
            }
        }
    }
}
