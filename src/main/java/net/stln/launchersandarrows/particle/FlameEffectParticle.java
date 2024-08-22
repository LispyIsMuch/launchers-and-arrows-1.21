package net.stln.launchersandarrows.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class FlameEffectParticle extends SpriteBillboardParticle {

    private final SpriteProvider spriteProvider;

    public FlameEffectParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, vx, vy, vz);
        this.maxAge = 4 + this.random.nextInt(6);
        this.scale = 0.15F;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }
    @Override
    public int getBrightness(float tint) {
        int i = this.maxAge / 2;
        return 15728880 -
                (this.age >= i ?
                ((this.age - i) / i * 15728880)
                : 0);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            if (this.age >= this.maxAge * 0.6F) {
                this.alpha = (this.maxAge - this.age) / (this.maxAge * 0.4F);
            }
            this.setSpriteForAge(this.spriteProvider);
        }
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
            return new FlameEffectParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
