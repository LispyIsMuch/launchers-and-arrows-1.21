package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.stln.launchersandarrows.item.ItemInit;
import net.stln.launchersandarrows.item.ModItemTags;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import net.stln.launchersandarrows.status_effect.util.StatusEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.entity.projectile.AbstractWindChargeEntity.EXPLOSION_BEHAVIOR;

@Mixin(ArrowEntity.class)
public abstract class ArrowEffectMixin {

    @Unique
    private int inGroundTime = 0;

    @Unique
    private static final TrackedData<ItemStack> ITEM_STACK =
            DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    @Unique
    ArrowEntity arrowEntity = (ArrowEntity) (Object) this;
    @Unique
    ItemStack itemStack = ItemStack.EMPTY;

    @Unique
    private ParticleEffect getparticleEffect() {
        if (itemStack.isOf(ItemInit.FLAME_ARROW)) return ParticleInit.FLAME_EFFECT;
         else if (itemStack.isOf(ItemInit.FREEZING_ARROW)) return ParticleInit.FROST_EFFECT;
         else if (itemStack.isOf(ItemInit.LIGHTNING_ARROW)) return ParticleInit.LIGHTNING_EFFECT;
         else if (itemStack.isOf(ItemInit.CORROSIVE_ARROW)) return ParticleInit.ACID_EFFECT;
         else if (itemStack.isOf(ItemInit.FLOOD_ARROW)) return ParticleInit.FLOOD_EFFECT;
         else if (itemStack.isOf(ItemInit.REVERBERATING_ARROW)) return ParticleInit.ECHO_EFFECT;
         else if (itemStack.isOf(ItemInit.WAVE_ARROW)) return ParticleInit.WAVE_EFFECT;
        return null;
    }

    @Unique
    private void generateWindExplosion() {
        Vec3d pos = arrowEntity.getPos();
        arrowEntity.getWorld().createExplosion(null, null,
                EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(),
                2.0F, false, World.ExplosionSourceType.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
        arrowEntity.kill();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!arrowEntity.getWorld().isClient()) {
            arrowEntity.getDataTracker().set(ITEM_STACK, arrowEntity.getItemStack());
        }
        itemStack = arrowEntity.getDataTracker().get(ITEM_STACK);
        if (itemStack.isOf(ItemInit.WAVE_ARROW)) {

            NbtCompound nbt = new NbtCompound();
            arrowEntity.writeCustomDataToNbt(nbt);
            if (nbt.getBoolean("inGround")) {
                inGroundTime++;
            } else {
                inGroundTime = 0;
            }
            if (inGroundTime > 50) {
                generateWindExplosion();
            }
        }
    }


    @Inject(method = "spawnParticles", at = @At("HEAD"))
    private void spawnParticles(int amount, CallbackInfo ci) {
        if (itemStack.isIn(ModItemTags.ARROWS_WITH_EFFECT) && !itemStack.isOf(ItemInit.PIERCING_ARROW)) {
            if (amount > 0) {
                NbtCompound nbt = new NbtCompound();
                arrowEntity.writeCustomDataToNbt(nbt);
                for (int j = 0; j < amount; j++) {
                    if (!nbt.getBoolean("inGround") || arrowEntity.getRandom().nextFloat() >= 0.65F) {
                        arrowEntity.getWorld()
                                .addParticle(
                                        getparticleEffect(), arrowEntity.getParticleX(0.25),
                                        arrowEntity.getY() - 0.125 + (arrowEntity.getRandom().nextFloat() / 4),
                                        arrowEntity.getParticleZ(0.25),
                                        0.0, 0.0, 0.0
                                );
                    }
                }
            }
        }
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(LivingEntity target, CallbackInfo ci) {
        if (itemStack.isIn(ModItemTags.ARROWS_WITH_EFFECT)) {
            if (itemStack.isOf(ItemInit.FLAME_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.FLAME_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.FREEZING_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.FROST_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.LIGHTNING_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.LIGHTNING_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.CORROSIVE_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.ACID_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.FLOOD_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.FLOOD_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.REVERBERATING_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.ECHO_ACCUMULATION, 20, 15));
            }
            else if (itemStack.isOf(ItemInit.WAVE_ARROW)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffectInit.SHOCK_EXPLOSION, 50, 0));
            }
            else if (itemStack.isOf(ItemInit.PIERCING_ARROW)) {
                StatusEffectUtil.stackStatusEffect(target, new StatusEffectInstance(StatusEffectInit.SERIOUS_INJURY, 100, 5));
            }
        }
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(ITEM_STACK, new ItemStack(Items.ARROW));
    }
}
