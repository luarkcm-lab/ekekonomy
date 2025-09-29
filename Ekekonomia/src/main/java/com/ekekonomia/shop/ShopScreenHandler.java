package com.ekekonomia.shop;

import com.ekekonomia.economy.PersistentEconomyManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ShopScreenHandler extends ScreenHandler {
    private final Inventory shopInventory;
    private final PlayerInventory playerInventory;
    private final PlayerEntity player;
    
    public ShopScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(com.ekekonomia.EkekonomiaMod.SHOP_SCREEN_HANDLER, syncId);
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;
        this.shopInventory = new SimpleInventory(27); // 3 filas de 9 slots
        
        // Configurar slots del inventario del jugador (3 filas de 9)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        
        // Hotbar del jugador
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
        
        // Slots de la tienda (3 filas de 9)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new ShopSlot(shopInventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }
        
        // Llenar la tienda con items
        populateShop();
    }
    
    private void populateShop() {
        int slot = 0;
        for (ShopCategory category : ShopCategory.values()) {
            for (ShopItem shopItem : category.getItems().values()) {
                if (slot >= 27) break; // Solo 27 slots
                
                ItemStack itemStack = new ItemStack(shopItem.getItem());
                shopInventory.setStack(slot, itemStack);
                slot++;
            }
        }
    }
    
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
    
    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }
    
    // Slot personalizado para la tienda
    private static class ShopSlot extends Slot {
        public ShopSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }
        
        @Override
        public boolean canInsert(ItemStack stack) {
            return false; // No se pueden insertar items en la tienda
        }
        
        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false; // No se pueden tomar items directamente
        }
    }
}