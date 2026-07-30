package org.autumn.yggdrasil.mixin;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.autumn.yggdrasil.core.cca.entity.LivingEntityComponent;
import org.autumn.yggdrasil.core.index.YggItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = Item.class)
public abstract class ItemMixin {

    @Inject(method = "useOnBlock", at = @At(value = "HEAD"))
    private void yggdrasil$getThings(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            ItemStack stack = player.getMainHandStack();
            ItemStack offStack = player.getOffHandStack();

            if (stack.getItem() instanceof AxeItem && offStack.isOf(Items.GLASS_BOTTLE)) {
                if (context.getWorld().getBlockState(context.getBlockPos()).isIn(ConventionalBlockTags.STRIPPED_LOGS)) {
                    if (LivingEntityComponent.KEY.get(player).isInBox()) {
                        offStack.decrement(1);
                        player.giveItemStack(new ItemStack(YggItems.BOTTLED_SAP));

                        player.swingHand(player.getActiveHand());

                        player.playSound(SoundEvents.BLOCK_HONEY_BLOCK_PLACE);
                    }
                }
            }
        }
    }
}
