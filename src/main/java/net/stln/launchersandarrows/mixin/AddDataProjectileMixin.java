package net.stln.launchersandarrows.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.stln.launchersandarrows.entity.AttributedProjectile;
import net.stln.launchersandarrows.entity.BypassDamageCooldownProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class AddDataProjectileMixin extends Entity implements BypassDamageCooldownProjectile, AttributedProjectile {



    public AddDataProjectileMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract void initDataTracker(DataTracker.Builder builder);

    @Override
    public void setBypass(boolean flag) {
        this.dataTracker.set(BYPASS_DAMAGE_COOLDOWN, Boolean.valueOf(flag));
    }

    @Override
    public boolean getBypass() {
        if (this.dataTracker.get(BYPASS_DAMAGE_COOLDOWN) != null) {
            return this.dataTracker.get(BYPASS_DAMAGE_COOLDOWN);
        }
        return false;
    }

    @Override
    public void setAttribute(int id, int amount) {
        NbtCompound nbtCompound = this.getDataTracker().get(ATTRIBUTE_EFFECT);
        nbtCompound.putInt(String.valueOf(id), amount);
        this.dataTracker.set(ATTRIBUTE_EFFECT, nbtCompound);
    }

    @Override
    public int getAttribute(int id) {
        NbtCompound nbtCompound = this.dataTracker.get(ATTRIBUTE_EFFECT);
        if (nbtCompound.contains(String.valueOf(id))) {
            return nbtCompound.getInt(String.valueOf(id));
        }
        return 0;
    }

    @Override
    public int[] getAttributes() {
        int[] array = new int[7];
        for (int i = 0; i < 7; i++) {
            array[i] = getAttribute(i);
        }
        return array;
    }

    @Override
    public int[] getRatioAttributes() {
        int[] array = new int[7];
        for (int i = 0; i < 7; i++) {
            array[i] = getAttribute(-i - 1);
        }
        return array;
    }

    @Unique
    private static final TrackedData<Boolean> BYPASS_DAMAGE_COOLDOWN = DataTracker.registerData(AddDataProjectileMixin.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private static final TrackedData<NbtCompound> ATTRIBUTE_EFFECT = DataTracker.registerData(AddDataProjectileMixin.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    @Inject(method = "initDataTracker", at = {@At("TAIL")})
    private void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(BYPASS_DAMAGE_COOLDOWN, Boolean.FALSE);
        builder.add(ATTRIBUTE_EFFECT, new NbtCompound());
    }

    @Inject(method = "onEntityHit", at = {@At("HEAD")})
    private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity target = entityHitResult.getEntity();
        if (getBypass() && target instanceof LivingEntity) {
            target.timeUntilRegen = 0;
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = {@At("TAIL")})
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("BypassDamageCooldown", getBypass());
        int[] attributeArray = new int[7];
        for (int i = 0; i < 7; i++) {
            attributeArray[i] = getAttribute(i);
        }
        nbt.putIntArray("AttributeEffect", attributeArray);
        int[] attributeRatioArray = new int[7];
        for (int i = 0; i < 7; i++) {
            attributeRatioArray[i] = getAttribute(-i - 1);
        }
        nbt.putIntArray("AttributeRatioEffect", attributeRatioArray);
    }

    @Inject(method = "readCustomDataFromNbt", at = {@At("TAIL")})
    private void readCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("BypassDamageCooldown")) {
            setBypass(nbt.getBoolean("BypassDamageCooldown"));
        }
        if (nbt.contains("AttributeEffect")) {
            int[] attributeArray = nbt.getIntArray("AttributeEffect");
            for (int i = 0; i < 7; i++) {
                setAttribute(i, attributeArray[i]);
            }
            int[] attributeRatioArray = nbt.getIntArray("AttributeRatioEffect");
            for (int i = 0; i < 7; i++) {
                setAttribute(-i - 1, attributeRatioArray[i]);
            }
        }
    }
}
