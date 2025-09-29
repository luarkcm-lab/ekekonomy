package com.ekekonomia.client;

import com.ekekonomia.config.EkekonomiaConfig;
import com.ekekonomia.economy.SimpleEconomyManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class SimpleEconomyHUD {
    
    public static void showEconomyInfo(MinecraftClient client) {
        if (client.player == null) return;
        
        // Get player money
        long money = SimpleEconomyManager.getBalance(client.player);
        
        // Send message to chat (this is a simple alternative to overlay)
        client.player.sendMessage(
            net.minecraft.text.Text.literal("§6[Ekekonomia] §fTu saldo: §a" + money + "§f monedas. Usa §e/shop§f para ver la tienda."),
            false
        );
    }
}
