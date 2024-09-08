package net.stln.launchersandarrows.item.util;

import net.minecraft.item.Item;
import net.stln.launchersandarrows.util.Map2d;

import java.util.HashMap;
import java.util.Map;

public class BowPullTimeDictionary {
    private static final Map<Item, Integer> dict = new HashMap<>();

    public static Integer get(Item item) {
        return dict.get(item);
    }

    public static Map<Item, Integer> getDict() {
        return dict;
    }
}
