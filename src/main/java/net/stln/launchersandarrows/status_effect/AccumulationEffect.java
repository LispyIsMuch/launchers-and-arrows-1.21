package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.stln.launchersandarrows.particle.ParticleInit;

public abstract class AccumulationEffect extends StatusEffect {

    protected LivingEntity entity;
    protected int amplifier = 0;
    protected boolean remove = false;

    protected AccumulationEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
        super(category, color, particleEffect);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (amplifier + 1 > Math.sqrt(entity.getMaxHealth()) * 5) {
            amplifier = 0;
            this.remove = true;
            applyEffect();
        } else {
            this.amplifier = amplifier;
            this.remove = false;
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        this.entity = entity;
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        if (entity != null && amplifier > 0 && !remove) {
            decreaseAmplifier();
        }
    }

    public abstract void decreaseAmplifier();

    public abstract void applyEffect();
}
