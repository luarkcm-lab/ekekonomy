package com.ekekonomia.shop;

import com.ekekonomia.EkekonomiaMod;
import com.ekekonomia.economy.PersistentEconomyManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class ChestShopScreenHandler extends ScreenHandler {
    private final Inventory shopInventory;
    private ShopCategory currentCategory = ShopCategory.BLOQUES;

    public ChestShopScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(EkekonomiaMod.SHOP_SCREEN_HANDLER, syncId);
        this.shopInventory = new SimpleInventory(54); // 6 filas x 9 columnas como un cofre doble

        // Slots del shop (6 filas superiores - cofre doble)
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new ShopSlot(shopInventory, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        // Slots del jugador (3 filas inferiores)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }

        // Hotbar del jugador
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 198));
        }

        // Poblar la tienda con items
        populateShop();
    }

    private void populateShop() {
        // Limpiar inventario
        for (int i = 0; i < 54; i++) {
            shopInventory.setStack(i, ItemStack.EMPTY);
        }

        // Primera fila: botones de categorías (slots 0-8)
        ShopCategory[] categories = ShopCategory.values();
        for (int i = 0; i < Math.min(categories.length, 9); i++) {
            ShopCategory category = categories[i];
            ItemStack categoryStack = new ItemStack(net.minecraft.item.Items.EMERALD);
            shopInventory.setStack(i, categoryStack);
        }

        // Filas 2-6: items de la categoría actual (slots 9-53)
        if (currentCategory != null) {
            var items = currentCategory.getItems().values().toArray(new ShopItem[0]);
            for (int i = 0; i < Math.min(items.length, 45); i++) { // 45 slots disponibles (9-53)
                ShopItem shopItem = items[i];
                ItemStack itemStack = new ItemStack(shopItem.getItem());
                shopInventory.setStack(i + 9, itemStack); // Empezar desde slot 9
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < 54) {
                // Clicked on a shop slot - comprar item
                ShopItem shopItem = getShopItem(originalStack.getItem());
                if (shopItem != null) {
                    long buyPrice = shopItem.getBuyPrice();
                    String itemName = net.minecraft.registry.Registries.ITEM.getId(originalStack.getItem()).getPath().replace("_", " ");
                    
                    if (PersistentEconomyManager.removeMoney(player, buyPrice)) {
                        ItemStack itemToGive = new ItemStack(originalStack.getItem());
                        if (!player.getInventory().insertStack(itemToGive)) {
                            player.dropItem(itemToGive, false);
                        }
                        player.sendMessage(Text.literal("§6[Tienda] §fHas comprado §a1 " + itemName + "§f por §a" + buyPrice + "§f monedas."), false);
                    } else {
                        player.sendMessage(Text.literal("§6[Tienda] §cNo tienes suficientes monedas para comprar §f" + itemName + "§c."), false);
                    }
                    return ItemStack.EMPTY;
                }
            } else {
                // Clicked on player inventory - vender item
                ShopItem shopItem = getShopItem(originalStack.getItem());
                if (shopItem != null) {
                    long sellPrice = shopItem.getSellPrice();
                    String itemName = net.minecraft.registry.Registries.ITEM.getId(originalStack.getItem()).getPath().replace("_", " ");
                    
                    if (player.getInventory().contains(new ItemStack(originalStack.getItem()))) {
                        player.getInventory().removeStack(player.getInventory().getSlotWithStack(new ItemStack(originalStack.getItem())), 1);
                        PersistentEconomyManager.addMoney(player, sellPrice);
                        player.sendMessage(Text.literal("§6[Tienda] §fHas vendido §a1 " + itemName + "§f por §a" + sellPrice + "§f monedas."), false);
                    } else {
                        player.sendMessage(Text.literal("§6[Tienda] §cNo tienes §f" + itemName + "§c en tu inventario para vender."), false);
                    }
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if (slotIndex >= 0 && slotIndex < 54) {
            // Clicked on a shop slot
            ItemStack shopStack = this.shopInventory.getStack(slotIndex);
            if (!shopStack.isEmpty()) {
                // Verificar si es un botón de categoría (primera fila, slots 0-8)
                if (slotIndex < 9 && shopStack.getItem() == net.minecraft.item.Items.EMERALD) {
                    // Click en botón de categoría
                    ShopCategory[] categories = ShopCategory.values();
                    if (slotIndex < categories.length) {
                        currentCategory = categories[slotIndex];
                        populateShop(); // Actualizar la tienda con la nueva categoría
                        // No mostrar mensaje en chat para evitar spam
                    }
                } else if (slotIndex >= 9) {
                    // Click en item de la tienda (slots 9-53) - abrir pantalla de detalle
                    ShopItem shopItem = getShopItem(shopStack.getItem());
                    if (shopItem != null) {
                        // Abrir pantalla de detalle del item
                        openItemDetailScreen(player, shopItem);
                    }
                }
            }
        } else {
            // Normal inventory interaction
            super.onSlotClick(slotIndex, button, actionType, player);
        }
    }

    private void openItemDetailScreen(PlayerEntity player, ShopItem shopItem) {
        // Abrir la pantalla de detalle del item
        if (player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer && shopItem != null) {
            serverPlayer.openHandledScreen(new ItemDetailScreenHandlerFactory(shopItem));
        }
    }

    private ShopItem getShopItem(net.minecraft.item.Item item) {
        for (ShopCategory category : ShopCategory.values()) {
            ShopItem shopItem = category.getItems().get(item);
            if (shopItem != null) {
                return shopItem;
            }
        }
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Slot personalizado para la tienda
    private static class ShopSlot extends Slot {
        public ShopSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false; // No se pueden tomar items directamente del shop
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false; // No se pueden insertar items en el shop
        }
    }
}