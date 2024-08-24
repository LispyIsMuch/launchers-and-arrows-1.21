package net.stln.launchersandarrows.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.LaunchersAndArrowsDataGenerator;
import net.stln.launchersandarrows.item.bow.LongBowItem;
import net.stln.launchersandarrows.item.bow.MultiShotBowItem;
import net.stln.launchersandarrows.item.bow.RapidBowItem;
import net.stln.launchersandarrows.item.launcher.CrossLauncherItem;

public class ItemInit {

    public static final Item LONG_BOW = registerItem("long_bow", new LongBowItem(new Item.Settings().maxDamage(512)));
    public static final Item RAPID_BOW = registerItem("rapid_bow", new RapidBowItem(new Item.Settings().maxDamage(512)));
    public static final Item MULTISHOT_BOW = registerItem("multishot_bow", new MultiShotBowItem(new Item.Settings().maxDamage(512)));
    public static final Item CROSSLAUNCHER = registerItem("crosslauncher", new CrossLauncherItem(new Item.Settings().maxDamage(1024)));

    public static final Item FLAME_ARROW = registerItem("flame_arrow", new ArrowItem(new Item.Settings()));
    public static final Item FREEZING_ARROW = registerItem("freezing_arrow", new ArrowItem(new Item.Settings()));
    public static final Item LIGHTNING_ARROW = registerItem("lightning_arrow", new ArrowItem(new Item.Settings()));
    public static final Item CORROSIVE_ARROW = registerItem("corrosive_arrow", new ArrowItem(new Item.Settings()));
    public static final Item FLOOD_ARROW = registerItem("flood_arrow", new ArrowItem(new Item.Settings()));
    public static final Item WAVE_ARROW = registerItem("wave_arrow", new ArrowItem(new Item.Settings()));
    public static final Item PIERCING_ARROW = registerItem("piercing_arrow", new ArrowItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LaunchersAndArrows.MOD_ID, name), item);
    }

    public static void  registerModItems() {
        LaunchersAndArrows.LOGGER.info("Registering Mod Items for" + LaunchersAndArrows.MOD_ID);
    }
}
