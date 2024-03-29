package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.olds;

import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.SeasonalSQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EasterOldSurvivalListeners implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        SeasonalSQL.loadPlayerProfile(e.getPlayer(), "quests_easter");
    }
}
