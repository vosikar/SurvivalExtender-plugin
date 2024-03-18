package me.tox1que.survivalextender.plugins.SpecialItems.listeners;

import me.tox1que.survivalextender.plugins.SpecialItems.SpecialItemsPlugin;
import me.tox1que.survivalextender.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpecialItemsHandlers implements Listener{

    private final SpecialItemsPlugin plugin;

    public SpecialItemsHandlers(SpecialItemsPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void minerPickaxeInventoryOpen(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        Player player = e.getPlayer();
        if(!e.getPlayer().isSneaking())
            return;

        ItemStack inHand = e.getItem();
        if(inHand == null)
            return;
        if(inHand.getItemMeta() == null)
            return;

        if(!inHand.getItemMeta().getDisplayName().equals(ItemUtils.colorize("#00C0CC&lKopáčův krumpáč")))
            return;

        Inventory inventory = plugin.getInventory(player);
        player.openInventory(inventory);
    }

    @EventHandler
    public void minerPickaxeInventoryClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equals(plugin.getMinerInventoryName()))
            return;
        if(!(e.getPlayer() instanceof Player player))
            return;

        plugin.updateInventory(player, e.getInventory());
    }
}
