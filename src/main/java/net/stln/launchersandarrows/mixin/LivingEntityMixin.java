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
        addStatusEffectParticle(StatusEffectInit.BURNING, ParticleInit.FLAME_EFFECT);
        addStatusEffectParticle(StatusEffectInit.FREEZE, ParticleInit.FROST_EFFECT);
        addStatusEffectParticle(StatusEffectInit.ELECTRIC_SHOCK, ParticleInit.LIGHTNING_EFFECT);
        addStatusEffectParticle(StatusEffectInit.CORROSION, ParticleInit.ACID_EFFECT);
        addStatusEffectParticle(StatusEffectInit.SUBMERGED, ParticleInit.FLOOD_EFFECT);
        addStatusEffectParticle(StatusEffectInit.CONFUSION, ParticleInit.ECHO_EFFECT);
    }

    @Unique
    private void addStatusEffectParticle(RegistryEntry<StatusEffect> statusEffect, ParticleEffect particleEffect) {
        if (entity.hasStatusEffect(statusEffect)) {
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
}
