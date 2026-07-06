package org.autumn.yggdrasil.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.autumn.yggdrasil.core.cca.entity.EnclosedComponent;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static org.autumn.yggdrasil.core.Yggdrasil.id;

@Mixin(value = InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Nullable protected abstract PlayerEntity getCameraPlayer();

    @WrapOperation(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud$HeartType;getTexture(ZZZ)Lnet/minecraft/util/Identifier;"))
    private Identifier customHearts(InGameHud.HeartType instance, boolean hardcore, boolean half, boolean blinking, Operation<Identifier> original) {
        PlayerEntity player = this.getCameraPlayer();

        if (player != null) {
            if (EnclosedComponent.KEY.get(player).isInBox() || TrustedComponent.KEY.get(player).isTrusted()) {
                if (instance != InGameHud.HeartType.CONTAINER && instance != InGameHud.HeartType.ABSORBING && instance != InGameHud.HeartType.POISONED && instance != InGameHud.HeartType.WITHERED) {
                    if (blinking) {
                        if (half) {
                            return id("hud/heart/half_blinking");
                        } else {
                            return id("hud/heart/full_blinking");
                        }
                    } else {
                        if (half) {
                            return id("hud/heart/half");
                        } else {
                            return id("hud/heart/full");
                        }
                    }
                }
            }
        }
        return original.call(instance, hardcore, half, blinking);
    }
}