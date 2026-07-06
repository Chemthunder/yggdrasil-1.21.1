package org.autumn.yggdrasil.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.autumn.yggdrasil.core.index.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockItem.class)
public abstract class BlockItemMixin {

    @WrapMethod(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;")
    private ActionResult denyPlace(ItemPlacementContext context, Operation<ActionResult> original) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null) {
            if (stack.isOf(ModBlocks.HEART.asItem())) {
                if (TrustedComponent.KEY.get(player).isTrusted()) {
                    return original.call(context);
                } else {
                    return ActionResult.FAIL;
                }
            } else {
                return original.call(context);
            }
        }
        return original.call(context);
    }

    @WrapMethod(method = "place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z")
    private boolean denyPlace0(ItemPlacementContext context, BlockState state, Operation<Boolean> original) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null) {
            if (stack.isOf(ModBlocks.HEART.asItem())) {
                if (TrustedComponent.KEY.get(player).isTrusted()) {
                    return original.call(context, state);
                } else {
                    return false;
                }
            } else {
                return original.call(context, state);
            }
        }
        return original.call(context, state);
    }
}
