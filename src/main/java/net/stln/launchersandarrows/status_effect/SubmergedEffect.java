package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.util.StatusEffectUtil;

public class SubmergedEffect extends StatusEffect {
    protected SubmergedEffect() {
        super(StatusEffectCategory.HARMFUL, 0x74C6FF, ParticleInit.FLOOD_EFFECT);
    }

    int duration = 0;

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getAir() > 0) {
            entity.setAir(entity.getAir() - ((amplifier + 1) * 5));
        } else {
            entity.setAir(-20);
            if (duration % 40 == 0) {
                entity.damage(entity.getDamageSources().drown(), 1);
            }
        }
        if (entity.hasStatusEffect(StatusEffectInit.FLOOD_ACCUMULATION)) {
            entity.removeStatusEffect(StatusEffectInit.FLOOD_ACCUMULATION);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        this.duration = duration;
        return duration > 0;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        StatusEffectUtil.removeOtherAttributeEffect(entity, 4);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundCategory.PLAYERS, 1.0F, 0.5F);
    }
}
