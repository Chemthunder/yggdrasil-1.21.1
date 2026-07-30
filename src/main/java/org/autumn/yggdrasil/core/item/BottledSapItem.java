package org.autumn.yggdrasil.core.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.autumn.yggdrasil.api.ToxicationEffect;
import org.autumn.yggdrasil.core.Yggdrasil;
import org.autumn.yggdrasil.core.cca.entity.IntoxicatedComponent;
import org.autumn.yggdrasil.core.index.YggComponentTypes;
import org.autumn.yggdrasil.core.util.ToxicationEffectResourceReloadListener;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Chemthunder
 */
public class BottledSapItem extends Item implements ModelVaryingItem {
    public BottledSapItem(Settings settings) {
        super(settings);
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (!stack.contains(YggComponentTypes.TOX_EFFECT)) {
            if (clickType == ClickType.RIGHT) {
                if (ToxicationEffectResourceReloadListener.findEffectFromItemStack(otherStack.getItem()) != null) {
                    ItemStack toSet = otherStack.split(1);
                    ToxicationEffect effect = ToxicationEffectResourceReloadListener.findEffectFromItemStack(toSet);

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

                    if (tox.getDuration() <= 0) {
                        tox.setEffect(effect);
                        tox.setDuration((30 * 20));
                        tox.apply();
                    }

                    player.giveItemStack(new ItemStack(Items.GLASS));
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
                tooltip.add(Text.literal(effect.source().getName().getString()).withColor(effect.color()).append(Text.literal(" Mixture").formatted(Formatting.DARK_GRAY)));
                tooltip.add(Text.empty()
                        .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                        .append(Text.translatable(effect.boostedAttribute().value().getTranslationKey()).withColor(effect.color()))
                );

                if (effect.value() < 0) {
                    tooltip.add(Text.empty()
                            .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal("Lowered by ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal(Math.abs(effect.value()) + "").withColor(effect.color()))
                    );
                } else {
                    tooltip.add(Text.empty()
                            .append(Text.literal("- ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal("Raised by ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal(Math.abs(effect.value()) + "").withColor(effect.color()))
                    );
                }
            }
        }
    }

    public Text getName(ItemStack stack) {
        if (stack.contains(YggComponentTypes.TOX_EFFECT)) {
            ToxicationEffect effect = stack.get(YggComponentTypes.TOX_EFFECT);

            if (effect != null) {
                return Text.literal("Sap Mixture").withColor(effect.color());
            } else {
                return super.getName(stack);
            }
        } else {
            return super.getName(stack);
        }
    }

    public Identifier getModel(ModelTransformationMode modelTransformationMode, ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        if (itemStack.contains(YggComponentTypes.TOX_EFFECT)) {
            ToxicationEffect effect = itemStack.get(YggComponentTypes.TOX_EFFECT);

            if (effect != null) {
                return Yggdrasil.id("sap_mixture");
            }
        }
        return Yggdrasil.id("bottled_sap");
    }

    public List<Identifier> getModelsToLoad() {
        return List.of(
                Yggdrasil.id("bottled_smp"),
                Yggdrasil.id("sap_mixture")
        );
    }
}
