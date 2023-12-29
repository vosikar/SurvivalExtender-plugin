package me.tox1que.survivalextender.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemUtils{

    public static void fillInventory(Inventory inventory, ItemStack fill){
        for(int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, fill);
        }
    }

}
