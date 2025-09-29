package com.ekekonomia.currency;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;

public class PlayerCurrencyDataTracker {
    public static final TrackedData<NbtCompound> CURRENCY_DATA = DataTracker.registerData(
        net.minecraft.entity.player.PlayerEntity.class,
        TrackedDataHandlerRegistry.NBT_COMPOUND
    );
}
