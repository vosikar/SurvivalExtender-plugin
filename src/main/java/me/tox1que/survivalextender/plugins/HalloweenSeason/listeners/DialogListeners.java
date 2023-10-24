package me.tox1que.survivalextender.plugins.HalloweenSeason.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.HalloweenSeason.utils.DialogNPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class DialogListeners implements Listener{

    @EventHandler
    public void on(PlayerInteractAtEntityEvent e){
        DialogNPC npc = SurvivalExtender.getInstance().getHalloweenPlugin().getNPC(e.getRightClicked().getName());
        if(npc == null)
            return;

        npc.interact(e.getPlayer());
    }
}
