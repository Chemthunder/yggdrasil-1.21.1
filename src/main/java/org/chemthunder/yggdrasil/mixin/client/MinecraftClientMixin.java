package org.chemthunder.yggdrasil.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.player.PlayerEntity;
import org.chemthunder.yggdrasil.core.item.SylvaticusItem;
import org.chemthunder.yggdrasil.core.networking.c2s.SylvaticusDashPayload;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Final public GameOptions options;

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"))
    private void ygg$dash(MinecraftClient instance, boolean breaking, Operation<Void> original) {
        PlayerEntity player = this.player;

        if (player != null) {
            if (this.options.attackKey.isPressed()) {
                if (player.getOffHandStack().getItem() instanceof SylvaticusItem sylvaticusItem) {
                    if (sylvaticusItem.canDash(player)) {
                        ClientPlayNetworking.send(new SylvaticusDashPayload());
                        sylvaticusItem.clientOnUse(player.getOffHandStack(), player);
                    }
                }
            }
        }
        original.call(instance, breaking);
    }
}
