package net.stln.launchersandarrows.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import org.spongepowered.asm.mixin.Unique;

public interface AttriburteDataTracker {
    public int getTracker(int id);
}
