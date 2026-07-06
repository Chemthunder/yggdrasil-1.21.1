package org.autumn.yggdrasil.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.autumn.yggdrasil.core.cca.entity.EnclosedComponent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow
    private @Nullable LivingEntity target;

    @WrapMethod(method = "getTarget")
    private LivingEntity a(Operation<LivingEntity> original) {
        if (EnclosedComponent.KEY.get(this).isInBox()) {
            if (this.target instanceof PlayerEntity) {
                return null;
            } else {
                return original.call();
            }
        }
        return original.call();
    }

    @WrapMethod(method = "getTargetInBrain")
    private LivingEntity b(Operation<LivingEntity> original) {
        if (EnclosedComponent.KEY.get(this).isInBox()) {
            if (this.target instanceof PlayerEntity) {
                return null;
            } else {
                return original.call();
            }
        }
        return original.call();
    }
}
