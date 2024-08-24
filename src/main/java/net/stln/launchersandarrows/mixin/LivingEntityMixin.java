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
    private @NotNull ParticleEffect setParticleAndAmplifier(int status, ParticleEffect particleEffect, RegistryEntry<StatusEffect> statusEffect) {
        entity.getDataTracker().set(EFFECT_STATUS, status);
        return particleEffect;
    }

    @Inject(method = "tickStatusEffects", at = @At("TAIL"))
    private void tickStatusEffects(CallbackInfo ci) {
        ParticleEffect particleEffect = null;
        if (entity.hasStatusEffect(StatusEffectInit.BURNING)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FLAME && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FLAME, ParticleInit.FLAME_EFFECT, StatusEffectInit.BURNING);


        } else if (entity.hasStatusEffect(StatusEffectInit.FREEZE)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FROST && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FROST, ParticleInit.FROST_EFFECT, StatusEffectInit.FREEZE);



        } else if (entity.hasStatusEffect(StatusEffectInit.ELECTRICK_SHOCK)
                || (entity.getDataTracker().get(EFFECT_STATUS) == LIGHTNING && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(LIGHTNING, ParticleInit.LIGHTNING_EFFECT, StatusEffectInit.ELECTRICK_SHOCK);



        } else if (entity.hasStatusEffect(StatusEffectInit.CORROSION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == ACID && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(ACID, ParticleInit.ACID_EFFECT, StatusEffectInit.CORROSION);



        } else if (entity.hasStatusEffect(StatusEffectInit.FLOOD_ACCUMULATION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FLOOD && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FLOOD, ParticleInit.FLOOD_EFFECT, StatusEffectInit.FLOOD_ACCUMULATION);



        } else if (entity.hasStatusEffect(StatusEffectInit.SHOCK_EXPLOSION)
                || (entity.getDataTracker().get(EFFECT_STATUS) == WAVE && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(WAVE, ParticleInit.WAVE_EFFECT, StatusEffectInit.SHOCK_EXPLOSION);

        } else {
            entity.getDataTracker().set(EFFECT_STATUS, 0);
        }

        if (particleEffect != null) {
            float w = entity.getWidth();
            float h = entity.getHeight();
            int entitySize = (int) (w * h * 10);
            for (int i = 0; i < entitySize; i++) {
                entity.getWorld().addParticle(
                        particleEffect,
                        entity.getParticleX(0.5F),
                        entity.getRandomBodyY(),
                        entity.getParticleZ(0.5F),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(EFFECT_STATUS, 0);
    }
}
