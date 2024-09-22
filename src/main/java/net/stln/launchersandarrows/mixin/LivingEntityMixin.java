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
import net.stln.launchersandarrows.LaunchersAndArrows;
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

    @Shadow public abstract void endCombat();

    @Unique
    private static final TrackedData<Boolean> BURNING_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> FREEZE_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> ELECTRIC_SHOCK_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> CORROSION_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> SUBMERGED_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> CONFUSION_FLAG = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    LivingEntity entity = (LivingEntity) (Object) this;

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
        addStatusEffectParticle(StatusEffectInit.BURNING, BURNING_FLAG, ParticleInit.FLAME_EFFECT);
        addStatusEffectParticle(StatusEffectInit.FREEZE, FREEZE_FLAG, ParticleInit.FROST_EFFECT);
        addStatusEffectParticle(StatusEffectInit.ELECTRIC_SHOCK, ELECTRIC_SHOCK_FLAG, ParticleInit.LIGHTNING_EFFECT);
        addStatusEffectParticle(StatusEffectInit.CORROSION, CORROSION_FLAG, ParticleInit.ACID_EFFECT);
        addStatusEffectParticle(StatusEffectInit.SUBMERGED, SUBMERGED_FLAG, ParticleInit.FLOOD_EFFECT);
        addStatusEffectParticle(StatusEffectInit.CONFUSION, CONFUSION_FLAG, ParticleInit.ECHO_EFFECT);
    }

    @Unique
    private void addStatusEffectParticle(RegistryEntry<StatusEffect> statusEffect, TrackedData<Boolean> data, ParticleEffect particleEffect) {
        if (entity.getWorld().isClient && entity.getDataTracker().get(data)) {
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
        } else if (entity.hasStatusEffect(statusEffect)) {
            entity.getDataTracker().set(data, true);
        } else {
            entity.getDataTracker().set(data, false);
        }
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(BURNING_FLAG, false);
        builder.add(FREEZE_FLAG, false);
        builder.add(ELECTRIC_SHOCK_FLAG, false);
        builder.add(CORROSION_FLAG, false);
        builder.add(SUBMERGED_FLAG, false);
        builder.add(CONFUSION_FLAG, false);
    }
}
