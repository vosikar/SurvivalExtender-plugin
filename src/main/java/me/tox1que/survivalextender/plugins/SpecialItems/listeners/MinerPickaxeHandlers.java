package me.tox1que.survivalextender.plugins.SpecialItems.listeners;

import me.tox1que.survivalextender.plugins.SpecialItems.SpecialItemsPlugin;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MinerPickaxeHandlers implements Listener{

    private final SpecialItemsPlugin plugin;

    public MinerPickaxeHandlers(SpecialItemsPlugin plugin){
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

        String displayName = inHand.getItemMeta().getDisplayName();
        if(!displayName.contains("§lKopáčův krumpáč"))
            return;

        Inventory inventory = plugin.getMinerInventory(player);
        player.openInventory(inventory);
    }

    @EventHandler
    public void minerPickaxeInventoryClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equals(plugin.getMinerInventoryName()))
            return;
        if(!(e.getPlayer() instanceof Player player))
            return;

        plugin.updateMinerInventory(player, e.getInventory());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void minerPickaxeMine(BlockDropItemEvent e){
        if(e.isCancelled())
            return;
        Player player = e.getPlayer();
        if(!player.isOp() && player.getName().equals("Borivoj0hniblesk"))
            return;
        if(player.getInventory().getItemInMainHand().getItemMeta() == null)
            return;
        if(!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("§lKopáčův krumpáč"))
            return;
        plugin.addToMinerInventory(player, e.getItems().stream().map(Item::getItemStack).toArray(ItemStack[]::new));
        e.setCancelled(true);
    }
}
