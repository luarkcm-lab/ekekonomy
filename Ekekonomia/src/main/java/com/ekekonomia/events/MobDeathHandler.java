package com.ekekonomia.events;

import com.ekekonomia.config.EkekonomiaConfig;
import com.ekekonomia.economy.PersistentEconomyManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobDeathHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger("ekekonomia-mob");
    
    public static void register() {
        LOGGER.info("Registering mob death handler");
        
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            LOGGER.info("Entity died: " + entity.getName().getString() + ", type: " + entity.getClass().getSimpleName());
            
            if (!EkekonomiaConfig.enableMobDrops) {
                LOGGER.info("Mob drops disabled");
                return;
            }
            
            // Only handle hostile mobs
            if (!(entity instanceof HostileEntity)) {
                LOGGER.info("Entity is not hostile");
                return;
            }
            
            // Get the killer from damage source
            net.minecraft.entity.Entity killer = damageSource.getAttacker();
            if (!(killer instanceof ServerPlayerEntity)) {
                LOGGER.info("Killer is not a player: " + (killer != null ? killer.getClass().getSimpleName() : "null"));
                return;
            }
            
            ServerPlayerEntity player = (ServerPlayerEntity) killer;
            LOGGER.info("Player " + player.getName().getString() + " killed " + entity.getName().getString());
            
            // Calculate money based on mob health (10% of health)
            float mobHealth = entity.getMaxHealth();
            long moneyToGive = (long) (mobHealth * 0.1); // 10% of mob health
            
            LOGGER.info("Mob health: " + mobHealth + ", money to give: " + moneyToGive);
            
            if (moneyToGive > 0) {
                PersistentEconomyManager.addMoney(player, moneyToGive);
                
                player.sendMessage(Text.literal("§6[Ekekonomia] §fHas ganado §a" + moneyToGive + "§f monedas por matar a §c" + entity.getName().getString() + "§f."), false);
                LOGGER.info("Added " + moneyToGive + " money to player " + player.getName().getString());
            }
        });
    }
}
