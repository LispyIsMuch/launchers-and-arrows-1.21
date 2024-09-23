package net.stln.launchersandarrows.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.projectile.BoltEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PersistentProjectileEntity.class})
public class DeleteBoltKnockbackMixin {

    @Shadow private double damage;
    @Unique
    PersistentProjectileEntity entity = (PersistentProjectileEntity) (Object) this;

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void cancelKnockback(LivingEntity target, DamageSource source, CallbackInfo ci) {
        if (entity instanceof BoltEntity) {
            ci.cancel();
        }
    }
}
