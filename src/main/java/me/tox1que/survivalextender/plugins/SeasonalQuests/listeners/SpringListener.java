package me.tox1que.survivalextender.plugins.SeasonalQuests.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.SeasonalSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpringListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        SeasonalSQL.loadPlayerProfile(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(!e.getPlayer().getWorld().getName().equals("SpawnWorld"))
            return;
        DialogNPC npc = SurvivalExtender.getInstance().getSeasonalPlugin().getNPC(e.getRightClicked().getName());
        if(npc == null)
            return;

        Player player = e.getPlayer();
        npc.interact(player);
    }
}
