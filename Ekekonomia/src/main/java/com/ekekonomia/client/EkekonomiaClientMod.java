package com.ekekonomia.client;

import com.ekekonomia.EkekonomiaMod;
import com.ekekonomia.client.commands.ClientCommands;
import com.ekekonomia.client.KeyBindings;
import com.ekekonomia.client.screen.ChestShopScreen;
import com.ekekonomia.client.screen.ItemDetailScreen;
import com.ekekonomia.shop.ChestShopScreenHandler;
import com.ekekonomia.shop.ItemDetailScreenHandler;
import net.fabricmc.api.ClientModInitializer;
// import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EkekonomiaClientMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ekekonomia-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Ekekonomia client mod with external APIs");

        // Register shop screens - using Minecraft's screen handler registration
        net.minecraft.client.gui.screen.ingame.HandledScreens.register(EkekonomiaMod.SHOP_SCREEN_HANDLER, ChestShopScreen::new);
        net.minecraft.client.gui.screen.ingame.HandledScreens.register(EkekonomiaMod.ITEM_DETAIL_SCREEN_HANDLER, ItemDetailScreen::new);
        
        // Register client commands
        LOGGER.info("Registering client commands...");
        ClientCommands.register();
        LOGGER.info("Client commands registered successfully");
        
        // Register keybindings
        LOGGER.info("Registering keybindings...");
        KeyBindings.register();
        LOGGER.info("Keybindings registered successfully");

        // Register economy overlay and shop button (temporarily disabled due to API compatibility issues)
        // HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
        //     MinecraftClient client = MinecraftClient.getInstance();
        //     if (client.currentScreen instanceof InventoryScreen) {
        //         EconomyOverlay.render(drawContext, client);
        //         ShopButton.render(drawContext, client);
        //     }
        // });
        
        // Register tick handler for keybindings
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyBindings.tick(client);
        });

        LOGGER.info("Ekekonomia client mod initialized successfully with external APIs");
    }
}