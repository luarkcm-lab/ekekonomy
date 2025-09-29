package com.ekekonomia.shop;

import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class ShopItem {
    private final Item item;
    private final long buyPrice;
    private final long sellPrice;

    public ShopItem(Item item, long buyPrice, long sellPrice) {
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public Item getItem() {
        return item;
    }

    public long getBuyPrice() {
        return buyPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public Text getDisplayText() {
        return Text.literal("§f" + item.getName().getString() + " §7- §aComprar: §f" + buyPrice + "§7 | §cVender: §f" + sellPrice);
    }
    
    public String getDisplayName() {
        return net.minecraft.registry.Registries.ITEM.getId(item).getPath().replace("_", " ");
    }
}