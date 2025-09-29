package com.ekekonomia.client;

import com.ekekonomia.config.EkekonomiaConfig;
import com.ekekonomia.economy.SimpleEconomyManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class EconomyOverlay {
    
    public static void render(DrawContext context, MinecraftClient client) {
        if (!EkekonomiaConfig.showMoneyCounter) return;
        if (client.player == null) return;
        
        // Get player money from server (this is a simplified version)
        long money = getPlayerMoney(client.player);
        
        // Position in top-right corner
        int x = client.getWindow().getScaledWidth() - 120;
        int y = 20;
        
        // Draw background rectangle
        context.fill(x - 5, y - 5, x + 110, y + 20, 0x80000000); // Semi-transparent black
        
        // Draw border
        context.drawBorder(x - 5, y - 5, 115, 25, 0xFF00FF00); // Green border
        
        // Draw money text
        String moneyText = "Dinero: " + money;
        context.drawText(client.textRenderer, moneyText, x, y, 0xFFFFFF, false);
    }
    
    private static long getPlayerMoney(PlayerEntity player) {
        // This is a simplified version - in a real implementation,
        // you would sync this data from server to client
        return SimpleEconomyManager.getBalance(player);
    }
}
