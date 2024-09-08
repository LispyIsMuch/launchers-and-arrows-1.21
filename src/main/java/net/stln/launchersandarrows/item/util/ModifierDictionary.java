package net.stln.launchersandarrows.item.util;

import net.minecraft.item.Item;
import net.stln.launchersandarrows.util.Map2d;

public class ModifierDictionary {
    private static final Map2d<Item, Integer, Integer> dict = new Map2d<>();

    public static void registerToDict(Item item, Integer id, Integer amount) {
        dict.put(item, id, amount);
    }

    public static Integer getEffect(Item item, Integer id) {
        return dict.get(item, id);
    }

    public static Map2d<Item, Integer, Integer> getDict() {
        return dict;
    }
}
