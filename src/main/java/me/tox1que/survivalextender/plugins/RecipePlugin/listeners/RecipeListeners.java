package me.tox1que.survivalextender.plugins.RecipePlugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RecipeListeners implements Listener{

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        if(!title.startsWith("Recept -"))
            return;
        e.setCancelled(true);

        ItemStack clicked =  e.getCurrentItem();
        if(clicked == null)
            return;
        if(clicked.getItemMeta() == null)
            return;
        if(clicked.getItemMeta().getLore() == null)
            return;

        if(clicked.getItemMeta().getDisplayName().equals("§aDalší recept") || clicked.getItemMeta().getDisplayName().equals("§aPředchozí recept")){
            int page = Integer.parseInt(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0).split("/")[0]));
            if(e.getRawSlot() != 53){
                page -= 2;
            }
            ((Player) e.getWhoClicked()).performCommand("survivalextender:recipe "+title.split(" ")[2]+" "+page);
        }
    }
}
