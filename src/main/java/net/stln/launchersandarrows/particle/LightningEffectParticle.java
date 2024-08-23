package net.stln.launchersandarrows.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class LightningEffectParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public LightningEffectParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, vx, vy, vz);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.maxAge = 4 + this.random.nextInt(6);
        this.scale = 0.15F;
        this.gravityStrength = 0.0F;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }
    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.velocityY = this.velocityY - 0.04 * (double)this.gravityStrength;
        }

        this.velocityX = this.velocityX * (double)this.velocityMultiplier;
        this.velocityY = this.velocityY * (double)this.velocityMultiplier;
        this.velocityZ = this.velocityZ * (double)this.velocityMultiplier;

        if (this.age >= this.maxAge * 0.6F) {
                this.alpha = (this.maxAge - this.age) / (this.maxAge * 0.4F);
            }
            this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new LightningEffectParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
