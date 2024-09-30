package net.stln.launchersandarrows.entity;

public interface AttributeDataTracker {
    int getAccumulationTracker(int id);

    boolean getEffectTracker(int id);

    int getEffectDuration(int id);
}
