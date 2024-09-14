package net.stln.launchersandarrows.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.LaunchersAndArrowsDataGenerator;
import net.stln.launchersandarrows.item.bow.LongBowItem;
import net.stln.launchersandarrows.item.bow.MultiShotBowItem;
import net.stln.launchersandarrows.item.bow.RapidBowItem;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.item.component.ModifierComponent;
import net.stln.launchersandarrows.item.launcher.BoltThrowerItem;
import net.stln.launchersandarrows.item.launcher.CrossLauncherItem;
import net.stln.launchersandarrows.item.launcher.HookLauncherItem;
import net.stln.launchersandarrows.item.launcher.SlingShotItem;
import net.stln.launchersandarrows.item.util.AttributeEffectsDictionary;
import net.stln.launchersandarrows.item.util.AttributeModifierDictionary;
import net.stln.launchersandarrows.item.util.ModifierDictionary;
import net.stln.launchersandarrows.util.AttributeEnum;
import net.stln.launchersandarrows.util.ModifierEnum;

import java.util.List;

public class ItemInit {

    public static final Item LONG_BOW = registerItem("long_bow",
            new LongBowItem(new Item.Settings().maxDamage(512).component(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.DEFAULT)));
    public static final Item RAPID_BOW = registerItem("rapid_bow",
            new RapidBowItem(new Item.Settings().maxDamage(512).component(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.DEFAULT)));
    public static final Item MULTISHOT_BOW = registerItem("multishot_bow",
            new MultiShotBowItem(new Item.Settings().maxDamage(512).component(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.DEFAULT)));
    public static final Item BOLT_THROWER = registerItem("bolt_thrower",
            new BoltThrowerItem(new Item.Settings().maxDamage(512).component(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.DEFAULT)
                    .component(ModComponentInit.BOLT_COUNT_COMPONENT, 0)
                    .component(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, 0)
                    .component(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT)));
    public static final Item CROSSLAUNCHER = registerItem("crosslauncher", new CrossLauncherItem(new Item.Settings().maxDamage(1024)));
    public static final Item HOOK_LAUNCHER = registerItem("hook_launcher", new HookLauncherItem(new Item.Settings().maxDamage(512)));
    public static final Item SLINGSHOT = registerItem("slingshot",
            new SlingShotItem(new Item.Settings().maxDamage(512).component(ModComponentInit.MODIFIER_COMPONENT,
                    ModifierComponent.DEFAULT)));

    public static final Item FLAME_ARROW = registerItem("flame_arrow", new ArrowItem(new Item.Settings()));
    public static final Item FREEZING_ARROW = registerItem("freezing_arrow", new ArrowItem(new Item.Settings()));
    public static final Item LIGHTNING_ARROW = registerItem("lightning_arrow", new ArrowItem(new Item.Settings()));
    public static final Item CORROSIVE_ARROW = registerItem("corrosive_arrow", new ArrowItem(new Item.Settings()));
    public static final Item FLOOD_ARROW = registerItem("flood_arrow", new ArrowItem(new Item.Settings()));
    public static final Item REVERBERATING_ARROW = registerItem("reverberating_arrow", new ArrowItem(new Item.Settings()));
    public static final Item WAVE_ARROW = registerItem("wave_arrow", new ArrowItem(new Item.Settings()));
    public static final Item PIERCING_ARROW = registerItem("piercing_arrow", new ArrowItem(new Item.Settings()));

    public static final Item GRAPPLING_HOOK = registerItem("grappling_hook", new ArrowItem(new Item.Settings()));

