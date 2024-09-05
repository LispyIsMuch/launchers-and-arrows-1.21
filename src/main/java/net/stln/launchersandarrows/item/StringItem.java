package net.stln.launchersandarrows.item;

import net.minecraft.item.Item;
import net.stln.launchersandarrows.item.util.AttributeModifierDictionary;

public class StringItem extends Item {
    public StringItem(Settings settings) {
        super(settings);
    }

    public int getAttributeModifier(int id) {
        return AttributeModifierDictionary.getAttributeEffect(this, id);
    }
}
