package net.stln.launchersandarrows;

import net.fabricmc.api.ClientModInitializer;
import net.stln.launchersandarrows.client.hud.HudInit;
import net.stln.launchersandarrows.entity.EntityInit;
import net.stln.launchersandarrows.item.CustomModelPredicateProvider;
import net.stln.launchersandarrows.item.ItemInit;
import net.stln.launchersandarrows.particle.ParticleInit;

public class LaunhchersAndArrowsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CustomModelPredicateProvider.registerModModels();
        ParticleInit.registerParticleClient();
        ItemInit.registerAttributeEffect();
        EntityInit.registerModEntitiesRenderer();
        HudInit.registerHud();
    }
}
