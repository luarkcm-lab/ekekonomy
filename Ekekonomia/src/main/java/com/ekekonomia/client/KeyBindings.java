package com.ekekonomia.client;

// Removed MercadoShopScreen import - using server-side command instead
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding OPEN_SHOP_KEY;
    
    public static void register() {
        OPEN_SHOP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ekekonomia.open_shop",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.ekekonomia.shop"
        ));
    }
    
    public static void tick(MinecraftClient client) {
        if (OPEN_SHOP_KEY.wasPressed()) {
            if (client.player != null) {
                System.out.println("[Ekekonomia] Keybind B pressed - Opening chest shop...");
                // Send chat message instead of command to avoid StackOverflowError
                client.getNetworkHandler().sendChatMessage("/tienda");
            }
        }
    }
}

