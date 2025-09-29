package com.ekekonomia.shop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class ChestShopScreenHandlerFactory implements NamedScreenHandlerFactory {
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ChestShopScreenHandler(syncId, inv);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("🏪 Tienda Ekekonomia");
    }
}
