package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;

public class StatusEffectInit {

    public static final StatusEffect FLAME_ACCUMULATION_EFFECT = new FlameAccumulationEffect();
    public static final RegistryEntry<StatusEffect> FLAME_ACCUMULATION = register("flame_accumulation", new FlameAccumulationEffect());

    public static void registerStatusEffect() {
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "flame_accumulation"), FLAME_ACCUMULATION_EFFECT);
    }

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, id), statusEffect);
    }
}
