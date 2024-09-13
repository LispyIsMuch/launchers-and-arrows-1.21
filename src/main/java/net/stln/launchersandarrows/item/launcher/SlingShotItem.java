package net.stln.launchersandarrows.item.launcher;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.projectile.ItemProjectile;
import net.stln.launchersandarrows.item.FovModifierItem;
import net.stln.launchersandarrows.item.bow.ModfiableBowItem;
import net.stln.launchersandarrows.sound.SoundInit;

import java.util.List;
import java.util.function.Predicate;

public class SlingShotItem extends ModfiableBowItem implements FovModifierItem {

    float fov = 1.0F;

    public static final Predicate<ItemStack> SLINGSHOT_HELD_PROJECTILES = (stack) -> {
        return stack.getItem() instanceof BlockItem && !stack.isOf(Items.HEAVY_CORE);
    };

    public SlingShotItem(Settings settings) {
        super(settings);
        pulltime = 10;
        slotsize = 0;
    }


    @Override
    public Predicate<ItemStack> getHeldProjectiles() {
        return SLINGSHOT_HELD_PROJECTILES;
    }
    @Override
    public Predicate<ItemStack> getProjectiles() {
        return SLINGSHOT_HELD_PROJECTILES;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound((Entity) user, user.getBlockPos(), SoundEvents.ITEM_CROSSBOW_LOADING_END.value(), SoundCategory.PLAYERS, 1f, 1.5f);
        world.playSound((Entity) user, user.getBlockPos(), SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE.value(), SoundCategory.PLAYERS, 1f, 1.5f);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        this.fov = 1.0f - getPullProgress(getMaxUseTime(stack, user) - remainingUseTicks) / 9.0f;
        if (user.isSneaking()) {
            this.fov *= 0.75f;
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            ItemStack itemStack = this.getProjectileTypeWithSelector(playerEntity, stack);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getModifiedPullProgress(i, stack);
                if (!((double)f < 0.3)) {
                    List<ItemStack> list = load(stack, itemStack, playerEntity);
                    if (world instanceof ServerWorld serverWorld && !list.isEmpty()) {
                        this.shootAll(serverWorld, playerEntity, playerEntity.getActiveHand(), stack, list, f * 6.0F, 1.0F, f == 1.0F, null);
                    }

                    world.playSound(
                            null,
                            playerEntity.getX(),
                            playerEntity.getY(),
                            playerEntity.getZ(),
                            SoundInit.BOW_RELEASE,
                            SoundCategory.PLAYERS,
                            1.5F,
                            2.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
                    );
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        return new ItemProjectile(world, shooter, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), projectileStack);
    }

    @Override
    public float getFov() {
        return this.fov;
    }

    @Override
    public void resetFov() {
        this.fov = 1.0F;
    }
}
