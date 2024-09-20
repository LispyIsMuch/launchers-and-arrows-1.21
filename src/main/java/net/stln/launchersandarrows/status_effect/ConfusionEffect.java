package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.util.StatusEffectUtil;

import java.util.List;

public class ConfusionEffect extends StatusEffect {
    protected ConfusionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x00A6B0, ParticleInit.ECHO_EFFECT);
    }

    int duration = 0;
    float yaw = 0;
    float pitch = 0;

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (duration % 20 == 0) {
                this.yaw = playerEntity.getRandom().nextFloat() - 0.5F;
                this.pitch = playerEntity.getRandom().nextFloat() - 0.5F;
            }
            playerEntity.setYaw(playerEntity.getYaw() + (yaw * (amplifier + 1) * 10));
            playerEntity.setPitch(playerEntity.getPitch() + (pitch * (amplifier + 1) * 10));
        } else if (entity instanceof MobEntity mobEntity) {
            List<Entity> list = mobEntity.getWorld().getOtherEntities(mobEntity,
                    new Box(mobEntity.getX() - 7, mobEntity.getY() - 7, mobEntity.getZ() - 7,
                            mobEntity.getX() + 7, mobEntity.getY() + 7, mobEntity.getZ() + 7),
                    EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
            List<LivingEntity> livingEntities = new java.util.ArrayList<>(List.of());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof LivingEntity livingEntity) {
                    livingEntities.add(livingEntity);
                }
            }
            if (!livingEntities.isEmpty()) {
                mobEntity.setTarget(livingEntities.get(mobEntity.getRandom().nextInt(livingEntities.size())));
            }
        }
        if (entity.hasStatusEffect(StatusEffectInit.ECHO_ACCUMULATION)) {
            entity.removeStatusEffect(StatusEffectInit.ECHO_ACCUMULATION);
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        this.duration = duration;
        return duration > 0;
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        StatusEffectUtil.removeOtherAttributeEffect(entity, 5);
        entity.getWorld().playSound(entity, entity.getBlockPos(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
