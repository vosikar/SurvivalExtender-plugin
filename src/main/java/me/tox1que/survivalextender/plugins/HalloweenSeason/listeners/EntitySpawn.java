package me.tox1que.survivalextender.plugins.HalloweenSeason.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class EntitySpawn implements Listener{

    @EventHandler
    public void on(EntitySpawnEvent e){
        int random = new Random().nextInt(4);
        if(random == 2){
            switch(e.getEntityType()){
                case POLAR_BEAR -> e.getEntity().setCustomName(ChatColor.of("#331A00")+"§lVrk");
                case CAVE_SPIDER -> e.getEntity().setCustomName(ChatColor.of("#2E2E33")+"§lČerná vdova");
                case BAT -> e.getEntity().setCustomName(ChatColor.of("#404040")+"§lNetopýr");
                case SILVERFISH -> e.getEntity().setCustomName(ChatColor.of("#808080")+"§lKrysa");
//                case VILLAGER -> {
//                    Villager villager = (Villager) e.getEntity();
//                    villager.get
//                }
            }
        }
    }
}
