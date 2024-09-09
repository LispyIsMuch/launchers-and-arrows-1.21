package net.stln.launchersandarrows.item_group;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.item.ItemInit;

public final class ItemGroupInit {
    public static final ItemGroup LAUNCHERS_AND_ARROWS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.LONG_BOW))
            .displayName(Text.translatable("itemgroup.launchers_and_arrows.launchers_and_arrows"))
            .entries((displayContext, entries) -> {
                entries.add(ItemInit.LONG_BOW);
                entries.add(ItemInit.RAPID_BOW);
                entries.add(ItemInit.MULTISHOT_BOW);
                entries.add(ItemInit.CROSSLAUNCHER);
                entries.add(ItemInit.HOOK_LAUNCHER);
                entries.add(ItemInit.FLAME_ARROW);
                entries.add(ItemInit.FREEZING_ARROW);
                entries.add(ItemInit.LIGHTNING_ARROW);
                entries.add(ItemInit.CORROSIVE_ARROW);
                entries.add(ItemInit.FLOOD_ARROW);
                entries.add(ItemInit.REVERBERATING_ARROW);
                entries.add(ItemInit.WAVE_ARROW);
                entries.add(ItemInit.PIERCING_ARROW);
                entries.add(ItemInit.GRAPPLING_HOOK);
                entries.add(ItemInit.IGNITION_STRING);
                entries.add(ItemInit.FROSTBITE_STRING);
                entries.add(ItemInit.CHARGING_STRING);
                entries.add(ItemInit.DETERIORATION_STRING);
                entries.add(ItemInit.PERMEATION_STRING);
                entries.add(ItemInit.VIBRATING_STRING);
                entries.add(ItemInit.RANGE_STRING);
                entries.add(ItemInit.STURDY_STRING);
                entries.add(ItemInit.LIGHTWEIGHT_STRING);
            })
            .build();

    public static void registerItemGroup() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(LaunchersAndArrows.MOD_ID, "launchers_and_arrows"), LAUNCHERS_AND_ARROWS);
    }
}
