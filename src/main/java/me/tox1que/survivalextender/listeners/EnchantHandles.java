package me.tox1que.survivalextender.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantHandles implements Listener{

    @EventHandler
    public void on(PrepareItemEnchantEvent e){
        if(e.getItem().getItemMeta() == null)
            return;
        if(e.getItem().getItemMeta().getLore() == null)
            return;

        for(String line : e.getItem().getItemMeta().getLore()){
            if(line.contains("© MineHub.cz")){
                e.setCancelled(true);
                break;
            }
        }
    }
}
