package me.tox1que.survivalextender.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemUtils{

    public static void fillInventory(Inventory inventory, ItemStack fill){
        for(int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, fill);
        }
    }

    public static String colorize(String string){
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for(Matcher matcher = pattern.matcher(string); matcher.find(); matcher = pattern.matcher(string)){
            String color = string.substring(matcher.start(), matcher.end());
            string = string.replace(color, net.md_5.bungee.api.ChatColor.of(color) + ""); // You're missing this replacing
        }
        string = ChatColor.translateAlternateColorCodes('&', string); // Translates any & codes too
        return string;
    }

}
