package me.tox1que.survivalextender.plugins.HalloweenSeason.listeners;

import me.tox1que.survivalextender.plugins.HalloweenSeason.HalloweenSQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener{

    @EventHandler
    public void on(PlayerJoinEvent e){
        HalloweenSQL.loadPlayerProfile(e.getPlayer());
    }
}
