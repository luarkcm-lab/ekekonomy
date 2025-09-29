package com.ekekonomia;

import com.ekekonomia.commands.EconomyCommands;
import com.ekekonomia.events.MobDeathHandler;
import com.ekekonomia.shop.ShopCategory;
import com.ekekonomia.shop.ChestShopScreenHandler;
import com.ekekonomia.shop.ItemDetailScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
// import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EkekonomiaMod implements ModInitializer {
    public static final String MOD_ID = "ekekonomia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    public static final ScreenHandlerType<ChestShopScreenHandler> SHOP_SCREEN_HANDLER = 
        new ScreenHandlerType<>(ChestShopScreenHandler::new, null);
    
    public static final ScreenHandlerType<ItemDetailScreenHandler> ITEM_DETAIL_SCREEN_HANDLER = 
        new ScreenHandlerType<>((syncId, playerInventory) -> new ItemDetailScreenHandler(syncId, playerInventory, null), null);
    

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Ekekonomia mod with external APIs");
        
        
        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            EconomyCommands.register(dispatcher, registryAccess);
        });
        
        // Register mob death handler
        MobDeathHandler.register();
        
        // Initialize shop items
        ShopCategory.initializeShopItems();
        LOGGER.info("Shop items initialized");
        
        // Register screen handlers
        net.minecraft.registry.Registry.register(net.minecraft.registry.Registries.SCREEN_HANDLER, 
            Identifier.of(MOD_ID, "shop"), SHOP_SCREEN_HANDLER);
        LOGGER.info("Shop screen handler registered");
        
        net.minecraft.registry.Registry.register(net.minecraft.registry.Registries.SCREEN_HANDLER, 
            Identifier.of(MOD_ID, "item_detail"), ITEM_DETAIL_SCREEN_HANDLER);
        LOGGER.info("Item detail screen handler registered");
        
        LOGGER.info("Ekekonomia mod initialized successfully with external APIs");
    }
}