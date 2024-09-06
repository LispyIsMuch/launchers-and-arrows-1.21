package net.stln.launchersandarrows.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModComponentInit {

    public static final ComponentType<ModifierComponent> MODIFIER_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(LaunchersAndArrows.MOD_ID, "modifier"),
            ComponentType.<ModifierComponent>builder().codec(ModifierComponent.CODEC).packetCodec(ModifierComponent.PACKET_CODEC).build()
    );
}
