package net.stln.launchersandarrows.util;

import net.minecraft.item.Item;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.item.ItemInit;

public class TextUtil {
    static Identifier iconFont = Identifier.of(LaunchersAndArrows.MOD_ID, "icons");

    public static Text getIconText(Item item) {
        if (item == ItemInit.IGNITION_STRING) {
            return Text.literal("\u1000").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.FROSTBITE_STRING) {
            return Text.literal("\u1001").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.CHARGING_STRING) {
            return Text.literal("\u1002").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.DETERIORATION_STRING) {
            return Text.literal("\u1003").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.PERMEATION_STRING) {
            return Text.literal("\u1004").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.VIBRATING_STRING) {
            return Text.literal("\u1005").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.RANGE_STRING) {
            return Text.literal("\u2000").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.STURDY_STRING) {
            return Text.literal("\u2001").setStyle(Style.EMPTY.withFont(iconFont));
        }
        if (item == ItemInit.LIGHTWEIGHT_STRING) {
            return Text.literal("\u2002").setStyle(Style.EMPTY.withFont(iconFont));
        }
        return Text.empty();
    }
}
