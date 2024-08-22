package net.stln.launchersandarrows.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;

public class ModItemTags {
    public static final TagKey<Item> ARROWS_WITH_EFFECT =
            TagKey.of(RegistryKeys.ITEM, Identifier.of(LaunchersAndArrows.MOD_ID, "arrows_with_effect"));
}
