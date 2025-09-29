package com.ekekonomia.client.screen;

import com.ekekonomia.shop.ChestShopScreenHandler;
import com.ekekonomia.shop.ShopCategory;
import com.ekekonomia.shop.ShopItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChestShopScreen extends HandledScreen<ChestShopScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("ekekonomia", "textures/gui/shop_background.png");

    public ChestShopScreen(ChestShopScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 276; // Altura para cofre doble
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
                context.fill(x, y, x + this.backgroundWidth, y + this.backgroundHeight, 0xFF8B4513); // Color marrón
                context.fill(x + 1, y + 1, x + this.backgroundWidth - 1, y + this.backgroundHeight - 1, 0xFF654321); // Borde más oscuro
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
            ItemStack stack = this.focusedSlot.getStack();
            
            // Check if it's a shop slot (first 54 slots are shop slots)
            if (this.focusedSlot.id >= 0 && this.focusedSlot.id < 54) {
                ShopItem shopItem = getShopItem(stack.getItem());
                if (shopItem != null) {
                    long buyPrice = shopItem.getBuyPrice();
                    long sellPrice = shopItem.getSellPrice();
                    String itemName = net.minecraft.registry.Registries.ITEM.getId(stack.getItem()).getPath().replace("_", " ");
                    
                    // Create compact tooltip with prices
                    Text tooltip = Text.literal("§6" + itemName)
                        .append(Text.literal("\n§aComprar: §f" + buyPrice + "§7 | §cVender: §f" + sellPrice))
                        .append(Text.literal("\n§7Click: Detalle"));

                    context.drawTooltip(this.textRenderer, tooltip, x, y);
                    return;
                }
            }
        }
        
        // Default tooltip for other slots
        super.drawMouseoverTooltip(context, x, y);
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
}