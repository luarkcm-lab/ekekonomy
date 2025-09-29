package com.ekekonomia.shop;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public enum ShopCategory {
    BLOQUES("Bloques", "§6"),
    COMIDA("Comida", "§a"),
    MINERALES("Minerales", "§7"),
    HERRAMIENTAS("Herramientas", "§e"),
    ARMAS_ARMADURAS("Armas y Armaduras", "§c"),
    AGRICULTURA("Agricultura", "§2"),
    TINTES("Tintes", "§d"),
    MISCELANEAS("Misceláneas", "§f"),
    BOTIN_MOBS("Botín de Mobs", "§5");

    private final String displayName;
    private final String color;
    private final Map<Item, ShopItem> items;

    ShopCategory(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
        this.items = new HashMap<>();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColor() {
        return color;
    }

    public Map<Item, ShopItem> getItems() {
        return items;
    }

    public static void initializeShopItems() {
        // BLOQUES - Items básicos de construcción
        BLOQUES.items.put(Items.STONE, new ShopItem(Items.STONE, 2, 1));
        BLOQUES.items.put(Items.COBBLESTONE, new ShopItem(Items.COBBLESTONE, 2, 1));
        BLOQUES.items.put(Items.DIRT, new ShopItem(Items.DIRT, 2, 1));
        BLOQUES.items.put(Items.SAND, new ShopItem(Items.SAND, 2, 1));
        BLOQUES.items.put(Items.GRAVEL, new ShopItem(Items.GRAVEL, 2, 1));
        BLOQUES.items.put(Items.CLAY, new ShopItem(Items.CLAY, 4, 2));
        BLOQUES.items.put(Items.GRASS_BLOCK, new ShopItem(Items.GRASS_BLOCK, 4, 2));
        BLOQUES.items.put(Items.OAK_LOG, new ShopItem(Items.OAK_LOG, 4, 2));
        BLOQUES.items.put(Items.BIRCH_LOG, new ShopItem(Items.BIRCH_LOG, 4, 2));
        BLOQUES.items.put(Items.SPRUCE_LOG, new ShopItem(Items.SPRUCE_LOG, 4, 2));
        BLOQUES.items.put(Items.OAK_PLANKS, new ShopItem(Items.OAK_PLANKS, 2, 1));
        BLOQUES.items.put(Items.STONE_BRICKS, new ShopItem(Items.STONE_BRICKS, 4, 2));
        BLOQUES.items.put(Items.BRICKS, new ShopItem(Items.BRICKS, 4, 2));
        BLOQUES.items.put(Items.GLASS, new ShopItem(Items.GLASS, 6, 3));

        // COMIDA - Alimentos básicos
        COMIDA.items.put(Items.BREAD, new ShopItem(Items.BREAD, 4, 2));
        COMIDA.items.put(Items.APPLE, new ShopItem(Items.APPLE, 2, 1));
        COMIDA.items.put(Items.CARROT, new ShopItem(Items.CARROT, 2, 1));
        COMIDA.items.put(Items.POTATO, new ShopItem(Items.POTATO, 2, 1));
        COMIDA.items.put(Items.WHEAT, new ShopItem(Items.WHEAT, 2, 1));
        COMIDA.items.put(Items.BEETROOT, new ShopItem(Items.BEETROOT, 2, 1));
        COMIDA.items.put(Items.MELON_SLICE, new ShopItem(Items.MELON_SLICE, 2, 1));
        COMIDA.items.put(Items.PUMPKIN, new ShopItem(Items.PUMPKIN, 4, 2));
        COMIDA.items.put(Items.SUGAR_CANE, new ShopItem(Items.SUGAR_CANE, 2, 1));
        COMIDA.items.put(Items.COOKED_BEEF, new ShopItem(Items.COOKED_BEEF, 10, 5));
        COMIDA.items.put(Items.COOKED_CHICKEN, new ShopItem(Items.COOKED_CHICKEN, 8, 4));
        COMIDA.items.put(Items.COOKED_PORKCHOP, new ShopItem(Items.COOKED_PORKCHOP, 8, 4));

        // MINERALES - Recursos mineros
        MINERALES.items.put(Items.COAL, new ShopItem(Items.COAL, 2, 1));
        MINERALES.items.put(Items.IRON_INGOT, new ShopItem(Items.IRON_INGOT, 10, 5));
        MINERALES.items.put(Items.GOLD_INGOT, new ShopItem(Items.GOLD_INGOT, 20, 10));
        MINERALES.items.put(Items.DIAMOND, new ShopItem(Items.DIAMOND, 100, 50));
        MINERALES.items.put(Items.EMERALD, new ShopItem(Items.EMERALD, 50, 25));
        MINERALES.items.put(Items.LAPIS_LAZULI, new ShopItem(Items.LAPIS_LAZULI, 4, 2));
        MINERALES.items.put(Items.REDSTONE, new ShopItem(Items.REDSTONE, 2, 1));
        MINERALES.items.put(Items.QUARTZ, new ShopItem(Items.QUARTZ, 6, 3));
        MINERALES.items.put(Items.NETHERITE_INGOT, new ShopItem(Items.NETHERITE_INGOT, 400, 200));
        MINERALES.items.put(Items.COPPER_INGOT, new ShopItem(Items.COPPER_INGOT, 4, 2));

        // HERRAMIENTAS - Herramientas de trabajo
        HERRAMIENTAS.items.put(Items.WOODEN_PICKAXE, new ShopItem(Items.WOODEN_PICKAXE, 10, 5));
        HERRAMIENTAS.items.put(Items.STONE_PICKAXE, new ShopItem(Items.STONE_PICKAXE, 20, 10));
        HERRAMIENTAS.items.put(Items.IRON_PICKAXE, new ShopItem(Items.IRON_PICKAXE, 50, 25));
        HERRAMIENTAS.items.put(Items.DIAMOND_PICKAXE, new ShopItem(Items.DIAMOND_PICKAXE, 200, 100));
        HERRAMIENTAS.items.put(Items.WOODEN_AXE, new ShopItem(Items.WOODEN_AXE, 6, 3));
        HERRAMIENTAS.items.put(Items.STONE_AXE, new ShopItem(Items.STONE_AXE, 12, 6));
        HERRAMIENTAS.items.put(Items.IRON_AXE, new ShopItem(Items.IRON_AXE, 30, 15));
        HERRAMIENTAS.items.put(Items.DIAMOND_AXE, new ShopItem(Items.DIAMOND_AXE, 120, 60));
        HERRAMIENTAS.items.put(Items.WOODEN_SHOVEL, new ShopItem(Items.WOODEN_SHOVEL, 4, 2));
        HERRAMIENTAS.items.put(Items.IRON_SHOVEL, new ShopItem(Items.IRON_SHOVEL, 20, 10));

        // ARMAS Y ARMADURAS - Equipamiento de combate
        ARMAS_ARMADURAS.items.put(Items.WOODEN_SWORD, new ShopItem(Items.WOODEN_SWORD, 6, 3));
        ARMAS_ARMADURAS.items.put(Items.STONE_SWORD, new ShopItem(Items.STONE_SWORD, 12, 6));
        ARMAS_ARMADURAS.items.put(Items.IRON_SWORD, new ShopItem(Items.IRON_SWORD, 30, 15));
        ARMAS_ARMADURAS.items.put(Items.DIAMOND_SWORD, new ShopItem(Items.DIAMOND_SWORD, 120, 60));
        ARMAS_ARMADURAS.items.put(Items.BOW, new ShopItem(Items.BOW, 20, 10));
        ARMAS_ARMADURAS.items.put(Items.ARROW, new ShopItem(Items.ARROW, 2, 1));
        ARMAS_ARMADURAS.items.put(Items.LEATHER_HELMET, new ShopItem(Items.LEATHER_HELMET, 10, 5));
        ARMAS_ARMADURAS.items.put(Items.LEATHER_CHESTPLATE, new ShopItem(Items.LEATHER_CHESTPLATE, 16, 8));
        ARMAS_ARMADURAS.items.put(Items.IRON_HELMET, new ShopItem(Items.IRON_HELMET, 40, 20));
        ARMAS_ARMADURAS.items.put(Items.IRON_CHESTPLATE, new ShopItem(Items.IRON_CHESTPLATE, 70, 35));

        // AGRICULTURA - Semillas y plantas
        AGRICULTURA.items.put(Items.WHEAT_SEEDS, new ShopItem(Items.WHEAT_SEEDS, 2, 1));
        AGRICULTURA.items.put(Items.CARROT, new ShopItem(Items.CARROT, 2, 1));
        AGRICULTURA.items.put(Items.POTATO, new ShopItem(Items.POTATO, 2, 1));
        AGRICULTURA.items.put(Items.BEETROOT_SEEDS, new ShopItem(Items.BEETROOT_SEEDS, 2, 1));
        AGRICULTURA.items.put(Items.MELON_SEEDS, new ShopItem(Items.MELON_SEEDS, 4, 2));
        AGRICULTURA.items.put(Items.PUMPKIN_SEEDS, new ShopItem(Items.PUMPKIN_SEEDS, 4, 2));
        AGRICULTURA.items.put(Items.SUGAR_CANE, new ShopItem(Items.SUGAR_CANE, 2, 1));
        AGRICULTURA.items.put(Items.COCOA_BEANS, new ShopItem(Items.COCOA_BEANS, 4, 2));
        AGRICULTURA.items.put(Items.CACTUS, new ShopItem(Items.CACTUS, 2, 1));
        AGRICULTURA.items.put(Items.BAMBOO, new ShopItem(Items.BAMBOO, 2, 1));

        // TINTES - Colorantes básicos
        TINTES.items.put(Items.WHITE_DYE, new ShopItem(Items.WHITE_DYE, 2, 1));
        TINTES.items.put(Items.RED_DYE, new ShopItem(Items.RED_DYE, 2, 1));
        TINTES.items.put(Items.BLUE_DYE, new ShopItem(Items.BLUE_DYE, 2, 1));
        TINTES.items.put(Items.GREEN_DYE, new ShopItem(Items.GREEN_DYE, 2, 1));
        TINTES.items.put(Items.YELLOW_DYE, new ShopItem(Items.YELLOW_DYE, 2, 1));
        TINTES.items.put(Items.BLACK_DYE, new ShopItem(Items.BLACK_DYE, 2, 1));
        TINTES.items.put(Items.ORANGE_DYE, new ShopItem(Items.ORANGE_DYE, 2, 1));
        TINTES.items.put(Items.PURPLE_DYE, new ShopItem(Items.PURPLE_DYE, 2, 1));

        // MISCELÁNEAS - Items diversos
        MISCELANEAS.items.put(Items.STRING, new ShopItem(Items.STRING, 2, 1));
        MISCELANEAS.items.put(Items.SPIDER_EYE, new ShopItem(Items.SPIDER_EYE, 4, 2));
        MISCELANEAS.items.put(Items.BLAZE_POWDER, new ShopItem(Items.BLAZE_POWDER, 10, 5));
        MISCELANEAS.items.put(Items.ENDER_PEARL, new ShopItem(Items.ENDER_PEARL, 16, 8));
        MISCELANEAS.items.put(Items.GUNPOWDER, new ShopItem(Items.GUNPOWDER, 4, 2));
        MISCELANEAS.items.put(Items.BONE, new ShopItem(Items.BONE, 2, 1));
        MISCELANEAS.items.put(Items.BONE_MEAL, new ShopItem(Items.BONE_MEAL, 2, 1));
        MISCELANEAS.items.put(Items.SLIME_BALL, new ShopItem(Items.SLIME_BALL, 6, 3));
        MISCELANEAS.items.put(Items.ENDER_EYE, new ShopItem(Items.ENDER_EYE, 30, 15));
        MISCELANEAS.items.put(Items.GLOWSTONE_DUST, new ShopItem(Items.GLOWSTONE_DUST, 4, 2));

        // BOTÍN DE MOBS - Drops de criaturas
        BOTIN_MOBS.items.put(Items.ROTTEN_FLESH, new ShopItem(Items.ROTTEN_FLESH, 2, 1));
        BOTIN_MOBS.items.put(Items.BONE, new ShopItem(Items.BONE, 2, 1));
        BOTIN_MOBS.items.put(Items.STRING, new ShopItem(Items.STRING, 2, 1));
        BOTIN_MOBS.items.put(Items.SPIDER_EYE, new ShopItem(Items.SPIDER_EYE, 4, 2));
        BOTIN_MOBS.items.put(Items.ARROW, new ShopItem(Items.ARROW, 2, 1));
        BOTIN_MOBS.items.put(Items.GUNPOWDER, new ShopItem(Items.GUNPOWDER, 4, 2));
        BOTIN_MOBS.items.put(Items.ENDER_PEARL, new ShopItem(Items.ENDER_PEARL, 16, 8));
        BOTIN_MOBS.items.put(Items.BLAZE_ROD, new ShopItem(Items.BLAZE_ROD, 20, 10));
        BOTIN_MOBS.items.put(Items.GHAST_TEAR, new ShopItem(Items.GHAST_TEAR, 20, 10));
        BOTIN_MOBS.items.put(Items.SLIME_BALL, new ShopItem(Items.SLIME_BALL, 6, 3));
    }

    public Text getDisplayText() {
        return Text.literal(color + "§l" + displayName);
    }
}