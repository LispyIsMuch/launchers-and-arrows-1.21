package net.stln.launchersandarrows.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.item.FovModifierItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class FovMixin {

	@Inject(at = @At("HEAD"), method = "getFovMultiplier", cancellable = true)
	private void injected(CallbackInfoReturnable<Float> cir) {
			AbstractClientPlayerEntity playerEntity = MinecraftClient.getInstance().player;
			if (playerEntity != null) {
				ItemStack itemStack = playerEntity.getMainHandStack();
				if (itemStack.getItem() instanceof FovModifierItem) {
					FovModifierItem item = (FovModifierItem) itemStack.getItem();
					float fov = item.getFov();
					item.resetFov();
					if (playerEntity.getAbilities().flying) {
						fov *= 1.1F;
					}

					fov *= ((float)playerEntity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / playerEntity.getAbilities().getWalkSpeed() + 1.0F) / 2.0F;
					if (playerEntity.getAbilities().getWalkSpeed() == 0.0F || Float.isNaN(fov) || Float.isInfinite(fov)) {
						fov = 1.0F;
					}
					if (playerEntity.isSneaking()) {
						fov *= 0.5f;
					}

					fov = MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0F, fov);
					cir.setReturnValue(fov);
				}
			}
	}
}