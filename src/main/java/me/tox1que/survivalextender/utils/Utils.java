package me.tox1que.survivalextender.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils{

    private static final SurvivalExtender main = SurvivalExtender.getInstance();
    private static final String prefix = main.getPrefix();
    private static final ChatColor primaryColor = main.getPrimaryColor().asBungee();
    private static final ChatColor secondaryColor = main.getSecondaryColor().asBungee();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss");

    public static void actionBarMessage(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static ItemStack createEnchantedItem(Material material, String name, List<String> lore){
        ItemStack item = createItem(material, name, lore);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.SOUL_SPEED, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore, ItemFlag... itemFlags){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if(itemFlags != null)
            meta.addItemFlags(itemFlags);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack item, Enchantment ench, int level){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(ench, level, true);
        item.setItemMeta(meta);
        return item;
    }

    public static String getFormattedTime(int time){
        int minutes = (int) Math.floor(time / 60.0);
        int seconds = time % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static String getFormattedTime(Date date){
        return dateFormat.format(date);
    }

    public static String getPluginMessage(String msg){
        return getPluginMessage(prefix, msg);
    }

    public static String getPluginMessage(String prefix, String msg){
        return getPluginColoredMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + " &7» [pc]" + msg));
    }

    public static String getPluginColoredMessage(String msg){
        return msg
                .replace("[pc]", primaryColor + "")
                .replace("[sc]", secondaryColor + "");
    }

    public static String getServerName(){
        int port = SurvivalExtender.getInstance().getServer().getPort();
        return switch(port){
            case 30029 -> "LightSurvival";
            case 30003 -> "OldSurvival";
            case 30001 -> "OneBlock";
            default -> "unknown";
        };
    }

    public static String getWordVariant(int amount, String singular, String plural, String secondPlural){
        return amount == 1 ? singular : amount <= 4 && amount >= 2 ? plural : secondPlural;
    }

    public static Location parseLocation(String string){
        String[] split = string.split(";");

        World w = Bukkit.getServer().getWorld(split[0]);
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);

        if(split.length > 4){
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static Location parseLocation(String string, World w){
        String[] split = string.split(";");

        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);

        if(split.length > 3){
            float yaw = Float.parseFloat(split[3]);
            float pitch = Float.parseFloat(split[4]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static String parseLocation(Location location){
        return String.join(";", location.getWorld().getName(), String.format("%.5f", location.getX()),
                String.format("%.5f", location.getY()), String.format("%.5f", location.getZ()));
    }

    public static void performConsoleCommand(String command){
        Bukkit.dispatchCommand(SurvivalExtender.getInstance().getServer().getConsoleSender(), command);
    }
}
