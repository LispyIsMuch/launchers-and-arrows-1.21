package net.stln.launchersandarrows.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;

public class StatusEffectInit {

    public static final StatusEffect FLAME_ACCUMULATION_EFFECT = new FlameAccumulationEffect();
    public static final StatusEffect FROST_ACCUMULATION_EFFECT = new FrostAccumulationEffect();
    public static final StatusEffect LIGHTNING_ACCUMULATION_EFFECT = new LightningAccumulationEffect();
    public static final StatusEffect ACID_ACCUMULATION_EFFECT = new AcidAccumulationEffect();
    public static final StatusEffect FLOOD_ACCUMULATION_EFFECT = new FloodAccumulationEffect();
    public static final StatusEffect ECHO_ACCUMULATION_EFFECT = new EchoAccumulationEffect();
    public static final StatusEffect SHOCK_EXPLOSION_EFFECT = new ShockExplosionEffect();
    public static final StatusEffect BURNING_EFFECT = new BurningEffect();
    public static final StatusEffect FREEZE_EFFECT = new FreezeEffect();
    public static final StatusEffect ELECTRIC_SHOCK_EFFECT = new ElectricShockEffect();
    public static final StatusEffect CORROSION_EFFECT = new CorrosionEffect();
    public static final StatusEffect SUBMERGED_EFFECT = new SubmergedEffect();
    public static final StatusEffect CONFUSION_EFFECT = new ConfusionEffect();
    public static final StatusEffect SERIOUS_INJURY_EFFECT = new SeriousInjuryEffect();
    public static final RegistryEntry<StatusEffect> FLAME_ACCUMULATION = register("flame_accumulation", new FlameAccumulationEffect());
    public static final RegistryEntry<StatusEffect> FROST_ACCUMULATION = register("frost_accumulation", new FrostAccumulationEffect());
    public static final RegistryEntry<StatusEffect> LIGHTNING_ACCUMULATION = register("lightning_accumulation", new LightningAccumulationEffect());
    public static final RegistryEntry<StatusEffect> ACID_ACCUMULATION = register("acid_accumulation", new AcidAccumulationEffect());
    public static final RegistryEntry<StatusEffect> FLOOD_ACCUMULATION = register("flood_accumulation", new FloodAccumulationEffect());
    public static final RegistryEntry<StatusEffect> ECHO_ACCUMULATION = register("echo_accumulation", new EchoAccumulationEffect());
    public static final RegistryEntry<StatusEffect> SHOCK_EXPLOSION = register("shock_explosion", new ShockExplosionEffect());
    public static final RegistryEntry<StatusEffect> BURNING = register("burning", new BurningEffect());
    public static final RegistryEntry<StatusEffect> FREEZE = register("freeze", new FreezeEffect());
    public static final RegistryEntry<StatusEffect> ELECTRICK_SHOCK = register("electric_shock", new ElectricShockEffect());
    public static final RegistryEntry<StatusEffect> CORROSION = register("corrosion", new CorrosionEffect());
    public static final RegistryEntry<StatusEffect> SUBMERGED = register("submerged", new SubmergedEffect());
    public static final RegistryEntry<StatusEffect> CONFUSION = register("confusion", new ConfusionEffect());
    public static final RegistryEntry<StatusEffect> SERIOUS_INJURY = register("serious_injury", new SeriousInjuryEffect());

    public static void registerStatusEffect() {
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "flame_accumulation"), FLAME_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "frost_accumulation"), FROST_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "lightning_accumulation"), LIGHTNING_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "acid_accumulation"), ACID_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "flood_accumulation"), FLOOD_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "echo_accumulation"), ECHO_ACCUMULATION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "shock_explosion"), SHOCK_EXPLOSION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "burning"), BURNING_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "freeze"), FREEZE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "electric_shock"), ELECTRIC_SHOCK_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "corrosion"), CORROSION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "submerged"), SUBMERGED_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "confusion"), CONFUSION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, "serious_injury"), SERIOUS_INJURY_EFFECT);
    }

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, id), statusEffect);
    }
}
