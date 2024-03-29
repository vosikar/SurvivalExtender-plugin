package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.ob;

import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.SeasonalSQL;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EasterOneBlockListeners implements Listener{

    private final EasterOneBlock plugin;

    public EasterOneBlockListeners(EasterOneBlock plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        SeasonalSQL.loadPlayerProfile(e.getPlayer(), "quests_easter");
        plugin.loadPlayerProfile(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!e.getPlayer().getWorld().getName().equals("SpawnWorld"))
            return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if(e.getHand() != EquipmentSlot.HAND)
            return;
        if(e.getClickedBlock() == null)
            return;
        Location location = e.getClickedBlock().getLocation();
        if(!plugin.isEgg(location))
            return;

        Player player = e.getPlayer();
        int index = plugin.getLocationIndex(location);
        if(plugin.hasCompleted(player.getName(), index)){
            PlayerUtils.sendSystemMessage(player, "Toto vajíčko už jsi dávno našel.");
        }else{
            plugin.addPlayerEgg(player.getName(), location);
            int collected = plugin.getCompletedAmount(player.getName());
            PlayerUtils.sendSystemMessage(player, "Heleme se, našel jsi "+collected+". vajíčko z 5.");
            if(collected == 5){
                PlayerUtils.sendSystemMessage(player, "Za nalezení všech jsi obdržel speciální item. :)");
                ItemUtils.giveKit(player, "easter2024_hunt_egg");
            }
        }
    }
}
