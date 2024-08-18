package net.stln.launchersandarrows.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CustomModelPredicateProvider {
    public static void registerModModels() {
        registerModBow(ItemInit.LONG_BOW);
    }

    private static void registerModBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 40.0F;
            }
        });
        ModelPredicateProviderRegistry.register(bow, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
}
