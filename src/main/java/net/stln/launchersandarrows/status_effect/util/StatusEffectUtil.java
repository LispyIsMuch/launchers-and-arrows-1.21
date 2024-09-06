package net.stln.launchersandarrows.status_effect.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.stln.launchersandarrows.item.util.AttributeEffectsDictionary;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import net.stln.launchersandarrows.util.AttributeEnum;

public class StatusEffectUtil {
    public static void stackStatusEffect(LivingEntity target, StatusEffectInstance effectInstance) {
        StatusEffectInstance targetEffect = target.getStatusEffect(effectInstance.getEffectType());
        int amplifier = -1;
        if (targetEffect != null) {
            amplifier = targetEffect.getAmplifier();
        }
        if (effectInstance.getAmplifier() >= 0) {
            target.addStatusEffect(new StatusEffectInstance(effectInstance.getEffectType(),
                    effectInstance.getDuration(), amplifier + effectInstance.getAmplifier() + 1));
        }
    }

    public static void applyAttributeEffect(LivingEntity entity, ItemStack stack) {
            Integer[] attributes = new Integer[7];
            Item item = stack.getItem();
            for (int i = 0; i < 7; i++) {
                attributes[i] = AttributeEffectsDictionary.getAttributeEffect(item, i);
            }
            if (attributes[AttributeEnum.FLAME.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLAME_ACCUMULATION, 20, attributes[AttributeEnum.FLAME.get()] - 1));
            }
            if (attributes[AttributeEnum.FROST.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FROST_ACCUMULATION, 20, attributes[AttributeEnum.FROST.get()] - 1));
            }
            if (attributes[AttributeEnum.LIGHTNING.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.LIGHTNING_ACCUMULATION, 20, attributes[AttributeEnum.LIGHTNING.get()] - 1));
            }
            if (attributes[AttributeEnum.ACID.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ACID_ACCUMULATION, 20, attributes[AttributeEnum.ACID.get()] - 1));
            }
            if (attributes[AttributeEnum.FLOOD.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLOOD_ACCUMULATION, 20, attributes[AttributeEnum.FLOOD.get()] - 1));
            }
            if (attributes[AttributeEnum.ECHO.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ECHO_ACCUMULATION, 20, attributes[AttributeEnum.ECHO.get()] - 1));
            }
            if (attributes[AttributeEnum.INJURY.get()] != null) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.SERIOUS_INJURY, 100, attributes[AttributeEnum.INJURY.get()] - 1));
            }
    }

    public static void applyAttributeModifier(LivingEntity entity, Integer[] data) {
            if (data[AttributeEnum.FLAME.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLAME_ACCUMULATION, 20, data[AttributeEnum.FLAME.get()] - 1));
            }
            if (data[AttributeEnum.FROST.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FROST_ACCUMULATION, 20, data[AttributeEnum.FROST.get()] - 1));
            }
            if (data[AttributeEnum.LIGHTNING.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.LIGHTNING_ACCUMULATION, 20, data[AttributeEnum.LIGHTNING.get()] - 1));
            }
            if (data[AttributeEnum.ACID.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ACID_ACCUMULATION, 20, data[AttributeEnum.ACID.get()] - 1));
            }
            if (data[AttributeEnum.FLOOD.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLOOD_ACCUMULATION, 20, data[AttributeEnum.FLOOD.get()] - 1));
            }
            if (data[AttributeEnum.ECHO.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ECHO_ACCUMULATION, 20, data[AttributeEnum.ECHO.get()] - 1));
            }
            if (data[AttributeEnum.INJURY.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.SERIOUS_INJURY, 100, data[AttributeEnum.INJURY.get()] - 1));
            }
    }

    public static void applyAttributeRatioModifier(LivingEntity entity, ItemStack stack, Integer[] data) {
        Integer[] attributes = new Integer[7];
        Item item = stack.getItem();
        for (int i = 0; i < 7; i++) {
            attributes[i] = AttributeEffectsDictionary.getAttributeEffect(item, i);
        }
            if (attributes[AttributeEnum.FLAME.get()] != null && data[AttributeEnum.FLAME.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLAME_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.FLAME.get()] * (data[AttributeEnum.FLAME.get()] / 100.0F) - 1)));
            }
            if (attributes[AttributeEnum.FROST.get()] != null && data[AttributeEnum.FROST.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FROST_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.FROST.get()] * (data[AttributeEnum.FROST.get()] / 100.0F) - 1)));
            }
            if (attributes[AttributeEnum.LIGHTNING.get()] != null && data[AttributeEnum.LIGHTNING.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.LIGHTNING_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.LIGHTNING.get()] * (data[AttributeEnum.LIGHTNING.get()] / 100.0F) - 1)));
            }
            if (attributes[AttributeEnum.ACID.get()] != null && data[AttributeEnum.ACID.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ACID_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.ACID.get()] * (data[AttributeEnum.ACID.get()] / 100.0F) - 1)));
            }
            if (attributes[AttributeEnum.FLOOD.get()] != null && data[AttributeEnum.FLOOD.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.FLOOD_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.FLOOD.get()] * (data[AttributeEnum.FLOOD.get()] / 100.0F) - 1)));
            }
            if (attributes[AttributeEnum.ECHO.get()] != null && data[AttributeEnum.ECHO.get()] > 0) {
                StatusEffectUtil.stackStatusEffect(entity, new StatusEffectInstance(StatusEffectInit.ECHO_ACCUMULATION, 20, Math.round(attributes[AttributeEnum.ECHO.get()] * (data[AttributeEnum.ECHO.get()] / 100.0F) - 1)));
            }
    }
}
