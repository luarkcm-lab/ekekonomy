package com.ekekonomia.client.screen;

import com.ekekonomia.economy.PersistentEconomyManager;
import com.ekekonomia.shop.ShopCategory;
import com.ekekonomia.shop.ShopItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopCategoryScreen extends Screen {
    private final ShopCategory category;
    private final List<ButtonWidget> itemButtons = new ArrayList<>();
    private long playerBalance;
    
    public ShopCategoryScreen(ShopCategory category) {
        super(Text.literal("§6§l" + category.getDisplayName()));
        this.category = category;
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
        
        // Crear grid para los items
        GridWidget grid = new GridWidget();
        grid.setColumnSpacing(5);
        grid.setRowSpacing(5);
        
        // Agregar botones para cada item en la categoría
        int row = 0;
        int col = 0;
        for (ShopItem shopItem : category.getItems().values()) {
            ButtonWidget itemButton = ButtonWidget.builder(
                Text.literal("§f" + shopItem.getDisplayName()),
                button -> openItemDetailScreen(shopItem)
            ).dimensions(0, 0, 100, 20).build();
            
            grid.add(itemButton, col, row);
            itemButtons.add(itemButton);
            
            col++;
            if (col >= 6) {
                col = 0;
                row++;
            }
        }
        
        // Posicionar el grid
        grid.refreshPositions();
        SimplePositioningWidget.setPos(grid, 0, 0, this.width, this.height, 0.5f, 0.4f);
        
        // Agregar todos los botones de items
        for (ButtonWidget button : itemButtons) {
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
        
        // Instrucciones
        context.drawCenteredTextWithShadow(this.textRenderer, "§7Selecciona un item para ver detalles", this.width / 2, 60, 0xAAAAAA);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void openItemDetailScreen(ShopItem shopItem) {
        if (this.client != null) {
            this.client.setScreen(new ShopItemDetailScreen(shopItem));
        }
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}