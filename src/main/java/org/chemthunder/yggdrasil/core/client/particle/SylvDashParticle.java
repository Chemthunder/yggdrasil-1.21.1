package org.chemthunder.yggdrasil.core.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

/**
 * @author Chemthunder
 */
public class SylvDashParticle extends AnimatedParticle {
    public SylvDashParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.01F);

        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;

        this.scale = 0.5F;

        this.angle = world.random.nextBetween(10, 360);
        this.prevAngle = this.angle;

        this.setMaxAge(4000000);
        this.setSpriteForAge(spriteProvider);
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();

        if (this.scale > 0) {
            this.scale -= 0.015F;
        }

        if (this.alpha > 0) {
            this.alpha -= 0.015F;
        }

        if (this.scale <= 0) {
            this.markDead();
        }

        this.prevAngle = this.angle;
        this.angle += 0.4F;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType parameters, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SylvDashParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        }
    }
}
