package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class GlobalSeasonsListeners implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(e.getHand() == EquipmentSlot.OFF_HAND)
            return;
        if(!e.getPlayer().getWorld().getName().equals("SpawnWorld"))
            return;
        DialogNPC npc = SurvivalExtender.getInstance().getSeasonalPlugin().getNPC(e.getRightClicked().getName());
        if(npc == null)
            return;
        e.setCancelled(true);

        Player player = e.getPlayer();
        npc.interact(player);
    }
}
