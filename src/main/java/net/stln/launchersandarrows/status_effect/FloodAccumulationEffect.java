package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.stln.launchersandarrows.particle.ParticleInit;

public class FloodAccumulationEffect extends AccumulationEffect {
    protected FloodAccumulationEffect() {
        super(StatusEffectCategory.HARMFUL, 0x0080FF, ParticleInit.FLOOD_EFFECT);
    }

    @Override
    public void decreaseAmplifier() {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffectInit.FLOOD_ACCUMULATION, 20, amplifier - 1));
    }

    @Override
    public void applyEffect() {
        entity.removeStatusEffect(StatusEffectInit.FLOOD_ACCUMULATION);
    }
}
