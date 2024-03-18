package me.tox1que.survivalextender.listeners;

import me.tox1que.survivalextender.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SpecialItemsHandlers implements Listener{

    private final String MINER_INVENTORY_NAME =  ItemUtils.colorize("#00C0CCKopáčův inventář");
    private final Map<String, Inventory> inventories = new HashMap<>();

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

        Inventory inventory = inventories.getOrDefault(player.getName(), Bukkit.createInventory(player, 27, MINER_INVENTORY_NAME));
        player.openInventory(inventory);
    }

    @EventHandler
    public void minerPickaxeInventoryClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equals(MINER_INVENTORY_NAME))
            return;

        inventories.put(e.getPlayer().getName(), e.getInventory());
    }
}
