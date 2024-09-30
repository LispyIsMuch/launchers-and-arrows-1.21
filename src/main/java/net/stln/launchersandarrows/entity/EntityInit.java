package net.stln.launchersandarrows.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.projectile.BoltEntity;
import net.stln.launchersandarrows.entity.projectile.ItemProjectile;
import net.stln.launchersandarrows.entity.renderer.BoltEntityRenderer;

public class EntityInit {

    public static final EntityType<ItemProjectile> ITEM_PROJECTILE = EntityType.Builder.<ItemProjectile>create(ItemProjectile::new, SpawnGroup.MISC)
            .dimensions(0.5F, 0.5F)
            .eyeHeight(0.13F)
            .maxTrackingRange(4)
            .trackingTickInterval(20)
            .build();
    public static final EntityType<BoltEntity> BOLT_PROJECTILE = EntityType.Builder.<BoltEntity>create(BoltEntity::new, SpawnGroup.MISC)
            .dimensions(0.5F, 0.5F)
            .eyeHeight(0.13F)
            .maxTrackingRange(4)
            .trackingTickInterval(20)
            .build();


    public static void registerModEntities() {
        LaunchersAndArrows.LOGGER.info("Registering Entity for " + LaunchersAndArrows.MOD_ID);
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(LaunchersAndArrows.MOD_ID, "item_projectile"), ITEM_PROJECTILE);
        Registry.register(Registries.ENTITY_TYPE, Identifier.of(LaunchersAndArrows.MOD_ID, "bolt"), BOLT_PROJECTILE);
    }

    public static void registerModEntitiesRenderer() {
        LaunchersAndArrows.LOGGER.info("Registering Entity Renderer for " + LaunchersAndArrows.MOD_ID);
        EntityRendererRegistry.register(ITEM_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(BOLT_PROJECTILE, BoltEntityRenderer::new);
    }

}
