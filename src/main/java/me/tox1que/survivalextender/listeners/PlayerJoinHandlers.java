package me.tox1que.survivalextender.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandlers implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        SurvivalExtender.getInstance().getStatsManager().loadStats(e.getPlayer());
    }
}
