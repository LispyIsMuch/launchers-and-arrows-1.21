package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.HoverEvent;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.util.StatusEffectUtil;

public class ElectricShockEffect extends StatusEffect {
    protected ElectricShockEffect() {
        super(StatusEffectCategory.HARMFUL, 0x4C5CFF, ParticleInit.LIGHTNING_EFFECT);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.PLAYERS, 1.0F, 2.0F);
        entity.damage(entity.getDamageSources().lightningBolt(), amplifier + 1);
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 9));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20, 9));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20, 9));
        if (entity.hasStatusEffect(StatusEffectInit.LIGHTNING_ACCUMULATION)) {
            entity.removeStatusEffect(StatusEffectInit.LIGHTNING_ACCUMULATION);
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
        StatusEffectUtil.removeOtherAttributeEffect(entity, 2);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.PLAYERS, 1.0F, 2.0F);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 1.0F, 2.0F);
    }
}
