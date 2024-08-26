package net.stln.launchersandarrows.status_effect.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class StatusEffectUtil {
    public static void stackStatusEffect(LivingEntity target, StatusEffectInstance effectInstance) {
        StatusEffectInstance targetEffect = target.getStatusEffect(effectInstance.getEffectType());
        int amplifier = -1;
        if (targetEffect != null) {
            amplifier = targetEffect.getAmplifier();
        }
        target.addStatusEffect(new StatusEffectInstance(effectInstance.getEffectType(),
                effectInstance.getDuration(),  amplifier + effectInstance.getAmplifier() + 1));
    }
}
