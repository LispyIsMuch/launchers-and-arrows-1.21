package net.stln.launchersandarrows;

import net.fabricmc.api.ClientModInitializer;
import net.stln.launchersandarrows.client.hud.HudInit;
import net.stln.launchersandarrows.entity.EntityInit;
import net.stln.launchersandarrows.item.CustomModelPredicateProvider;
import net.stln.launchersandarrows.item.ItemInit;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;

public class LaunhchersAndArrowsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CustomModelPredicateProvider.registerModModels();
        ParticleInit.registerParticleClient();
        EntityInit.registerModEntitiesRenderer();
        StatusEffectInit.registerStatusEffect();
        HudInit.registerHud();
    }
}
