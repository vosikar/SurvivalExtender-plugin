package me.tox1que.survivalextender.listeners;

import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SmithingTableHandlers implements Listener{
    @EventHandler
    public void onSmi(PlayerInteractEvent e){
        if(e.getClickedBlock() == null)
            return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if(e.getClickedBlock().getType() == Material.SMITHING_TABLE){
            if(Utils.getServerType() == ServerType.SURVIVAL){
                e.getPlayer().performCommand("smithingtable");
            }else{
                ChatColor a = ChatColor.of("#0B69B0");
                ChatColor b = ChatColor.of("#8FC3EB");
                e.getPlayer().sendMessage(a + "Systém §8» " + b + "Smithing table otevřeš pomocí '" + a + "/smithingtable" + b + "'.");
            }
        }
    }
}
