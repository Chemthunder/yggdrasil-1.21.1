package org.autumn.yggdrasil.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import org.autumn.yggdrasil.core.cca.entity.EnclosedComponent;
import org.autumn.yggdrasil.core.cca.entity.TrustedComponent;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@SuppressWarnings("ConstantValue")
@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {

    @WrapMethod(method = "damage")
    private boolean deny(DamageSource source, float amount, Operation<Boolean> original) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (source.getAttacker() instanceof LivingEntity living) {
            if (EnclosedComponent.KEY.get(living).isInBox()) {
                if (self.getWorld() instanceof ServerWorld serverWorld) {
                    if (living instanceof PlayerEntity) {
                        serverWorld.spawnParticles(
                                ParticleTypes.END_ROD,
                                self.getX(),
                                self.getY() + 0.5F,
                                self.getZ(),
                                9,
                                0.1F,
                                self.getHeight() / 2.0F,
                                0.1F,
                                0.03F
                        );
                    }
                }
                return false;
            }
        }
        return original.call(source, amount);
    }

    @WrapMethod(method = "getMovementSpeed()F")
    private float sprintless(Operation<Float> original) {
        if (EnclosedComponent.KEY.get(this).isInBox()) {
            if (!((Object) this instanceof PlayerEntity player && TrustedComponent.KEY.get(player).isTrusted())) {
                return original.call() / 2;
            }
        }
        return original.call();
    }
}
