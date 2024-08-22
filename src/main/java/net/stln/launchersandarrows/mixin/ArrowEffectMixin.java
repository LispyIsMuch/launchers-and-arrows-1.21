package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.stln.launchersandarrows.item.ItemInit;
import net.stln.launchersandarrows.item.ModItemTags;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntity.class)
public abstract class ArrowEffectMixin {

    @Unique
    private static final TrackedData<ItemStack> ITEM_STACK =
            DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    @Unique
    ArrowEntity arrowEntity = (ArrowEntity) (Object) this;
    @Unique
    ItemStack itemStack = ItemStack.EMPTY;

    private ParticleEffect getparticleEffect() {
        if (itemStack.isOf(ItemInit.FLAME_ARROW)) return ParticleInit.FLAME_EFFECT;
        // else if (arrowEntity.getItemStack().isOf(ItemInit.FLAME_ARROW)) particleType = ParticleInit.FLAME_EFFECT;
        return null;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!arrowEntity.getWorld().isClient()) {
            arrowEntity.getDataTracker().set(ITEM_STACK, arrowEntity.getItemStack());
        }
        itemStack = arrowEntity.getDataTracker().get(ITEM_STACK);
    }


    @Inject(method = "spawnParticles", at = @At("HEAD"))
    private void spawnParticles(int amount, CallbackInfo ci) {
        if (itemStack.isIn(ModItemTags.ARROWS_WITH_EFFECT)) {
            if (amount > 0) {
                for (int j = 0; j < amount; j++) {
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

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(LivingEntity target, CallbackInfo ci) {
        if (itemStack.isIn(ModItemTags.ARROWS_WITH_EFFECT)) {
            if (itemStack.isOf(ItemInit.FLAME_ARROW)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffectInit.FLAME_ACCUMULATION, 10, 0));
            }
            if (itemStack.isOf(ItemInit.FREEZING_ARROW)) {
                target.setFrozenTicks(target.getMinFreezeDamageTicks() + 200);
            }
        }
    }


        @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(ITEM_STACK, new ItemStack(Items.ARROW));
    }
}
