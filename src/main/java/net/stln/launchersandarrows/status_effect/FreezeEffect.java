package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.stln.launchersandarrows.particle.ParticleInit;

public class FreezeEffect extends StatusEffect {
    protected FreezeEffect() {
        super(StatusEffectCategory.HARMFUL, 0x89FEFF, ParticleInit.FROST_EFFECT);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getFrozenTicks() <= entity.getMinFreezeDamageTicks() + 20) {
            entity.setFrozenTicks(Math.min(entity.getFrozenTicks() + ((amplifier + 1) * 10), entity.getMinFreezeDamageTicks() + 20));
        }
        if (entity.hasStatusEffect(StatusEffectInit.FROST_ACCUMULATION)) {
            entity.removeStatusEffect(StatusEffectInit.FROST_ACCUMULATION);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration > 0;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 1.0F, 2.0F);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
