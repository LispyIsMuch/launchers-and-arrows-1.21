package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.stln.launchersandarrows.particle.ParticleInit;

import static net.minecraft.entity.projectile.AbstractWindChargeEntity.EXPLOSION_BEHAVIOR;

public class ShockExplosionEffect extends StatusEffect {
    protected ShockExplosionEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFFFFFF, ParticleInit.WAVE_EFFECT);
    }

    LivingEntity entity;
    int amplifier;

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        super.canApplyUpdateEffect(duration, amplifier);
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        this.entity = entity;
        this.amplifier = amplifier;
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        super.onRemoved(attributeContainer);
        generateWindExplosion();
    }

    @Override
    public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        super.onEntityRemoval(entity, amplifier, reason);
        if (entity != null) {
            generateWindExplosion();
        }
    }

    private void generateWindExplosion() {
        entity.getWorld().createExplosion(null, null,
                EXPLOSION_BEHAVIOR, entity.getParticleX(0.5F), entity.getRandomBodyY() - 0.1F, entity.getParticleZ(0.5F),
                this.amplifier * 2 + 2, false, World.ExplosionSourceType.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
    }
}
