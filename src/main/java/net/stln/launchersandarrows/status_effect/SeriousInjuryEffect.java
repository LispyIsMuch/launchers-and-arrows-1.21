package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.stln.launchersandarrows.particle.ParticleInit;

public class SeriousInjuryEffect extends StatusEffect {

    protected LivingEntity entity;
    protected int amplifier = 0;
    protected boolean remove = false;

    protected SeriousInjuryEffect() {
        super(StatusEffectCategory.HARMFUL, 0x800000);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        this.amplifier = amplifier;
        this.remove = false;
        if (entity.isSprinting()) {
            entity.setSprinting(false);
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


    public void decreaseAmplifier() {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffectInit.SERIOUS_INJURY, 100, amplifier - 1));
    }
}
