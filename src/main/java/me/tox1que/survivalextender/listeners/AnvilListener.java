package me.tox1que.survivalextender.listeners;

import com.Zrips.CMI.events.CMIAnvilItemRenameEvent;
import com.Zrips.CMI.events.CMIAnvilItemRepairEvent;
import me.tox1que.survivalextender.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AnvilListener implements Listener{

    @EventHandler
    public void onRename(CMIAnvilItemRenameEvent e){
        Player player = e.getPlayer();
        int cost = e.getCost();
        String oldName = e.getItemFrom().getItemMeta().getDisplayName();
        String newName = e.getItemTo().getItemMeta().getDisplayName();
        Logger.Database.Anvil.write(true, player.getName(), cost, e.getItemFrom().getType(), oldName, newName, "rename");
    }

    @EventHandler
    public void onRepair(CMIAnvilItemRepairEvent e){
        Player player = e.getPlayer();
        int cost = e.getRepairCost();
        String oldName = e.getItemFrom().getItemMeta().getDisplayName();
        String newName = e.getItemTo().getItemMeta().getDisplayName();
        Logger.Database.Anvil.write(true, player.getName(), cost, e.getItemFrom().getType(), oldName, newName, "repair");
    }
}
