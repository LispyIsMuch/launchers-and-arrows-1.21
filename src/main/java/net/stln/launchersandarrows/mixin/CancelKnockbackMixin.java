package net.stln.launchersandarrows.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.stln.launchersandarrows.entity.projectile.BoltEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class CancelKnockbackMixin {

    @ModifyArg(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"), index = 0)
    private double cancelKnockback(double strength, @Local(argsOnly = true) DamageSource source) {
        if (source.getSource() instanceof BoltEntity) {
            return strength / 10;
        }
        return strength;
    }
}
