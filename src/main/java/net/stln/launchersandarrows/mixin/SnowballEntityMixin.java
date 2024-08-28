package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import net.stln.launchersandarrows.status_effect.util.StatusEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {

    @Inject(method = "onEntityHit", at = @At("TAIL"))
    protected void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (!entityHitResult.getEntity().getWorld().isClient) {
            if (entityHitResult.getEntity() instanceof LivingEntity livingEntity) {
                StatusEffectUtil.applyAttributeEffect(livingEntity, Items.SNOWBALL.getDefaultStack());
            }
        }
    }
}
