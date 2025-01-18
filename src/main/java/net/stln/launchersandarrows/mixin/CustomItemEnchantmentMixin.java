package net.stln.launchersandarrows.mixin; 

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.stln.launchersandarrows.item.bow.LongBowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class LongBowEnchantmentMixin {

    @Inject(method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true)
    private void allowLongBowEnchantments(ItemStack stack, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof LongBowItem) {
            // Allow only specific enchantments for LongBow
            if (enchantment == Enchantments.POWER || enchantment == Enchantments.PUNCH || enchantment == Enchantments.INFINITY) {
                cir.setReturnValue(true); 
            } else {
                cir.setReturnValue(false); 
            }
        }
    }
}
