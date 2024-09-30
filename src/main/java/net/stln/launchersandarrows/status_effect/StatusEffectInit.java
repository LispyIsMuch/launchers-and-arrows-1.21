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
    public static RegistryEntry<StatusEffect> FLAME_ACCUMULATION = registerEntry("flame_accumulation", FLAME_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> FROST_ACCUMULATION = registerEntry("frost_accumulation", FROST_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> LIGHTNING_ACCUMULATION = registerEntry("lightning_accumulation", LIGHTNING_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> ACID_ACCUMULATION = registerEntry("acid_accumulation", ACID_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> FLOOD_ACCUMULATION = registerEntry("flood_accumulation", FLOOD_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> ECHO_ACCUMULATION = registerEntry("echo_accumulation", ECHO_ACCUMULATION_EFFECT);
    public static RegistryEntry<StatusEffect> SHOCK_EXPLOSION = registerEntry("shock_explosion", SHOCK_EXPLOSION_EFFECT);
    public static RegistryEntry<StatusEffect> BURNING = registerEntry("burning", BURNING_EFFECT);
    public static RegistryEntry<StatusEffect> FREEZE = registerEntry("freeze", FREEZE_EFFECT);
    public static RegistryEntry<StatusEffect> ELECTRIC_SHOCK = registerEntry("electric_shock", ELECTRIC_SHOCK_EFFECT);
    public static RegistryEntry<StatusEffect> CORROSION = registerEntry("corrosion", CORROSION_EFFECT);
    public static RegistryEntry<StatusEffect> SUBMERGED = registerEntry("submerged", SUBMERGED_EFFECT);
    public static RegistryEntry<StatusEffect> CONFUSION = registerEntry("confusion", CONFUSION_EFFECT);
    public static RegistryEntry<StatusEffect> SERIOUS_INJURY = registerEntry("serious_injury", SERIOUS_INJURY_EFFECT);

    public static void registerStatusEffect() {
        LaunchersAndArrows.LOGGER.info("Registering Status Effect for " + LaunchersAndArrows.MOD_ID);
    }

    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, id), statusEffect);
    }
    private static RegistryEntry<StatusEffect> registerEntry(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT,
                Identifier.of(LaunchersAndArrows.MOD_ID, id), statusEffect);
    }
    private static RegistryEntry<StatusEffect> registerOf(String id, StatusEffect statusEffect) {
        return RegistryEntry.of(statusEffect);
    }
}
