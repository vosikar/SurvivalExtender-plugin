package me.tox1que.survivalextender.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerUtils{

    public static ItemStack getPlayerHead(OfflinePlayer player){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setDisplayName("§b" + player.getName());
        meta.setOwningPlayer(player);
        skull.setItemMeta(meta);
        return skull;
    }

    public static String getParsedLocation(OfflinePlayer player){
        if(!player.isOnline() || player.getPlayer() == null)
            return "offline";
        Location location = player.getPlayer().getLocation();
        return String.format("[%s] %.2f,%.2f,%.2f",location.getWorld() != null ? location.getWorld().getName() : "null",
                location.getX(), location.getY(), location.getZ());
    }

    public static boolean isOnline(OfflinePlayer player){
        return player != null && player.getPlayer() != null && player.isOnline();
    }

    public static void sendSystemMessage(Player player, String message){
        player.sendMessage("§3Systém §7» §b"+message);
    }
}
