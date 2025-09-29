package com.ekekonomia.economy;

import com.ekekonomia.config.EkekonomiaConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersistentEconomyManager {
    
    private static final Map<UUID, Long> playerBalances = new HashMap<>();
    
    public static long getBalance(PlayerEntity player) {
        // Return from memory or default
        return playerBalances.getOrDefault(player.getUuid(), (long) EkekonomiaConfig.startingMoney);
    }
    
    public static boolean setBalance(PlayerEntity player, long amount) {
        if (amount < 0) return false;
        
        playerBalances.put(player.getUuid(), amount);
        return true;
    }
    
    public static boolean addMoney(PlayerEntity player, long amount) {
        if (amount < 0) return false;
        long currentBalance = getBalance(player);
        return setBalance(player, currentBalance + amount);
    }
    
    public static boolean removeMoney(PlayerEntity player, long amount) {
        if (amount < 0) return false;
        long currentBalance = getBalance(player);
        if (currentBalance < amount) return false;
        return setBalance(player, currentBalance - amount);
    }
    
    public static boolean transferMoney(PlayerEntity from, PlayerEntity to, long amount) {
        if (amount < 0) return false;
        if (removeMoney(from, amount)) {
            return addMoney(to, amount);
        }
        return false;
    }
    
    public static void sendBalanceMessage(ServerPlayerEntity player) {
        long balance = getBalance(player);
        player.sendMessage(Text.literal("§6[Ekekonomia] §fTu saldo actual: §a" + balance + "§f monedas."), false);
    }
}
