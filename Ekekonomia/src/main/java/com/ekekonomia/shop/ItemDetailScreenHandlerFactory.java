package com.ekekonomia.shop;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class ItemDetailScreenHandlerFactory implements NamedScreenHandlerFactory {
    private final ShopItem shopItem;

    public ItemDetailScreenHandlerFactory(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ItemDetailScreenHandler(syncId, inv, shopItem);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("§6" + shopItem.getDisplayName());
    }
}
