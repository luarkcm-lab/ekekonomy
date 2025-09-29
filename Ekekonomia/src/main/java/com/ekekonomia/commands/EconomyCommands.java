package com.ekekonomia.commands;

import com.ekekonomia.EkekonomiaMod;
import com.ekekonomia.economy.PersistentEconomyManager;
import com.ekekonomia.shop.ShopCategory;
import com.ekekonomia.shop.ShopItem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EconomyCommands {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        // /eco command
        dispatcher.register(CommandManager.literal("eco")
            .then(CommandManager.literal("balance")
                .executes(EconomyCommands::checkBalance))
            .then(CommandManager.literal("set")
                .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                    .executes(EconomyCommands::setBalance)))
            .then(CommandManager.literal("add")
                .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                    .executes(EconomyCommands::addBalance)))
            .then(CommandManager.literal("remove")
                .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                    .executes(EconomyCommands::removeBalance)))
            .then(CommandManager.literal("pay")
                .then(CommandManager.argument("player", net.minecraft.command.argument.EntityArgumentType.player())
                    .then(CommandManager.argument("amount", LongArgumentType.longArg(0))
                        .executes(EconomyCommands::payPlayer))))
            .executes(EconomyCommands::checkBalance));
        
        // /shop command
        dispatcher.register(CommandManager.literal("shop")
            .then(CommandManager.argument("category", StringArgumentType.string())
                .executes(EconomyCommands::showCategory))
            .executes(EconomyCommands::openShop));
            
        // /shopgui command - Open GUI (DISABLED - Use /tienda instead)
        // dispatcher.register(CommandManager.literal("shopgui")
        //     .executes(EconomyCommands::openShopGUI));
            
        // /tienda command - Open Economy Shop GUI
        dispatcher.register(CommandManager.literal("tienda")
            .executes(EconomyCommands::openEconomyShopGUI));
        
        // /tienda-init command - Initialize data pack
        dispatcher.register(CommandManager.literal("tienda-init")
            .executes(EconomyCommands::initializeDataPack));
            
        // /buy command
        dispatcher.register(CommandManager.literal("buy")
            .then(CommandManager.argument("item", StringArgumentType.string())
                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                    .executes(EconomyCommands::buyItem))
                .executes(EconomyCommands::buyItemSingle)));
                
        // /sell command
        dispatcher.register(CommandManager.literal("sell")
            .then(CommandManager.argument("item", StringArgumentType.string())
                .then(CommandManager.argument("amount", IntegerArgumentType.integer(1))
                    .executes(EconomyCommands::sellItem))
                .executes(EconomyCommands::sellItemSingle)));
            
        // /money command (alias for /eco)
        dispatcher.register(CommandManager.literal("money")
            .executes(EconomyCommands::checkBalance));
            
        // /testmob command for testing mob drops
        dispatcher.register(CommandManager.literal("testmob")
            .executes(EconomyCommands::testMobDrop));
    }
    
    private static int checkBalance(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            PersistentEconomyManager.sendBalanceMessage(player);
            return 1;
        }
        return 0;
    }
    
    private static int setBalance(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        long amount = LongArgumentType.getLong(context, "amount");
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            if (PersistentEconomyManager.setBalance(player, amount)) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §fTu saldo se ha establecido a §a" + amount + "§f monedas."), false);
            } else {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cError al establecer el saldo."), false);
            }
            return 1;
        }
        return 0;
    }
    
    private static int addBalance(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        long amount = LongArgumentType.getLong(context, "amount");
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            if (PersistentEconomyManager.addMoney(player, amount)) {
                long newBalance = PersistentEconomyManager.getBalance(player);
                player.sendMessage(Text.literal("§6[Ekekonomia] §fSe han añadido §a" + amount + "§f monedas. Nuevo saldo: §a" + newBalance + "§f monedas."), false);
            } else {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cError al añadir dinero."), false);
            }
            return 1;
        }
        return 0;
    }
    
    private static int removeBalance(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        long amount = LongArgumentType.getLong(context, "amount");
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            if (PersistentEconomyManager.removeMoney(player, amount)) {
                long newBalance = PersistentEconomyManager.getBalance(player);
                player.sendMessage(Text.literal("§6[Ekekonomia] §fSe han removido §c" + amount + "§f monedas. Nuevo saldo: §a" + newBalance + "§f monedas."), false);
            } else {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cNo tienes suficiente dinero o error al remover dinero."), false);
            }
            return 1;
        }
        return 0;
    }
    
    private static int payPlayer(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity targetPlayer = net.minecraft.command.argument.EntityArgumentType.getPlayer(context, "player");
        long amount = LongArgumentType.getLong(context, "amount");
        
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            if (PersistentEconomyManager.transferMoney(player, targetPlayer, amount)) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §fHas enviado §a" + amount + "§f monedas a §e" + targetPlayer.getNameForScoreboard() + "§f."), false);
                targetPlayer.sendMessage(Text.literal("§6[Ekekonomia] §fHas recibido §a" + amount + "§f monedas de §e" + player.getNameForScoreboard() + "§f."), false);
            } else {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cNo tienes suficiente dinero o error en la transferencia."), false);
            }
            return 1;
        }
        return 0;
    }
    
    private static int openShop(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            long balance = PersistentEconomyManager.getBalance(player);
            
            player.sendMessage(Text.literal("§6§l========== TIENDA EKEKONOMIA =========="), false);
            player.sendMessage(Text.literal("§fTu saldo actual: §a" + balance + "§f monedas"), false);
            player.sendMessage(Text.literal(""), false);
            player.sendMessage(Text.literal("§fSelecciona una categoría escribiendo: §a/shop <categoría>"), false);
            player.sendMessage(Text.literal(""), false);
            
            // Mostrar todas las categorías disponibles
            for (ShopCategory category : ShopCategory.values()) {
                player.sendMessage(Text.literal("§7• " + category.getColor() + category.getDisplayName() + " §7- §f/shop " + category.name().toLowerCase()), false);
            }
            
            player.sendMessage(Text.literal(""), false);
            player.sendMessage(Text.literal("§fPara comprar: §a/buy <item> [cantidad]"), false);
            player.sendMessage(Text.literal("§fPara vender: §c/sell <item> [cantidad]"), false);
            player.sendMessage(Text.literal("§6§l====================================="), false);
            
            return 1;
        }
        return 0;
    }
    
    private static int testMobDrop(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            // Simulate a zombie kill (20 HP * 0.1 = 2 money)
            long testMoney = 2;
            PersistentEconomyManager.addMoney(player, testMoney);
            
            player.sendMessage(Text.literal("§6[Ekekonomia] §fSimulando muerte de zombie: +§a" + testMoney + "§f monedas"), false);
            long newBalance = PersistentEconomyManager.getBalance(player);
            player.sendMessage(Text.literal("§6[Ekekonomia] §fNuevo saldo: §a" + newBalance + "§f monedas"), false);
            return 1;
        }
        return 0;
    }
    
    private static int showCategory(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            String categoryName = StringArgumentType.getString(context, "category").toLowerCase();
            
            // Buscar la categoría
            ShopCategory category = null;
            for (ShopCategory cat : ShopCategory.values()) {
                if (cat.name().toLowerCase().equals(categoryName)) {
                    category = cat;
                    break;
                }
            }
            
            if (category == null) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cCategoría no encontrada. Usa §f/shop §cpara ver las categorías disponibles."), false);
                return 0;
            }
            
            long balance = PersistentEconomyManager.getBalance(player);
            
            player.sendMessage(Text.literal("§6§l========== " + category.getColor() + category.getDisplayName().toUpperCase() + " §6§l=========="), false);
            player.sendMessage(Text.literal("§fTu saldo actual: §a" + balance + "§f monedas"), false);
            player.sendMessage(Text.literal(""), false);
            
            // Mostrar items de la categoría
            if (category.getItems().isEmpty()) {
                player.sendMessage(Text.literal("§cNo hay items disponibles en esta categoría."), false);
            } else {
                for (ShopItem shopItem : category.getItems().values()) {
                    String itemName = Registries.ITEM.getId(shopItem.getItem()).getPath().replace("_", " ");
                    player.sendMessage(Text.literal("§7• §f" + itemName + " §7- §aComprar: §f" + shopItem.getBuyPrice() + "§7 | §cVender: §f" + shopItem.getSellPrice()), false);
                }
            }
            
            player.sendMessage(Text.literal(""), false);
            player.sendMessage(Text.literal("§fPara comprar: §a/buy <item> [cantidad]"), false);
            player.sendMessage(Text.literal("§fPara vender: §c/sell <item> [cantidad]"), false);
            player.sendMessage(Text.literal("§6§l====================================="), false);
            
            return 1;
        }
        return 0;
    }
    
    private static int buyItemSingle(CommandContext<ServerCommandSource> context) {
        return buyItemWithAmount(context, 1);
    }
    
    private static int buyItem(CommandContext<ServerCommandSource> context) {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        return buyItemWithAmount(context, amount);
    }
    
    private static int buyItemWithAmount(CommandContext<ServerCommandSource> context, int amount) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            String itemName = StringArgumentType.getString(context, "item").toLowerCase();
            
            // Buscar el item en todas las categorías
            ShopItem shopItem = null;
            Item item = null;
            
            for (ShopCategory category : ShopCategory.values()) {
                for (Item categoryItem : category.getItems().keySet()) {
                    if (Registries.ITEM.getId(categoryItem).getPath().toLowerCase().contains(itemName)) {
                        shopItem = category.getItems().get(categoryItem);
                        item = categoryItem;
                        break;
                    }
                }
                if (shopItem != null) break;
            }
            
            if (shopItem == null) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cItem no encontrado en la tienda."), false);
                return 0;
            }
            
            long totalCost = shopItem.getBuyPrice() * amount;
            long balance = PersistentEconomyManager.getBalance(player);
            
            if (balance < totalCost) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cNo tienes suficiente dinero. Necesitas §f" + totalCost + "§c monedas pero solo tienes §f" + balance + "§c."), false);
                return 0;
            }
            
            // Realizar la compra
            if (PersistentEconomyManager.removeMoney(player, totalCost)) {
                ItemStack itemStack = new ItemStack(item, amount);
                player.getInventory().insertStack(itemStack);
                
                player.sendMessage(Text.literal("§6[Ekekonomia] §fHas comprado §a" + amount + "x " + Registries.ITEM.getId(item).getPath().replace("_", " ") + "§f por §a" + totalCost + "§f monedas."), false);
                long newBalance = PersistentEconomyManager.getBalance(player);
                player.sendMessage(Text.literal("§6[Ekekonomia] §fNuevo saldo: §a" + newBalance + "§f monedas."), false);
                return 1;
            } else {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cError al procesar la compra."), false);
                return 0;
            }
        }
        return 0;
    }
    
    private static int sellItemSingle(CommandContext<ServerCommandSource> context) {
        return sellItemWithAmount(context, 1);
    }
    
    private static int sellItem(CommandContext<ServerCommandSource> context) {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        return sellItemWithAmount(context, amount);
    }
    
    private static int sellItemWithAmount(CommandContext<ServerCommandSource> context, int amount) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            String itemName = StringArgumentType.getString(context, "item").toLowerCase();
            
            // Buscar el item en todas las categorías
            ShopItem shopItem = null;
            Item item = null;
            
            for (ShopCategory category : ShopCategory.values()) {
                for (Item categoryItem : category.getItems().keySet()) {
                    if (Registries.ITEM.getId(categoryItem).getPath().toLowerCase().contains(itemName)) {
                        shopItem = category.getItems().get(categoryItem);
                        item = categoryItem;
                        break;
                    }
                }
                if (shopItem != null) break;
            }
            
            if (shopItem == null) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cItem no encontrado en la tienda."), false);
                return 0;
            }
            
            // Verificar que el jugador tenga los items
            int availableAmount = 0;
            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == item) {
                    availableAmount += stack.getCount();
                }
            }
            
            if (availableAmount < amount) {
                player.sendMessage(Text.literal("§6[Ekekonomia] §cNo tienes suficientes items. Tienes §f" + availableAmount + "§c pero necesitas §f" + amount + "§c."), false);
                return 0;
            }
            
            // Remover items del inventario
            int remainingToRemove = amount;
            for (int i = 0; i < player.getInventory().size() && remainingToRemove > 0; i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == item) {
                    int toRemove = Math.min(remainingToRemove, stack.getCount());
                    stack.decrement(toRemove);
                    remainingToRemove -= toRemove;
                }
            }
            
            long totalEarned = shopItem.getSellPrice() * amount;
            PersistentEconomyManager.addMoney(player, totalEarned);
            
            player.sendMessage(Text.literal("§6[Ekekonomia] §fHas vendido §c" + amount + "x " + Registries.ITEM.getId(item).getPath().replace("_", " ") + "§f por §a" + totalEarned + "§f monedas."), false);
            long newBalance = PersistentEconomyManager.getBalance(player);
            player.sendMessage(Text.literal("§6[Ekekonomia] §fNuevo saldo: §a" + newBalance + "§f monedas."), false);
            
            return 1;
        }
        return 0;
    }
    
    private static int openShopGUI(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, playerEntity) -> new com.ekekonomia.shop.ShopScreenHandler(syncId, inventory),
                Text.literal("§6§lTienda Ekekonomia")
            ));
            return 1;
        }
        return 0;
    }
    
    private static int openEconomyShopGUI(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            // Abrir la tienda usando el GUI de cofre
            player.openHandledScreen(new com.ekekonomia.shop.ChestShopScreenHandlerFactory());
            player.sendMessage(Text.literal("§6[Ekekonomia] §fTienda abierta. Click izquierdo para comprar, click derecho para vender."), false);
            return 1;
        }
        return 0;
    }
    
    private static int initializeDataPack(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        if (source.getEntity() instanceof ServerPlayerEntity player) {
            try {
                // Inicializar el data pack
                source.getServer().getCommandManager().executeWithPrefix(source, "function ekekonomia:shop/init");
                player.sendMessage(Text.literal("§6[Ekekonomia] §fData pack inicializado. Usa §a/tienda §fpara abrir la tienda."), false);
                return 1;
            } catch (Exception e) {
                player.sendMessage(Text.literal("§c[Ekekonomia] §fError al inicializar el data pack: " + e.getMessage()), false);
                return 0;
            }
        }
        return 0;
    }
}
