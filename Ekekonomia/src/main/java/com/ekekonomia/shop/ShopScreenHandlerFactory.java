package com.ekekonomia.shop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class ShopScreenHandlerFactory implements NamedScreenHandlerFactory {
    
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ShopScreenHandler(syncId, playerInventory);
    }
    
    @Override
    public Text getDisplayName() {
        return Text.literal("§6§lTienda Ekekonomia");
    }
}
