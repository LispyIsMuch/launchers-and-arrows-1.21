package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.stln.launchersandarrows.particle.ParticleInit;

public class FrostAccumulationEffect extends AccumulationEffect {
    protected FrostAccumulationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x00FFFF, ParticleInit.FROST_EFFECT);
    }

    @Override
    public void decreaseAmplifier() {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffectInit.FROST_ACCUMULATION, 20, amplifier - 1));
    }
}
