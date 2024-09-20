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

    public static final EntityType<ItemProjectile> ITEM_PROJECTILE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(LaunchersAndArrows.MOD_ID, "item_projectile"),
            FabricEntityTypeBuilder.<ItemProjectile>create(SpawnGroup.MISC, ItemProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .build());
    public static final EntityType<BoltEntity> BOLT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(LaunchersAndArrows.MOD_ID, "bolt"),
            FabricEntityTypeBuilder.<BoltEntity>create(SpawnGroup.MISC, BoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .build());


    public static void registerModEntitiesRenderer() {
        EntityRendererRegistry.register(ITEM_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(BOLT_PROJECTILE, BoltEntityRenderer::new);
    }
}
