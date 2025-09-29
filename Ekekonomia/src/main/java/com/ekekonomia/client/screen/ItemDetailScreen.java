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
        
        // Dibujar la textura personalizada
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
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
