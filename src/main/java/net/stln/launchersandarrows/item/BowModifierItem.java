package net.stln.launchersandarrows.item;

import net.minecraft.item.Item;
import net.stln.launchersandarrows.item.bow.ModfiableBowItem;
import net.stln.launchersandarrows.item.launcher.BoltThrowerItem;

public class BowModifierItem extends ModifierItem{
    public BowModifierItem(Settings settings) {
        super(settings);
    }

    @Override
    protected Item getCorrectTarget(Item item) {
        if (item instanceof ModfiableBowItem && !(item instanceof BoltThrowerItem)) {
            return item;
        }
        return null;
    }
}
