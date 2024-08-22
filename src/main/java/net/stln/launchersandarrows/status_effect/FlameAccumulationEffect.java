package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.stln.launchersandarrows.particle.ParticleInit;

public class FlameAccumulationEffect extends StatusEffect {
    protected FlameAccumulationEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFF0000, ParticleInit.FLAME_EFFECT);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        float w = entity.getWidth();
        float h = entity.getHeight();
        int entitySize = (int) (w * w * h);
        for (int i = 0; i < entitySize * 10; i++) {
            entity.getWorld().addParticle(
                    ParticleInit.FLAME_EFFECT,
                    entity.getParticleX(1.0F),
                    entity.getRandomBodyY(),
                    entity.getParticleZ(1.0F),
                    0.0, 0.0, 0.0
            );
        }
        return true;
    }
}
