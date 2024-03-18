package me.tox1que.survivalextender.utils;

import me.saves.core.managers.ItemsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CoreConnector{

    public static void addItemsToInventoryOrSafe(Player player, ItemStack... items){
        Logger.Console.INFO("adding "+items.length+" items through Core to "+player.getName());
        ItemsManager.addItemsToInventoryOrSafe(player, items);
    }
}
