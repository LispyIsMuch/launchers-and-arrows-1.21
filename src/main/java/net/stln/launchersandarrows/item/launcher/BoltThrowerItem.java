package net.stln.launchersandarrows.item.launcher;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.stln.launchersandarrows.item.ModItemTags;
import net.stln.launchersandarrows.item.ModifierItem;
import net.stln.launchersandarrows.item.bow.ModfiableBowItem;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.sound.SoundInit;

import java.util.List;
import java.util.function.Predicate;

public class BoltThrowerItem extends ModfiableBowItem {

    public static final Predicate<ItemStack> SLINGSHOT_HELD_PROJECTILES = (stack) -> {
        return stack.isIn(ModItemTags.BOXED_BOLTS);
    };

    public BoltThrowerItem(Settings settings) {
        super(settings);
        pulltime = 40;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            if (stack.get(ModComponentInit.BOLT_COUNT_COMPONENT) == 0) {
                this.loadBolt(stack, player, remainingUseTicks);
            } else {
                int i = this.getMaxUseTime(stack, player) - remainingUseTicks;
                int count = getChargedCount(i, stack);
                stack.set(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, count);
            }
        }
    }

    private int getChargedCount(int i, ItemStack stack) {
        return Math.min(i, 60);
    }

    private void loadBolt(ItemStack stack, PlayerEntity playerEntity, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack, playerEntity) - remainingUseTicks;
        float f = getModifiedPullProgress(i, stack);
        if (f >= 1.0F) {
            ItemStack itemStack = this.getProjectileTypeWithSelector(playerEntity, stack);
            ChargedProjectilesComponent component = ChargedProjectilesComponent.of(itemStack);
            stack.set(DataComponentTypes.CHARGED_PROJECTILES, component);
            stack.set(ModComponentInit.BOLT_COUNT_COMPONENT, 60);
            World world = playerEntity.getWorld();
            world.playSound(
                    null,
                    playerEntity.getX(),
                    playerEntity.getY(),
                    playerEntity.getZ(),
                    SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
                    SoundCategory.PLAYERS,
                    1.5F,
                    1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );
            world.playSound(
                    null,
                    playerEntity.getX(),
                    playerEntity.getY(),
                    playerEntity.getZ(),
                    SoundEvents.BLOCK_PISTON_CONTRACT,
                    SoundCategory.PLAYERS,
                    0.5F,
                    2.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        float f = getModifiedPullProgress(i, stack);
        if (f == 1.0F && stack.get(ModComponentInit.BOLT_COUNT_COMPONENT) == 0) {
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity playerEntity &&
                (playerEntity.getMainHandStack().equals(stack) || playerEntity.getOffHandStack().equals(stack))
                && stack.get(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT) != null
                && stack.get(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT) != null) {
            int count = stack.get(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT);
            int leftCount = stack.get(ModComponentInit.BOLT_COUNT_COMPONENT);
            if (count > 0) {
                if (leftCount > 0) {
                    ChargedProjectilesComponent chargedProjectilesComponent = (ChargedProjectilesComponent) stack.get(DataComponentTypes.CHARGED_PROJECTILES);
                    if (chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty()) {
                        ItemStack itemStack = chargedProjectilesComponent.getProjectiles().get(0);
                        if (!itemStack.isEmpty()) {
                            List<ItemStack> list = load(stack, itemStack, playerEntity);
                            if (world instanceof ServerWorld serverWorld && !list.isEmpty()) {
                                this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, 2.0F, 10.0F, true, null);
                            }
                            world.playSound(
                                    null,
                                    playerEntity.getX(),
                                    playerEntity.getY(),
                                    playerEntity.getZ(),
                                    SoundInit.BOW_RELEASE,
                                    SoundCategory.PLAYERS,
                                    1.5F,
                                    1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                            );
                        }
                    }
                    stack.set(ModComponentInit.BOLT_COUNT_COMPONENT, leftCount - 1);
                } else {
                    world.playSound(
                            null,
                            playerEntity.getX(),
                            playerEntity.getY(),
                            playerEntity.getZ(),
                            SoundEvents.BLOCK_DISPENSER_FAIL,
                            SoundCategory.PLAYERS,
                            1.5F,
                            1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                    );
                }
                stack.set(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, count - 1);
            }
            if (count == 0 && leftCount == 0) {
                stack.set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT);
            }
        } else {
            stack.set(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, 0);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }
}