    public static final Item IGNITION_STRING = registerItem("ignition_string", new ModifierItem(new Item.Settings()));
    public static final Item FROSTBITE_STRING = registerItem("frostbite_string", new ModifierItem(new Item.Settings()));
    public static final Item CHARGING_STRING = registerItem("charging_string", new ModifierItem(new Item.Settings()));
    public static final Item DETERIORATION_STRING = registerItem("deterioration_string", new ModifierItem(new Item.Settings()));
    public static final Item PERMEATION_STRING = registerItem("permeation_string", new ModifierItem(new Item.Settings()));
    public static final Item VIBRATING_STRING = registerItem("vibrating_string", new ModifierItem(new Item.Settings()));
    public static final Item RANGE_STRING = registerItem("range_string", new ModifierItem(new Item.Settings()));
    public static final Item STURDY_STRING = registerItem("sturdy_string", new ModifierItem(new Item.Settings()));
    public static final Item LIGHTWEIGHT_STRING = registerItem("lightweight_string", new ModifierItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LaunchersAndArrows.MOD_ID, name), item);
    }

    public static void  registerModItems() {
        LaunchersAndArrows.LOGGER.info("Registering Mod Items for" + LaunchersAndArrows.MOD_ID);
    }

    public static void registerAttributeEffect() {
        AttributeEffectsDictionary.registerToDict(FLAME_ARROW, AttributeEnum.FLAME.get(), 15);
        AttributeEffectsDictionary.registerToDict(FREEZING_ARROW, AttributeEnum.FROST.get(), 15);
        AttributeEffectsDictionary.registerToDict(LIGHTNING_ARROW, AttributeEnum.LIGHTNING.get(), 15);
        AttributeEffectsDictionary.registerToDict(CORROSIVE_ARROW, AttributeEnum.ACID.get(), 15);
        AttributeEffectsDictionary.registerToDict(FLOOD_ARROW, AttributeEnum.FLOOD.get(), 15);
        AttributeEffectsDictionary.registerToDict(REVERBERATING_ARROW, AttributeEnum.ECHO.get(), 15);
        AttributeEffectsDictionary.registerToDict(PIERCING_ARROW, AttributeEnum.INJURY.get(), 5);

        AttributeEffectsDictionary.registerToDict(Items.MAGMA_CREAM, AttributeEnum.FLAME.get(), 5);
        AttributeEffectsDictionary.registerToDict(Items.SNOWBALL, AttributeEnum.FROST.get(), 5);
        AttributeEffectsDictionary.registerToDict(Items.LIGHTNING_ROD, AttributeEnum.LIGHTNING.get(), 5);
        AttributeEffectsDictionary.registerToDict(Items.SLIME_BALL, AttributeEnum.ACID.get(), 3);
        AttributeEffectsDictionary.registerToDict(Items.ECHO_SHARD, AttributeEnum.ECHO.get(), 175);
        AttributeEffectsDictionary.registerToDict(Items.HEART_OF_THE_SEA, AttributeEnum.FLOOD.get(), 175);
        AttributeEffectsDictionary.registerToDict(Items.HEAVY_CORE, AttributeEnum.INJURY.get(), 10);
        AttributeEffectsDictionary.registerToDict(Items.POINTED_DRIPSTONE, AttributeEnum.INJURY.get(), 3);

        AttributeModifierDictionary.registerToDict(IGNITION_STRING, AttributeEnum.FLAME.get(), 3);
        AttributeModifierDictionary.registerToDict(IGNITION_STRING, AttributeEnum.FLAME_RATIO.get(), 75);
        AttributeModifierDictionary.registerToDict(FROSTBITE_STRING, AttributeEnum.FROST.get(), 3);
        AttributeModifierDictionary.registerToDict(FROSTBITE_STRING, AttributeEnum.FROST_RATIO.get(), 75);
        AttributeModifierDictionary.registerToDict(CHARGING_STRING, AttributeEnum.LIGHTNING.get(), 3);
        AttributeModifierDictionary.registerToDict(CHARGING_STRING, AttributeEnum.LIGHTNING_RATIO.get(), 75);
        AttributeModifierDictionary.registerToDict(DETERIORATION_STRING, AttributeEnum.ACID.get(), 3);
        AttributeModifierDictionary.registerToDict(DETERIORATION_STRING, AttributeEnum.ACID_RATIO.get(), 75);
        AttributeModifierDictionary.registerToDict(PERMEATION_STRING, AttributeEnum.FLOOD.get(), 3);
        AttributeModifierDictionary.registerToDict(PERMEATION_STRING, AttributeEnum.FLOOD_RATIO.get(), 75);
        AttributeModifierDictionary.registerToDict(VIBRATING_STRING, AttributeEnum.ECHO.get(), 3);
        AttributeModifierDictionary.registerToDict(VIBRATING_STRING, AttributeEnum.ECHO_RATIO.get(), 75);

        ModifierDictionary.registerToDict(RANGE_STRING, ModifierEnum.RANGE.get(), 25);
        ModifierDictionary.registerToDict(STURDY_STRING, ModifierEnum.STURDY.get(), 25);
        ModifierDictionary.registerToDict(LIGHTWEIGHT_STRING, ModifierEnum.LIGHTWEIGHT.get(), 15);
    }
}
