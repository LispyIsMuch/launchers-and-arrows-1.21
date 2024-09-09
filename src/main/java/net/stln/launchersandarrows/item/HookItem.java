package net.stln.launchersandarrows.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.projectile.ItemProjectile;
import org.jetbrains.annotations.Nullable;

public class HookItem extends Item implements ProjectileItem {
    public HookItem(Item.Settings settings) {
        super(settings);
    }

    public ItemProjectile createHook(World world, ItemStack stack, LivingEntity shooter) {
        return new ItemProjectile(world, shooter, stack.copyWithCount(1));
    }

    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        return new ItemProjectile(world, null, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
    }
}
