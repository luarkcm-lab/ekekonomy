package com.ekekonomia.mixins;

import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    
    @Inject(method = "init", at = @At("TAIL"))
    private void addShopButton(CallbackInfo ci) {
        // Shop button functionality will be implemented differently
        // to avoid access issues with protected methods
    }
}
