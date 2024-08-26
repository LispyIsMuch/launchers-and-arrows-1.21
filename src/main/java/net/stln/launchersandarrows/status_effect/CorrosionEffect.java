package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.stln.launchersandarrows.particle.ParticleInit;

public class CorrosionEffect extends StatusEffect {
    protected CorrosionEffect() {
        super(StatusEffectCategory.HARMFUL, 0xA3FF4C, ParticleInit.ACID_EFFECT);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().magic(), amplifier + 1);
        if (entity.hasStatusEffect(StatusEffectInit.ACID_ACCUMULATION)) {
            entity.removeStatusEffect(StatusEffectInit.ACID_ACCUMULATION);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 40 == 0 && duration > 0;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, 0.5F);
    }
}
