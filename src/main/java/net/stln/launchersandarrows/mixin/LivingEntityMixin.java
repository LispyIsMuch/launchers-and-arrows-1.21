package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Vec3d;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract Vec3d applyMovementInput(Vec3d movementInput, float slipperiness);

    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow @Nullable private DamageSource lastDamageSource;
    @Shadow @Nullable private LivingEntity attacker;

    @Shadow public abstract void damageArmor(DamageSource source, float amount);

    @Shadow public abstract boolean isFallFlying();

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

    @Unique
    DamageSource damageSource = null;

    @Inject(method = "damage", at = @At("HEAD"))
    private void getDamageSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        damageSource = source;
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0)
    private float modifyDamage(float damage) {
        if (entity.hasStatusEffect(StatusEffectInit.CORROSION) && !damageSource.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
            damage *= (float) (entity.getStatusEffect(StatusEffectInit.CORROSION).getAmplifier() + 3) / 2;
        }
        if (damageSource.getAttacker() != null && damageSource.getAttacker() instanceof LivingEntity attacker) {
            if (attacker.hasStatusEffect(StatusEffectInit.SUBMERGED)) {
                damage /= (attacker.getStatusEffect(StatusEffectInit.SUBMERGED).getAmplifier() + 2);
            }
        }
        if (entity.hasStatusEffect(StatusEffectInit.SERIOUS_INJURY)) {
            damage += (float) entity.getStatusEffect(StatusEffectInit.SERIOUS_INJURY).getAmplifier() / 4 + 1;
            entity.removeStatusEffect(StatusEffectInit.SERIOUS_INJURY);
        }
        return damage;
    }

    @ModifyVariable(method = "heal", at = @At("HEAD"), ordinal = 0)
    private float modifyHeal(float amount) {
        if (entity.hasStatusEffect(StatusEffectInit.SERIOUS_INJURY)) {
            return amount * (1 - ((float) (entity.getStatusEffect(StatusEffectInit.SERIOUS_INJURY).getAmplifier() + 1) / (entity.getStatusEffect(StatusEffectInit.SERIOUS_INJURY).getAmplifier() + 3)));
        }
        return amount;
    }

    @Inject(method = "getNextAirOnLand", at = @At("HEAD"), cancellable = true)
    private void checkHasSubmergedEffect(int air, CallbackInfoReturnable<Integer> cir) {
        if (entity.hasStatusEffect(StatusEffectInit.SUBMERGED)) {
            cir.setReturnValue(air);
        }
    }

    @ModifyArg(method = "applyArmorToDamage", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/entity/damage/DamageSource;FF)F"),
            index = 3)
    private float getArmorLeft(float armor) {
        if (entity.hasStatusEffect(StatusEffectInit.CORROSION)) {
            return armor * (1 - ((float) (entity.getStatusEffect(StatusEffectInit.CORROSION).getAmplifier() + 1) / (entity.getStatusEffect(StatusEffectInit.CORROSION).getAmplifier() + 3)));
        }
        return armor;
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



        } else if (entity.hasStatusEffect(StatusEffectInit.SUBMERGED)
                || (entity.getDataTracker().get(EFFECT_STATUS) == FLOOD && entity.getWorld().isClient())) {
            particleEffect = setParticleAndAmplifier(FLOOD, ParticleInit.FLOOD_EFFECT, StatusEffectInit.SUBMERGED);



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
