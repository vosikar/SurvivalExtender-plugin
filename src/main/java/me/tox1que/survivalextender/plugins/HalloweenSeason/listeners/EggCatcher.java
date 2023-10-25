package me.tox1que.survivalextender.plugins.HalloweenSeason.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class EggCatcher implements Listener{
    private final Map<String, String> items;

    public EggCatcher(){
        items = new HashMap<>();
        items.put(ChatColor.of("#2E2E33")+"§lČerná vdova", "halloweenevent_vdova");
        items.put(ChatColor.of("#331A00")+"§lVrk", "halloweenevent_vrk");
        items.put(ChatColor.of("#404040")+"§lNetopýr", "halloweenevent_netopyr");
        items.put(ChatColor.of("#808080")+"§lKrysa", "halloweenevent_krysa");
        items.put(ChatColor.of("#206020")+"§lNemrtvý alchymista", "halloweenevent_alchymista");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Egg egg))
            return;
        if(!(egg.getShooter() instanceof Player player))
            return;
        if(e.getEntity().getCustomName() == null)
            return;

        if(items.containsKey(e.getEntity().getCustomName())){
            e.getEntity().remove();
            String command = "cmi kit " + items.get(e.getEntity().getCustomName()) + " " + player.getName();
            Bukkit.dispatchCommand(SurvivalExtender.getInstance().getServer().getConsoleSender(), command);
        }
    }
}
