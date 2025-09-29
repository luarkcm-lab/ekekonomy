package com.ekekonomia.client;

import com.ekekonomia.config.EkekonomiaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ShopButton {
    
    public static void render(DrawContext context, MinecraftClient client) {
        if (!EkekonomiaConfig.showShopButton) return;
        if (client.currentScreen == null) return;
        
        // Position in top-left corner
        int x = 10;
        int y = 10;
        int width = 80;
        int height = 20;
        
        // Draw button background
        context.fill(x, y, x + width, y + height, 0x80000000); // Semi-transparent black
        context.drawBorder(x, y, width, height, 0xFF00FF00); // Green border
        
        // Draw button text
        String buttonText = "Tienda";
        int textX = x + (width - client.textRenderer.getWidth(buttonText)) / 2;
        int textY = y + (height - client.textRenderer.fontHeight) / 2;
        context.drawText(client.textRenderer, buttonText, textX, textY, 0xFFFFFF, false);
        
        // Check if mouse is over button (simple collision detection)
        int mouseX = (int) (client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getWidth());
        int mouseY = (int) (client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight());
        
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            // Draw hover effect
            context.fill(x, y, x + width, y + height, 0x4000FF00); // Semi-transparent green
        }
    }
    
    public static boolean isMouseOverButton(MinecraftClient client, int mouseX, int mouseY) {
        int x = 10;
        int y = 10;
        int width = 80;
        int height = 20;
        
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
