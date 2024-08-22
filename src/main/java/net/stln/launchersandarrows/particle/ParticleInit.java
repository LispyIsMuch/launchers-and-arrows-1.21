package net.stln.launchersandarrows.particle;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;

public class ParticleInit {

    public static final SimpleParticleType FLAME_EFFECT = FabricParticleTypes.simple();

    public static void registerParticleTypes() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(LaunchersAndArrows.MOD_ID, "flame_effect"), FLAME_EFFECT);
    }

    public static void registerParticleClient() {
        ParticleFactoryRegistry.getInstance().register(ParticleInit.FLAME_EFFECT, EffectParticle.Factory::new);
    }
}
