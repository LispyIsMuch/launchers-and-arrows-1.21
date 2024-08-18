package net.stln.launchersandarrows;

import net.fabricmc.api.ClientModInitializer;
import net.stln.launchersandarrows.item.CustomModelPredicateProvider;

public class LaunhchersAndArrowsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CustomModelPredicateProvider.registerModModels();
    }
}
