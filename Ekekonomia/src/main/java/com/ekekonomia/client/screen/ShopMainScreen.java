package com.ekekonomia.client.screen;

import com.ekekonomia.economy.PersistentEconomyManager;
import com.ekekonomia.shop.ShopCategory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopMainScreen extends Screen {
    private final List<ButtonWidget> categoryButtons = new ArrayList<>();
    private long playerBalance;
    
    public ShopMainScreen() {
        super(Text.literal("§6§lTienda Ekekonomia"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Obtener saldo del jugador
        if (this.client != null && this.client.player != null) {
            this.playerBalance = PersistentEconomyManager.getBalance(this.client.player);
        }
        
        // Crear grid para los botones de categorías
        GridWidget grid = new GridWidget();
        grid.setColumnSpacing(10);
        grid.setRowSpacing(10);
        
        // Agregar botones para cada categoría
        int row = 0;
        int col = 0;
        for (ShopCategory category : ShopCategory.values()) {
            ButtonWidget categoryButton = ButtonWidget.builder(
                Text.literal(category.getColor() + category.getDisplayName()),
                button -> openCategoryScreen(category)
            ).dimensions(0, 0, 120, 30).build();
            
            grid.add(categoryButton, col, row);
            categoryButtons.add(categoryButton);
            
            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
        
        // Posicionar el grid en el centro
        grid.refreshPositions();
        SimplePositioningWidget.setPos(grid, 0, 0, this.width, this.height, 0.5f, 0.3f);
        
        // Agregar botón de cerrar
        ButtonWidget closeButton = ButtonWidget.builder(
            Text.literal("§cCerrar"),
            button -> this.close()
        ).dimensions(0, 0, 100, 20).build();
        
        closeButton.setPosition(this.width / 2 - 50, this.height - 40);
        this.addDrawableChild(closeButton);
        
        // Agregar todos los botones de categorías
        for (ButtonWidget button : categoryButtons) {
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
        context.drawCenteredTextWithShadow(this.textRenderer, "§7Selecciona una categoría para ver los items", this.width / 2, 60, 0xAAAAAA);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    private void openCategoryScreen(ShopCategory category) {
        if (this.client != null) {
            this.client.setScreen(new ShopCategoryScreen(category));
        }
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}