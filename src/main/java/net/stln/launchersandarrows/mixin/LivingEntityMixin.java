package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Unique
    private static final TrackedData<Integer> EFFECT_STATUS =
        DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique
    private static final TrackedData<Integer> AMPLIFIER =
        DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique
    private static int FLAME = 1;
    @Unique
    private static int FROST = 2;
    @Unique
    private static int LIGHTNING = 3;
    @Unique
    private static int ACID = 4;
    @Unique
    private static int FLOOD = 5;
    @Unique
    private static int WAVE = 6;

    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;
    @Unique
    int amplifier = 0;

    @Unique
    private @NotNull ParticleEffect setParticleAndAmplifier(int status, ParticleEffect particleEffect, RegistryEntry<StatusEffect> statusEffect) {
        entity.getDataTracker().set(EFFECT_STATUS, status);

        if (entity.hasStatusEffect(statusEffect)) {
            amplifier = entity.getStatusEffect(statusEffect).getAmplifier();
        }
        return particleEffect;
    }

    @Inject(method = "tickStatusEffects", at = @At("TAIL"))
    private void tickStatusEffects(CallbackInfo ci) {
        ParticleEffect particleEffect = null;
        if (entity.hasStatusEffect(StatusEffectInit.FLAME_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FLAME && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FLAME, ParticleInit.FLAME_EFFECT, StatusEffectInit.FLAME_ACCUMULATION);


        } else if (entity.hasStatusEffect(StatusEffectInit.FROST_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FROST && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FROST, ParticleInit.FROST_EFFECT, StatusEffectInit.FROST_ACCUMULATION);



        } else if (entity.hasStatusEffect(StatusEffectInit.LIGHTNING_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == LIGHTNING && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(LIGHTNING, ParticleInit.LIGHTNING_EFFECT, StatusEffectInit.LIGHTNING_ACCUMULATION);



        } else if (entity.hasStatusEffect(StatusEffectInit.ACID_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == ACID && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(ACID, ParticleInit.ACID_EFFECT, StatusEffectInit.ACID_ACCUMULATION);



        } else if (entity.hasStatusEffect(StatusEffectInit.FLOOD_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FLOOD && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FLOOD, ParticleInit.FLOOD_EFFECT, StatusEffectInit.FLOOD_ACCUMULATION);



        } else if (entity.hasStatusEffect(StatusEffectInit.SHOCK_EXPLOSION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == WAVE && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(WAVE, ParticleInit.WAVE_EFFECT, StatusEffectInit.SHOCK_EXPLOSION);

        } else {
            entity.getDataTracker().set(EFFECT_STATUS, 0);
        }

        if (entity.getWorld().isClient()) {
            amplifier = entity.getDataTracker().get(AMPLIFIER);
        } else {
            entity.getDataTracker().set(AMPLIFIER, amplifier);
        }
        LaunchersAndArrows.LOGGER.info(String.valueOf(amplifier));

//        if (particleEffect != null) {
//            float w = entity.getWidth();
//            float h = entity.getHeight();
//            int entitySize = (int) (w * w * h * (amplifier + 1));
//            for (int i = 0; i < entitySize; i++) {
//                entity.getWorld().addParticle(
//                        particleEffect,
//                        entity.getParticleX(0.5F),
//                        entity.getRandomBodyY(),
//                        entity.getParticleZ(0.5F),
//                        0.0, 0.0, 0.0
//                );
//            }
//        }
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(EFFECT_STATUS, 0);
        builder.add(AMPLIFIER, 0);
    }
}
