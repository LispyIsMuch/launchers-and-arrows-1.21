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
    protected int amplifier;

    protected AccumulationEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
        super(category, color, particleEffect);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getMaxHealth() <= amplifier + 1) {
            applyEffect();
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        this.entity = entity;
        this.amplifier = amplifier;
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        if (entity != null && amplifier > 0) {
            decreaseAmplifier();
        }
    }

    public abstract void decreaseAmplifier();

    public abstract void applyEffect();
}
