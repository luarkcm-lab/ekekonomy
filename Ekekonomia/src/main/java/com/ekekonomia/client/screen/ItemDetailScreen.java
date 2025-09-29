package com.ekekonomia.client.screen;

import com.ekekonomia.shop.ItemDetailScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemDetailScreen extends HandledScreen<ItemDetailScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("ekekonomia", "textures/gui/item_detail_background.png");

    public ItemDetailScreen(ItemDetailScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 222;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        
        // Intentar cargar la textura personalizada usando diferentes métodos
        try {
            // Método 1: drawTexture con firma completa
            context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight, this.backgroundWidth, this.backgroundHeight);
        } catch (Exception e1) {
            try {
                // Método 2: drawTexture con firma simple
                context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
            } catch (Exception e2) {
                // Fallback a colores sólidos si ambos métodos fallan
                context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, 0xFF2F2F2F); // Color gris oscuro
                context.fill(x + 1, y + 1, x + this.backgroundWidth - 1, y + this.backgroundHeight - 1, 0xFF1F1F1F); // Borde más oscuro
            }
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // No hacer nada - usar solo la funcionalidad básica de HandledScreen
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
            // Mostrar tooltip personalizado para controles
            super.drawMouseoverTooltip(context, x, y);
        }
    }
}
