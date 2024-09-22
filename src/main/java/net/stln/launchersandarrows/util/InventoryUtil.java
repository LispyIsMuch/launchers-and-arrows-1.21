package net.stln.launchersandarrows.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.stln.launchersandarrows.entity.EntityInit;

public class InventoryUtil {

    public static ItemStack getItemInInventory(PlayerEntity entity, Item item) {
        for (int i = 0; i < entity.getInventory().size(); i++) {
            if (entity.getInventory().getStack(i).isOf(item)) {
                return entity.getInventory().getStack(i);
            }
        }
        return null;
    }
}
