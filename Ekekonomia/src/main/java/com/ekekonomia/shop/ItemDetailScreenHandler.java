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

public class ItemDetailScreenHandler extends ScreenHandler {
    private final Inventory detailInventory;
    private final ShopItem shopItem;
    private int quantity = 1;

    public ItemDetailScreenHandler(int syncId, PlayerInventory playerInventory, ShopItem shopItem) {
        super(EkekonomiaMod.ITEM_DETAIL_SCREEN_HANDLER, syncId);
        this.shopItem = shopItem != null ? shopItem : ShopCategory.BLOQUES.getItems().values().iterator().next();
        this.detailInventory = new SimpleInventory(27); // 3 filas para controles

        // Slots de controles (3 filas superiores)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new DetailSlot(detailInventory, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }

        // Slots del jugador (3 filas inferiores)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Hotbar del jugador
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        populateDetailScreen();
    }

    private void populateDetailScreen() {
        // Limpiar inventario
        for (int i = 0; i < 27; i++) {
            detailInventory.setStack(i, ItemStack.EMPTY);
        }

        // Validar que shopItem no sea null
        if (shopItem == null || shopItem.getItem() == null) {
            return;
        }

        // Item central (slot 13 - centro de la segunda fila)
        ItemStack centerItem = new ItemStack(shopItem.getItem(), quantity);
        detailInventory.setStack(13, centerItem);

        // Botones de cantidad verde (aumentar) - alrededor del centro
        ItemStack add64 = new ItemStack(net.minecraft.item.Items.GREEN_STAINED_GLASS, 64);
        detailInventory.setStack(10, add64); // +64 (izquierda)

        ItemStack add10 = new ItemStack(net.minecraft.item.Items.GREEN_STAINED_GLASS, 10);
        detailInventory.setStack(11, add10); // +10 (izquierda)

        ItemStack add1 = new ItemStack(net.minecraft.item.Items.GREEN_STAINED_GLASS, 1);
        detailInventory.setStack(12, add1); // +1 (izquierda)

        // Botones de cantidad rojo (disminuir) - derecha del centro
        ItemStack remove1 = new ItemStack(net.minecraft.item.Items.RED_STAINED_GLASS, 1);
        detailInventory.setStack(14, remove1); // -1 (derecha)

        ItemStack remove10 = new ItemStack(net.minecraft.item.Items.RED_STAINED_GLASS, 10);
        detailInventory.setStack(15, remove10); // -10 (derecha)

        ItemStack remove64 = new ItemStack(net.minecraft.item.Items.RED_STAINED_GLASS, 64);
        detailInventory.setStack(16, remove64); // -64 (derecha)

        // Botones de confirmación (tercera fila)
        ItemStack confirm = new ItemStack(net.minecraft.item.Items.GREEN_CONCRETE);
        detailInventory.setStack(21, confirm); // Confirmar (un bloque más a la izquierda)

        ItemStack cancel = new ItemStack(net.minecraft.item.Items.RED_CONCRETE);
        detailInventory.setStack(23, cancel); // Cancelar (un bloque más a la izquierda)
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        if (slotIndex >= 0 && slotIndex < 27) {
            ItemStack stack = this.detailInventory.getStack(slotIndex);
            if (!stack.isEmpty()) {
                handleControlClick(player, slotIndex, stack);
            }
        } else {
            super.onSlotClick(slotIndex, button, actionType, player);
        }
    }

    private void handleControlClick(PlayerEntity player, int slotIndex, ItemStack stack) {
        // Validar que shopItem no sea null
        if (shopItem == null || shopItem.getItem() == null) {
            return;
        }
        
        if (stack.getItem() == net.minecraft.item.Items.GREEN_STAINED_GLASS) {
            // Botones de aumentar cantidad
            if (slotIndex == 10) { // +64
                quantity = Math.min(quantity + 64, 64);
            } else if (slotIndex == 11) { // +10
                quantity = Math.min(quantity + 10, 64);
            } else if (slotIndex == 12) { // +1
                quantity = Math.min(quantity + 1, 64);
            }
            populateDetailScreen(); // Actualizar la pantalla
        } else if (stack.getItem() == net.minecraft.item.Items.RED_STAINED_GLASS) {
            // Botones de disminuir cantidad
            if (slotIndex == 14) { // -1
                quantity = Math.max(quantity - 1, 1);
            } else if (slotIndex == 15) { // -10
                quantity = Math.max(quantity - 10, 1);
            } else if (slotIndex == 16) { // -64
                quantity = Math.max(quantity - 64, 1);
            }
            populateDetailScreen(); // Actualizar la pantalla
        } else if (stack.getItem() == net.minecraft.item.Items.GREEN_CONCRETE) {
            // Confirmar compra (slot 21)
            if (slotIndex == 21 && shopItem != null) {
                long totalPrice = shopItem.getBuyPrice() * quantity;
                String itemName = shopItem.getDisplayName();
                
                if (PersistentEconomyManager.removeMoney(player, totalPrice)) {
                    ItemStack itemToGive = new ItemStack(shopItem.getItem(), quantity);
                    if (!player.getInventory().insertStack(itemToGive)) {
                        player.dropItem(itemToGive, false);
                    }
                    player.sendMessage(Text.literal("§6[Tienda] §fHas comprado §a" + quantity + "x " + itemName + "§f por §a" + totalPrice + "§f monedas."), false);
                    if (player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
                        serverPlayer.closeHandledScreen(); // Cerrar la pantalla de detalle
                    }
                } else {
                    player.sendMessage(Text.literal("§6[Tienda] §cNo tienes suficientes monedas para comprar §f" + quantity + "x " + itemName + "§c."), false);
                }
            }
        } else if (stack.getItem() == net.minecraft.item.Items.RED_CONCRETE) {
            // Cancelar (slot 23)
            if (slotIndex == 23) {
                if (player instanceof net.minecraft.server.network.ServerPlayerEntity serverPlayer) {
                    serverPlayer.closeHandledScreen();
                }
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY; // No permitir movimiento de items
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    // Slot personalizado para controles
    private static class DetailSlot extends Slot {
        public DetailSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }
    }
}
