package net.stln.launchersandarrows.entity.renderer;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.projectile.BoltEntity;

public class BoltEntityRenderer extends ProjectileEntityRenderer<BoltEntity> {
    public BoltEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(BoltEntity boltEntity) {
        return Identifier.of(LaunchersAndArrows.MOD_ID, "textures/entity/bolt.png");
    }
}
