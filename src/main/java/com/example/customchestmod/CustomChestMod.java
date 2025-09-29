package com.example.customchestmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;

public class CustomChestMod implements ModInitializer {
    public static final Block CUSTOM_CHEST = new CustomChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST));

    @Override
    public void onInitialize() {
        BlockRegistry.register(new Identifier("customchestmod", "custom_chest"), CUSTOM_CHEST);
    }
}