package net.stln.launchersandarrows.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.projectile.BoltEntity;
import net.stln.launchersandarrows.entity.renderer.BoltEntityRenderer;
import org.jetbrains.annotations.Nullable;

public class BoltItem extends ArrowItem {
    public BoltItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new BoltEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        BoltEntity boltEntity = new BoltEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), (ItemStack)null);
        boltEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        return boltEntity;
    }
}
