package net.stln.launchersandarrows.item.bow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.AttributedProjectile;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.item.util.AttributeModifierDictionary;

import java.util.List;

public class ModfiableBowItem extends BowItem {
    protected static int slotsize = 3;

    public ModfiableBowItem(Settings settings) {
        super(settings);
    }


    public void setModifier(int slot, ItemStack bow, ItemStack modifier) {
        if (slot < slotsize) {
            List<ItemStack> modifiers = bow.get(ModComponentInit.MODIFIER_COMPONENT);
            List<ItemStack> copy = new java.util.ArrayList<>(List.of());
            if (modifiers != null && !modifiers.isEmpty()) {
                for (int i = 0; i < modifiers.size(); i++) {
                    copy.set(i, modifiers.get(i));
                }
                copy.set(slot, modifier);
                bow.set(ModComponentInit.MODIFIER_COMPONENT, copy);
            }
            bow.set(ModComponentInit.MODIFIER_COMPONENT, List.of(modifier));
        }
    }

    public ItemStack getModifier(int slot, ItemStack bow) {
        List<ItemStack> modifiers = bow.get(ModComponentInit.MODIFIER_COMPONENT);
        if (slot < slotsize && modifiers != null) {
            return modifiers.get(slot);
        }
        return null;
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        ProjectileEntity entity = super.createArrowEntity(world, shooter, weaponStack, projectileStack, critical);
        if (entity instanceof PersistentProjectileEntity projectile) {
            for (int i = 0; i < slotsize; i++) {
                ItemStack modifier = getModifier(i, weaponStack);
                if (modifier != null) {
                    for (int j = 0; j < 13; j++) {
                        if (AttributeModifierDictionary.getDict().containsKey2(modifier.getItem(), j - 6)) {
                            ((AttributedProjectile)projectile).setAttribute(j - 6, AttributeModifierDictionary.getAttributeEffect(modifier.getItem(), j - 6));
                        }
                    }
                }
            }
            return projectile;
        }
        return entity;
    }

    public int getSlotsize() {
        return slotsize;
    }
}
