package net.stln.launchersandarrows.item.launcher;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.projectile.ItemProjectile;
import net.stln.launchersandarrows.sound.SoundInit;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CrossLauncherItem extends CrossbowItem {

    private static final float DEFAULT_PULL_TIME = 1.25F;
    public static final int RANGE = 8;
    private boolean charged = false;
    private boolean loaded = false;
    private static final float CHARGE_PROGRESS = 0.2F;
    private static final float LOAD_PROGRESS = 0.5F;
    private static final float DEFAULT_SPEED = 3.15F;
    private static final float FIREWORK_ROCKET_SPEED = 1.6F;
    public static final float field_49258 = 1.6F;
    private static final LoadingSounds DEFAULT_LOADING_SOUNDS = new LoadingSounds(
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_START),
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE),
            Optional.of(SoundEvents.ITEM_CROSSBOW_LOADING_END)
    );

    public static final Predicate<ItemStack> CROSSLAUNCHER_HELD_PROJECTILES = BOW_PROJECTILES
            .or(stack -> stack.isOf(Items.FIREWORK_ROCKET))
            .or(stack -> stack.isOf(Items.POTION))
            .or(stack -> stack.isOf(Items.SPLASH_POTION))
            .or(stack -> stack.isOf(Items.LINGERING_POTION))
            .or(stack -> stack.isOf(Items.TORCH))
            .or(stack -> stack.isOf(Items.BLAZE_ROD))
            .or(stack -> stack.isOf(Items.WIND_CHARGE))
            .or(stack -> stack.isOf(Items.FIRE_CHARGE))
            .or(stack -> stack.isOf(Items.GLOW_INK_SAC))
            .or(stack -> stack.isOf(Items.INK_SAC))
            .or(stack -> stack.isOf(Items.AMETHYST_SHARD))
            .or(stack -> stack.isOf(Items.SLIME_BALL))
            .or(stack -> stack.isOf(Items.SNOWBALL))
            .or(stack -> stack.isOf(Items.EGG))
            .or(stack -> stack.isOf(Items.ENDER_PEARL))
            .or(stack -> stack.isOf(Items.TRIDENT))
            .or(stack -> stack.isOf(Items.DRAGON_BREATH))
            .or(stack -> stack.isOf(Items.END_ROD))
            .or(stack -> stack.isOf(Items.ENDER_EYE))
            .or(stack -> stack.isOf(Items.MAGMA_CREAM))
            .or(stack -> stack.isOf(Items.ECHO_SHARD))
            .or(stack -> stack.isOf(Items.HEART_OF_THE_SEA))
            .or(stack -> stack.isOf(Items.HEAVY_CORE))
            .or(stack -> stack.isOf(Items.POINTED_DRIPSTONE))
            .or(stack -> stack.isOf(Items.LIGHTNING_ROD));

    public CrossLauncherItem(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getHeldProjectiles() {
        return CROSSLAUNCHER_HELD_PROJECTILES;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ChargedProjectilesComponent chargedProjectilesComponent = itemStack.get(DataComponentTypes.CHARGED_PROJECTILES);
        if (chargedProjectilesComponent != null && !chargedProjectilesComponent.isEmpty()) {
            this.shootAll(world, user, hand, itemStack, getSpeed(chargedProjectilesComponent), 1.0F, null);
            if (getShootSound(chargedProjectilesComponent) != null) {
                user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(),
                        getShootSound(chargedProjectilesComponent), user.getSoundCategory(),
                        1.0F, 1.0F / (user.getRandom().nextFloat() * 0.5F + 1.8F) * (float) Math.sqrt(getSpeed(chargedProjectilesComponent)) + 0.43F);
            }
            return TypedActionResult.consume(itemStack);
        } else if (!user.getProjectileType(itemStack).isEmpty()) {
            this.charged = false;
            this.loaded = false;
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    protected void shootAll(
            ServerWorld world,
            LivingEntity shooter,
            Hand hand,
            ItemStack stack,
            List<ItemStack> projectiles,
            float speed,
            float divergence,
            boolean critical,
            @Nullable LivingEntity target
    ) {
        float f = EnchantmentHelper.getProjectileSpread(world, stack, shooter, 0.0F);
        float g = projectiles.size() == 1 ? 0.0F : 2.0F * f / (float)(projectiles.size() - 1);
        float h = (float)((projectiles.size() - 1) % 2) * g / 2.0F;
        float i = 1.0F;

        for (int j = 0; j < projectiles.size(); j++) {
            ItemStack itemStack = (ItemStack)projectiles.get(j);
            if (!itemStack.isEmpty()) {
                float k = h + i * (float)((j + 1) / 2) * g;
                i = -i;
                if (itemStack.isOf(Items.AMETHYST_SHARD)) {
                    for (int l = 0; l < 10; l++) {
                        ProjectileEntity projectileEntity = this.createArrowEntity(world, shooter, stack, itemStack, critical);
                        this.shoot(shooter, projectileEntity, j, speed, divergence * 10, k, target);
                        world.spawnEntity(projectileEntity);
                    }
                } else {
                    ProjectileEntity projectileEntity = this.createArrowEntity(world, shooter, stack, itemStack, critical);
                    this.shoot(shooter, projectileEntity, j, speed, divergence, k, target);
                    world.spawnEntity(projectileEntity);
                }
                stack.damage(this.getWeaponStackDamage(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                if (stack.isEmpty()) {
                    break;
                }
            }
        }
    }


    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        Vector3f vector3f;
        if (target != null) {
            double d = target.getX() - shooter.getX();
            double e = target.getZ() - shooter.getZ();
            double f = Math.sqrt(d * d + e * e);
            double g = target.getBodyY(0.3333333333333333) - projectile.getY() + f * 0.2F;
            vector3f = calcVelocity(shooter, new Vec3d(d, g, e), yaw);
        } else {
            Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis((double)(yaw * (float) (Math.PI / 180.0)), vec3d.x, vec3d.y, vec3d.z);
            Vec3d vec3d2 = shooter.getRotationVec(1.0F);
            vector3f = vec3d2.toVector3f().rotate(quaternionf);
        }

        projectile.setVelocity((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), speed, divergence);
        float h = 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.8F) + 0.53F;
        shooter.getWorld().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundInit.CROSSLAUNCHER, shooter.getSoundCategory(), 1.0F, h);
        shooter.getWorld().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.BLOCK_COPPER_BULB_PLACE, shooter.getSoundCategory(), 1.0F, h);
        shooter.getWorld().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.BLOCK_LANTERN_BREAK, shooter.getSoundCategory(), 1.0F, h);
        shooter.getWorld().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, shooter.getSoundCategory(), 1.0F, h - 0.7F);
    }

    private static Vector3f calcVelocity(LivingEntity shooter, Vec3d direction, float yaw) {
        Vector3f vector3f = direction.toVector3f().normalize();
        Vector3f vector3f2 = new Vector3f(vector3f).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double)vector3f2.lengthSquared() <= 1.0E-7) {
            Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
            vector3f2 = new Vector3f(vector3f).cross(vec3d.toVector3f());
        }

        Vector3f vector3f3 = new Vector3f(vector3f).rotateAxis((float) (Math.PI / 2), vector3f2.x, vector3f2.y, vector3f2.z);
        return new Vector3f(vector3f).rotateAxis(yaw * (float) (Math.PI / 180.0), vector3f3.x, vector3f3.y, vector3f3.z);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        float f = getPullProgress(i, stack, user);
        if (f >= 1.0F && !isCharged(stack) && loadProjectiles(user, stack)) {
            LoadingSounds loadingSounds = this.getLoadingSounds(stack);
            loadingSounds.end()
                    .ifPresent(
                            sound -> world.playSound(
                                    null,
                                    user.getX(),
                                    user.getY(),
                                    user.getZ(),
                                    (SoundEvent)sound.value(),
                                    user.getSoundCategory(),
                                    1.0F,
                                    1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F
                            )
                    );
        }
        float h = 1.0F / (user.getRandom().nextFloat() * 0.5F + 1.8F) + 0.53F;
        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_DISPENSER_FAIL, user.getSoundCategory(), 1.0F, h + 1.0F);
        user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_IRON_DOOR_OPEN, user.getSoundCategory(), 1.0F, h + 1.0F);
    }

    LoadingSounds getLoadingSounds(ItemStack stack) {
        return (LoadingSounds)EnchantmentHelper.getEffect(stack, EnchantmentEffectComponentTypes.CROSSBOW_CHARGING_SOUNDS)
                .orElse(DEFAULT_LOADING_SOUNDS);
    }

    private static boolean loadProjectiles(LivingEntity shooter, ItemStack crossbow) {
        List<ItemStack> list = load(crossbow, shooter.getProjectileType(crossbow), shooter);
        if (!list.isEmpty()) {
            crossbow.set(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.of(list));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {
        if (projectileStack.isOf(Items.FIREWORK_ROCKET)) {
            return new FireworkRocketEntity(world, projectileStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
        } else if (projectileStack.isOf(Items.POTION)
                || projectileStack.isOf(Items.SPLASH_POTION)
                || projectileStack.isOf(Items.LINGERING_POTION)) {
            PotionEntity potionEntity = new PotionEntity(world, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
            potionEntity.setItem(projectileStack);
            return potionEntity;

        } else if (projectileStack.isOf(Items.BLAZE_ROD)) {
            ProjectileEntity entity = new SmallFireballEntity(world, shooter, new Vec3d(0, 0, 0));
            entity.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
            return entity;

        } else if (projectileStack.isOf(Items.WIND_CHARGE)) {
            return new AbstractWindChargeEntity(EntityType.BREEZE_WIND_CHARGE, world, shooter, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ()) {
                @Override
                protected void createExplosion(Vec3d pos) {
                    this.getWorld()
                            .createExplosion(
                                    this,
                                    null,
                                    EXPLOSION_BEHAVIOR,
                                    pos.getX(),
                                    pos.getY(),
                                    pos.getZ(),
                                    10.0F,
                                    false,
                                    World.ExplosionSourceType.TRIGGER,
                                    ParticleTypes.GUST_EMITTER_SMALL,
                                    ParticleTypes.GUST_EMITTER_LARGE,
                                    SoundEvents.ENTITY_BREEZE_WIND_BURST
                            );
                }
            };

        } else if (projectileStack.isOf(Items.FIRE_CHARGE)) {
            ProjectileEntity entity = new FireballEntity(world, shooter, new Vec3d(0, 0, 0), 1);
            entity.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
            return entity;

        } else if (projectileStack.isOf(Items.SNOWBALL)) {
            return new SnowballEntity(world, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());

        } else if (projectileStack.isOf(Items.EGG)) {
            return new EggEntity(world, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());

        } else if (projectileStack.isOf(Items.ENDER_PEARL)) {
            return new EnderPearlEntity(world, shooter);

        } else if (projectileStack.isOf(Items.TRIDENT)) {
            return new TridentEntity(world, shooter, projectileStack);

        } else if (projectileStack.isOf(Items.DRAGON_BREATH)) {
            ProjectileEntity entity = new DragonFireballEntity(world, shooter, new Vec3d(0, 0, 0));
            entity.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
            return entity;

        } else if (projectileStack.isOf(Items.END_ROD)) {
            ProjectileEntity entity = new ShulkerBulletEntity(EntityType.SHULKER_BULLET, world);
            entity.setOwner(shooter);
            entity.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
            return entity;

        } else if (projectileStack.isIn(ItemTags.ARROWS)) {
            ProjectileEntity projectileEntity = super.createArrowEntity(world, shooter, weaponStack, projectileStack, critical);
            if (projectileEntity instanceof PersistentProjectileEntity persistentProjectileEntity) {
                persistentProjectileEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
            }
            return projectileEntity;
        } else {
            return new ItemProjectile(world, shooter, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ(), projectileStack);
        }
    }


    private static float getSpeed(ChargedProjectilesComponent stack) {
        float v;
        if (stack.contains(Items.POTION)) {
            v = 0.5F;
        } else if (stack.contains(Items.BLAZE_ROD) || stack.contains(Items.HEAVY_CORE)) {
            v = 1.0F;
        } else if (stack.contains(Items.POINTED_DRIPSTONE)) {
            v = 2.0F;
        } else if (stack.contains(Items.FIRE_CHARGE) || stack.contains(Items.DRAGON_BREATH) || stack.contains(Items.END_ROD)) {
            v = 2.5F;
        } else if (stack.contains(Items.SPLASH_POTION) || stack.contains(Items.LINGERING_POTION)
                || stack.contains(Items.SLIME_BALL) || stack.contains(Items.TORCH)
                || stack.contains(Items.GLOW_INK_SAC) || stack.contains(Items.INK_SAC)
                || stack.contains(Items.ENDER_EYE) || stack.contains(Items.MAGMA_CREAM)
                || stack.contains(Items.ECHO_SHARD) || stack.contains(Items.HEART_OF_THE_SEA)
                || stack.contains(Items.LIGHTNING_ROD)) {
            v = 3.0F;
        } else if (stack.contains(Items.WIND_CHARGE) || stack.contains(Items.SNOWBALL) || stack.contains(Items.EGG) || stack.contains(Items.ENDER_PEARL) || stack.contains(Items.TRIDENT)) {
            v = 5.0F;
        } else {
            v = 1.5F;
        }
        return v;
    }


    private static SoundEvent getShootSound(ChargedProjectilesComponent stack) {
        SoundEvent soundEvent;
        if (stack.contains(Items.POTION) || stack.contains(Items.SPLASH_POTION) || stack.contains(Items.LINGERING_POTION)) {
            soundEvent = SoundEvents.ITEM_BOTTLE_FILL;
        } else if (stack.contains(Items.SNOWBALL) || stack.contains(Items.EGG) || stack.contains(Items.ENDER_PEARL)) {
            soundEvent = SoundEvents.ENTITY_ENDER_PEARL_THROW;
        } else if (stack.contains(Items.BLAZE_ROD)) {
            soundEvent = SoundEvents.ENTITY_BLAZE_SHOOT;
        } else if (stack.contains(Items.WIND_CHARGE)) {
            soundEvent = SoundEvents.ENTITY_BREEZE_SHOOT;
        } else if (stack.contains(Items.FIRE_CHARGE)) {
            soundEvent = SoundEvents.ITEM_FIRECHARGE_USE;
        } else if (stack.contains(Items.TRIDENT)) {
            soundEvent = SoundEvents.ITEM_TRIDENT_THROW.value();
        } else if (stack.contains(Items.DRAGON_BREATH)) {
            soundEvent = SoundEvents.ENTITY_ENDER_DRAGON_SHOOT;
        } else if (stack.contains(Items.SLIME_BALL) || stack.contains(Items.MAGMA_CREAM)) {
            soundEvent = SoundEvents.ENTITY_SLIME_JUMP;
        } else if (stack.contains(Items.TORCH)) {
            soundEvent = SoundEvents.BLOCK_WOOD_BREAK;
        } else if (stack.contains(Items.GLOW_INK_SAC) || stack.contains(Items.INK_SAC)) {
            soundEvent = SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP;
        } else if (stack.contains(Items.END_ROD)) {
            soundEvent = SoundEvents.ENTITY_SHULKER_SHOOT;
        } else if (stack.contains(Items.AMETHYST_SHARD)) {
            soundEvent = SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK;
        } else if (stack.contains(Items.ENDER_EYE)) {
            soundEvent = SoundEvents.ENTITY_ENDER_EYE_DEATH;
        } else if (stack.contains(Items.ECHO_SHARD)) {
            soundEvent = SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK;
        } else if (stack.contains(Items.HEART_OF_THE_SEA)) {
            soundEvent = SoundEvents.BLOCK_CONDUIT_ACTIVATE;
        } else if (stack.contains(Items.HEAVY_CORE)) {
            soundEvent = SoundEvents.BLOCK_HEAVY_CORE_BREAK;
        } else if (stack.contains(Items.POINTED_DRIPSTONE)) {
            soundEvent = SoundEvents.BLOCK_DRIPSTONE_BLOCK_BREAK;
        } else if (stack.contains(Items.LIGHTNING_ROD)) {
            soundEvent = SoundEvents.ITEM_TRIDENT_THUNDER.value();
        } else {
            soundEvent = null;
        }
        return soundEvent;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            LoadingSounds loadingSounds = this.getLoadingSounds(stack);
            float f = (float)(stack.getMaxUseTime(user) - remainingUseTicks) / (float)getPullTime(stack, user);
            if (f < 0.2F) {
                this.charged = false;
                this.loaded = false;
            }

            if (f >= 0.2F && !this.charged) {
                this.charged = true;
                loadingSounds.start()
                        .ifPresent(sound -> world.playSound(null, user.getX(), user.getY(), user.getZ(), (SoundEvent)sound.value(), SoundCategory.PLAYERS, 0.5F, 1.0F));
            }

            if (f >= 0.5F && !this.loaded) {
                this.loaded = true;
                loadingSounds.mid()
                        .ifPresent(sound -> world.playSound(null, user.getX(), user.getY(), user.getZ(), (SoundEvent)sound.value(), SoundCategory.PLAYERS, 0.5F, 1.0F));
            }
        }
    }


    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return getPullTime(stack, user) + 3;
    }

    public static int getPullTime(ItemStack stack, LivingEntity user) {
        float f = EnchantmentHelper.getCrossbowChargeTime(stack, user, 0.5F);
        return MathHelper.floor(f * 20.0F);
    }

    private static float getPullProgress(int useTicks, ItemStack stack, LivingEntity user) {
        float f = (float)useTicks / (float)getPullTime(stack, user);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
}
