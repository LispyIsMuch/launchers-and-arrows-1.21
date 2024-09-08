package net.stln.launchersandarrows.item.bow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.AttributedProjectile;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.item.component.ModifierComponent;
import net.stln.launchersandarrows.item.util.AttributeModifierDictionary;
import net.stln.launchersandarrows.item.util.ModifierDictionary;
import net.stln.launchersandarrows.util.ModifierEnum;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModfiableBowItem extends BowItem {
    protected static int slotsize = 3;
    protected int pulltime = 40;

    public ModfiableBowItem(Settings settings) {
        super(settings);
    }


    public void setModifier(int slot, ItemStack bow, ItemStack modifier) {
        if (slot < slotsize) {
            ModifierComponent modifierComponent = bow.get(ModComponentInit.MODIFIER_COMPONENT);
            if (modifierComponent != null) {
                List<ItemStack> modifiers = new java.util.ArrayList<>(List.copyOf(modifierComponent.getModifiers()));
                modifiers.add(modifier);
                bow.set(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.of(modifiers));
            } else {
                bow.set(ModComponentInit.MODIFIER_COMPONENT, ModifierComponent.of(List.of(modifier)));
            }
        }
    }

    public ItemStack getModifier(int slot, ItemStack bow) {
        ModifierComponent modifierComponent = bow.get(ModComponentInit.MODIFIER_COMPONENT);
        if (modifierComponent !=null) {
            List<ItemStack> modifiers = modifierComponent.getModifiers();
            if (slot < modifiers.size()) {
                return modifiers.get(slot);
            }
        }
        return null;
    }

    public List<ItemStack> getModifiers(ItemStack bow) {
        ModifierComponent modifierComponent = bow.get(ModComponentInit.MODIFIER_COMPONENT);
        if (modifierComponent !=null) {
            return modifierComponent.getModifiers();
        }
        return null;
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        ProjectileEntity entity = super.createArrowEntity(world, shooter, weaponStack, projectileStack, critical);
        if (entity instanceof PersistentProjectileEntity projectile) {
            for (int i = 0; i < slotsize; i++) {
                if (i < getModifiers(weaponStack).size()) {
                    ItemStack modifier = getModifier(i, weaponStack);
                    if (modifier != null) {
                        for (int j = 0; j < 13; j++) {
                            if (AttributeModifierDictionary.getDict().containsKey2(modifier.getItem(), j - 6)) {
                                ((AttributedProjectile) projectile).setAttribute(j - 6,
                                        AttributeModifierDictionary.getAttributeEffect(modifier.getItem(), j - 6) + ((AttributedProjectile) projectile).getAttribute(j - 6));
                            }
                        }
                    }
                }
            }
            return projectile;
        }
        return entity;
    }

    @Override
    protected void shootAll(ServerWorld world, LivingEntity shooter, Hand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, @Nullable LivingEntity target) {
        speed = applyModifier(shooter, stack, speed);
        super.shootAll(world, shooter, hand, stack, projectiles, speed, divergence, critical, target);
    }

    protected float applyModifier(LivingEntity shooter, ItemStack stack, float speed) {
        float sturdyPercentage = 0F;
        for (int i = 0; i < slotsize; i++) {
            if (i < getModifiers(stack).size()) {
                ItemStack modifier = getModifier(i, stack);
                if (ModifierDictionary.getEffect(modifier.getItem(), ModifierEnum.RANGE.get()) != null) {
                    speed *= (ModifierDictionary.getEffect(modifier.getItem(), ModifierEnum.RANGE.get()) + 100) / 100.0F;
                }
                if (ModifierDictionary.getEffect(modifier.getItem(), ModifierEnum.STURDY.get()) != null) {
                    sturdyPercentage += (ModifierDictionary.getEffect(modifier.getItem(), ModifierEnum.STURDY.get())) / 100.0F;
                }
            }
        }
        LaunchersAndArrows.LOGGER.info(String.valueOf(sturdyPercentage));
        if (shooter.getRandom().nextFloat() < sturdyPercentage) {
            stack.setDamage(stack.getDamage() - 1);
            LaunchersAndArrows.LOGGER.info("applied");
        }
        return speed;
    }

    public int getSlotsize() {
        return slotsize;
    }

    public int getPulltime() {
        return pulltime;
    }

    @Override
    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / ;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
}
