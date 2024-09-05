package net.stln.launchersandarrows.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;

public class ModfiableBowItem extends BowItem {
    private int slotsize;
    ItemStack[] modifiers;

    public ModfiableBowItem(Settings settings, int slotsize) {
        super(settings);
        this.slotsize = slotsize;
        modifiers = new ItemStack[slotsize];
    }


    public void setModifier(int slot, ItemStack itemStack) {
        if (slot < slotsize) {
            modifiers[slot] = itemStack;
        }
    }

    public ItemStack getModifier(int slot) {
        return modifiers[slot];
    }

    public ItemStack[] getModifiers() {
        return modifiers;
    }


}
