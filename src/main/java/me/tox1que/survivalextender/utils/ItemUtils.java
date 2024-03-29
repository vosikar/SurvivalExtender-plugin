package me.tox1que.survivalextender.utils;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtils{

    public static String colorize(String string){
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for(Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)){
            String color = string.substring(matcher.start(), matcher.end());
            string = string.replace(color, net.md_5.bungee.api.ChatColor.of(color) + ""); // You're missing this replacing
        }
        string = ChatColor.translateAlternateColorCodes('&', string); // Translates any & codes too
        return string;
    }

    public static void fillInventory(Inventory inventory, ItemStack fill){
        for(int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, fill);
        }
    }

    public static ItemStack getKitItem(String name){
        Kit kit = CMI.getInstance().getKitsManager().getKit(name, true);
        if(kit == null){
            Logger.Console.SEVERE("Kit " + name + " is null! !");
            return null;
        }
        return kit.getFirstNotNullItem();
    }

    public static void giveKit(Player player, String name){
        Kit kit = CMI.getInstance().getKitsManager().getKit(name, true);
        if(kit == null){
            Logger.Console.SEVERE("Kit " + name + " is null! !");
        }else{
            PlayerUtils.sendSystemMessage(player, "Obdržel jsi kit §b§o" + name + "§b.");
            ItemStack[] items = kit.getItems().stream().filter(is -> is != null && is.getType() != Material.AIR).toArray(ItemStack[]::new);
            if(Bukkit.getPluginManager().getPlugin("Core") != null){
                CoreConnector.addItemsToInventoryOrSafe(player, items);
            }else{
                player.getInventory().addItem(items);
            }
        }
    }

}
