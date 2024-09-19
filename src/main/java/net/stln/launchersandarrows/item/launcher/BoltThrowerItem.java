package net.stln.launchersandarrows.item.launcher;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.stln.launchersandarrows.item.ModItemTags;
import net.stln.launchersandarrows.item.ModifierItem;
import net.stln.launchersandarrows.item.bow.ModfiableBowItem;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.sound.SoundInit;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BoltThrowerItem extends ModfiableBowItem {

    private boolean played0 = false;
    private boolean played1 = false;
    private boolean played2 = false;

    public static final Predicate<ItemStack> SLINGSHOT_HELD_PROJECTILES = (stack) -> {
        return stack.isIn(ModItemTags.BOXED_BOLTS);
    };
    private static final CrossbowItem.LoadingSounds DEFAULT_LOADING_SOUNDS = new CrossbowItem.LoadingSounds(
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_START),
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE),
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_END)
    );

    public BoltThrowerItem(Settings settings) {
        super(settings);
        pulltime = 40;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        this.played0 = false;
        this.played1 = false;
        this.played2 = false;
        if (user.getStackInHand(hand).get(ModComponentInit.BOLT_COUNT_COMPONENT) > 0) {
            user.getStackInHand(hand).set(ModComponentInit.CHARGING_COMPONENT, true);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        stack.set(ModComponentInit.CHARGING_COMPONENT, false);
        if (user instanceof PlayerEntity player) {
            if (stack.get(ModComponentInit.BOLT_COUNT_COMPONENT) == 0) {
                this.loadBolt(stack, player, remainingUseTicks);
            }
        }
    }

    private void loadBolt(ItemStack stack, PlayerEntity playerEntity, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack, playerEntity) - remainingUseTicks;
        float f = getModifiedPullProgress(i, stack);
        if (f >= 1.0F) {
            ItemStack itemStack = this.getProjectileTypeWithSelector(playerEntity, stack);
            ChargedProjectilesComponent component = ChargedProjectilesComponent.of(itemStack);
            stack.set(DataComponentTypes.CHARGED_PROJECTILES, component);
            stack.set(ModComponentInit.BOLT_COUNT_COMPONENT, 60);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int count = stack.get(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT);
        int leftCount = stack.get(ModComponentInit.BOLT_COUNT_COMPONENT);
        if (!world.isClient) {
            CrossbowItem.LoadingSounds loadingSounds = this.getLoadingSounds(stack);
            float f = getModifiedPullProgress(stack.getMaxUseTime(user) - remainingUseTicks, stack);
            if (f >= 0.2F && leftCount == 0 && !played0) {
                loadingSounds.start()
                        .ifPresent(sound -> world.playSound(null, user.getX(), user.getY(), user.getZ(), (SoundEvent)sound.value(), SoundCategory.PLAYERS, 0.5F, 1.0F));
                played0 = true;
            }

            if (f >= 0.5F && leftCount == 0 && !played1) {
                loadingSounds.mid()
                        .ifPresent(sound -> world.playSound(null, user.getX(), user.getY(), user.getZ(), (SoundEvent)sound.value(), SoundCategory.PLAYERS, 0.5F, 1.0F));
                played1 = true;
            }
            if (f == 1.0F && leftCount == 0 && !played2) {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
                        SoundCategory.PLAYERS,
                        1.5F,
                        1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                );
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_PISTON_CONTRACT,
                        SoundCategory.PLAYERS,
                        0.5F,
                        2.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                );
                played2 = true;
            }
            if (count < Math.min(pulltime, leftCount) && leftCount > 0) {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_PISTON_CONTRACT,
                        SoundCategory.PLAYERS,
                        0.2F,
                        2.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                );
            }
            if (count >= Math.min(pulltime, leftCount) && leftCount > 0 && !played2) {
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_SMITHING_TABLE_USE,
                        SoundCategory.PLAYERS,
                        1.0F,
                        2.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
                );
                played2 = true;
            }
        }
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (leftCount > 0 && stack.get(ModComponentInit.CHARGING_COMPONENT)) {
            stack.set(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, Math.min(count + 1, Math.min(pulltime, leftCount)));
        }
        float f = getModifiedPullProgress(i, stack);
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity playerEntity && !stack.get(ModComponentInit.CHARGING_COMPONENT)
                && (playerEntity.getMainHandStack().equals(stack) || playerEntity.getOffHandStack().equals(stack))
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
        } else if (!stack.get(ModComponentInit.CHARGING_COMPONENT)) {
            stack.set(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT, 0);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    CrossbowItem.LoadingSounds getLoadingSounds(ItemStack stack) {
        return (CrossbowItem.LoadingSounds) EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.CROSSBOW_CHARGING_SOUNDS)
                .orElse(DEFAULT_LOADING_SOUNDS);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        ChargedProjectilesComponent chargedProjectilesComponent = stack.get(DataComponentTypes.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty()) {
            ItemStack itemStack = (ItemStack)chargedProjectilesComponent.getProjectiles().get(0);
            tooltip.add(Text.translatable("item.minecraft.crossbow.projectile").append(ScreenTexts.SPACE).append(itemStack.toHoverableText()));
            if (type.isAdvanced() && itemStack.isOf(Items.FIREWORK_ROCKET)) {
                List<Text> list = Lists.<Text>newArrayList();
                Items.FIREWORK_ROCKET.appendTooltip(itemStack, context, list, type);
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        list.set(i, Text.literal("  ").append((Text)list.get(i)).formatted(Formatting.GRAY));
                    }

                    tooltip.addAll(list);
                }
            }
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.isOf(this);
    }

    @Override
    public int getRange() {
        return 8;
    }

    public static record LoadingSounds(Optional<RegistryEntry<SoundEvent>> start, Optional<RegistryEntry<SoundEvent>> mid, Optional<RegistryEntry<SoundEvent>> end) {
        public static final Codec<CrossbowItem.LoadingSounds> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                SoundEvent.ENTRY_CODEC.optionalFieldOf("start").forGetter(CrossbowItem.LoadingSounds::start),
                                SoundEvent.ENTRY_CODEC.optionalFieldOf("mid").forGetter(CrossbowItem.LoadingSounds::mid),
                                SoundEvent.ENTRY_CODEC.optionalFieldOf("end").forGetter(CrossbowItem.LoadingSounds::end)
                        )
                        .apply(instance, CrossbowItem.LoadingSounds::new)
        );
    }
}
