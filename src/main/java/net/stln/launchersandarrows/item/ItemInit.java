package net.stln.launchersandarrows.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.LaunchersAndArrowsDataGenerator;
import net.stln.launchersandarrows.item.bow.LongBowItem;
import net.stln.launchersandarrows.item.bow.RapidBowItem;

public class ItemInit {

    public static final Item LONG_BOW = registerItem("long_bow", new LongBowItem(new Item.Settings().maxDamage(512)));
    public static final Item RAPID_BOW = registerItem("rapid_bow", new RapidBowItem(new Item.Settings().maxDamage(256)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LaunchersAndArrows.MOD_ID, name), item);
    }

    public static void  registerModItems() {
        LaunchersAndArrows.LOGGER.info("Registering Mod Items for" + LaunchersAndArrows.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(LONG_BOW);
            entries.add(RAPID_BOW);
        });
    }
}
