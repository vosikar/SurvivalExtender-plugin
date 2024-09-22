package me.tox1que.survivalextender.managers.QoL;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class QoLListeners implements Listener{

    /* MineWorld BossBar */
    @EventHandler
    public void onWorldChange(PlayerTeleportEvent e){
        if(e.getFrom().getWorld() == null)
            return;
        if(e.getTo() == null || e.getTo().getWorld() == null)
            return;

        World from = e.getFrom().getWorld();
        World to = e.getTo().getWorld();
        if(from == to)
            return;

        if(to.getName().equals("MineWorld")){
            SurvivalExtender.getInstance().getQolManager().addMineWorldBossBar(e.getPlayer());
        }else{
            SurvivalExtender.getInstance().getQolManager().removeMineWorldBossBar(e.getPlayer());
        }
    }
}
