package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {

    @Inject(method = "onEntityHit", at = @At("TAIL"))
    protected void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (!entityHitResult.getEntity().getWorld().isClient) {
            Entity entity = entityHitResult.getEntity();
            if (entity.canFreeze()) {
                entity.setFrozenTicks(Math.min(entity.getFrozenTicks() + 30, entity.getMinFreezeDamageTicks() + 200));
            }
        }
    }
}
