package net.stln.launchersandarrows.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.stln.launchersandarrows.particle.ParticleInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ArrowEntity.class)
public abstract class ArrowEffectMixin {

    @Unique
    ArrowEntity arrowEntity = (ArrowEntity) (Object) this;

    @Inject(method = "spawnParticles", at = @At("HEAD"))
    private void spawnParticles(int amount, CallbackInfo ci) {
        if (amount > 0) {
            for (int j = 0; j < amount; j++) {
                arrowEntity.getWorld()
                        .addParticle(
                                ParticleInit.FLAME_EFFECT, arrowEntity.getParticleX(0.25),
                                arrowEntity.getRandomBodyY(), arrowEntity.getParticleZ(0.25),
                                0.0, 0.0, 0.0
                        );
            }
        }
    }

}
