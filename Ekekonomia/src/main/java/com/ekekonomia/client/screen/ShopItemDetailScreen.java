package com.ekekonomia.client.screen;

import com.ekekonomia.economy.PersistentEconomyManager;
import com.ekekonomia.shop.ShopItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopItemDetailScreen extends Screen {
    private final ShopItem shopItem;
    private final List<ButtonWidget> quantityButtons = new ArrayList<>();
    private long playerBalance;
    private int selectedQuantity = 1;
    
    public ShopItemDetailScreen(ShopItem shopItem) {
        super(Text.literal("§6§l" + shopItem.getDisplayName()));
        this.shopItem = shopItem;
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Obtener saldo del jugador
        if (this.client != null && this.client.player != null) {
            this.playerBalance = PersistentEconomyManager.getBalance(this.client.player);
        }
        
        // Botón de volver
        ButtonWidget backButton = ButtonWidget.builder(
            Text.literal("§7← Volver"),
            button -> this.close()
        ).dimensions(0, 0, 80, 20).build();
        
        backButton.setPosition(20, 20);
        this.addDrawableChild(backButton);
        
        // Botones de cantidad rápida
        int[] quantities = {1, 10, 32, 64, 128, 256};
        GridWidget quantityGrid = new GridWidget();
        quantityGrid.setColumnSpacing(5);
        quantityGrid.setRowSpacing(5);
        
        for (int i = 0; i < quantities.length; i++) {
            int quantity = quantities[i];
            ButtonWidget quantityButton = ButtonWidget.builder(
                Text.literal("§f" + quantity),
                button -> this.selectedQuantity = quantity
            ).dimensions(0, 0, 40, 20).build();
            
            quantityGrid.add(quantityButton, i % 3, i / 3);
            quantityButtons.add(quantityButton);
        }
        
        // Posicionar el grid de cantidades
        quantityGrid.refreshPositions();
        SimplePositioningWidget.setPos(quantityGrid, 0, 0, this.width, this.height, 0.5f, 0.3f);
        
        // Botones de compra y venta
        ButtonWidget buyButton = ButtonWidget.builder(
            Text.literal("§aComprar §f" + selectedQuantity + " §7(" + (shopItem.getBuyPrice() * selectedQuantity) + " monedas)"),
            button -> buyItem(selectedQuantity)
        ).dimensions(0, 0, 150, 30).build();
        
        ButtonWidget sellButton = ButtonWidget.builder(
            Text.literal("§cVender §f" + selectedQuantity + " §7(" + (shopItem.getSellPrice() * selectedQuantity) + " monedas)"),
            button -> sellItem(selectedQuantity)
        ).dimensions(0, 0, 150, 30).build();
        
        // Posicionar botones de compra/venta
        buyButton.setPosition(this.width / 2 - 160, this.height - 100);
        sellButton.setPosition(this.width / 2 + 10, this.height - 100);
        
        this.addDrawableChild(buyButton);
        this.addDrawableChild(sellButton);
        
        // Agregar todos los botones de cantidad
        for (ButtonWidget button : quantityButtons) {
            this.addDrawableChild(button);
        }
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Fondo sólido simple
        context.fill(0, 0, this.width, this.height, 0xFF1E1E1E);
        
        // Título
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Saldo del jugador
        String balanceText = "§fSaldo: §a" + this.playerBalance + "§f monedas";
        context.drawCenteredTextWithShadow(this.textRenderer, balanceText, this.width / 2, 40, 0xFFFFFF);
        
        // Información del item
        String itemInfo = "§fItem: §e" + shopItem.getDisplayName();
        context.drawCenteredTextWithShadow(this.textRenderer, itemInfo, this.width / 2, 60, 0xFFFFFF);
        
        String priceInfo = "§aComprar: §f" + shopItem.getBuyPrice() + "§7 | §cVender: §f" + shopItem.getSellPrice();
        context.drawCenteredTextWithShadow(this.textRenderer, priceInfo, this.width / 2, 80, 0xFFFFFF);
        
        // Cantidad seleccionada
        String quantityInfo = "§fCantidad seleccionada: §e" + selectedQuantity;
        context.drawCenteredTextWithShadow(this.textRenderer, quantityInfo, this.width / 2, 100, 0xFFFFFF);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void buyItem(int quantity) {
        if (this.client != null && this.client.player != null) {
            long totalCost = shopItem.getBuyPrice() * quantity;
            if (playerBalance >= totalCost) {
                // Aquí implementarías la lógica de compra
                this.client.player.sendMessage(Text.literal("§6[Ekekonomia] §fComprando " + quantity + " " + shopItem.getDisplayName() + " por " + totalCost + " monedas"), false);
            } else {
                this.client.player.sendMessage(Text.literal("§6[Ekekonomia] §cNo tienes suficiente dinero"), false);
            }
        }
    }
    
    private void sellItem(int quantity) {
        if (this.client != null && this.client.player != null) {
            long totalValue = shopItem.getSellPrice() * quantity;
            // Aquí implementarías la lógica de venta
            this.client.player.sendMessage(Text.literal("§6[Ekekonomia] §fVendiendo " + quantity + " " + shopItem.getDisplayName() + " por " + totalValue + " monedas"), false);
        }
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}