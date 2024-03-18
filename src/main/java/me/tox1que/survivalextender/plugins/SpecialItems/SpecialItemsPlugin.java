package me.tox1que.survivalextender.plugins.SpecialItems;

import me.tox1que.survivalextender.plugins.SpecialItems.listeners.SpecialItemsHandlers;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SpecialItemsPlugin extends BasePlugin{

    private final String MINER_INVENTORY_NAME = ItemUtils.colorize("#00C0CCKopáčův inventář");
    private final Map<String, Inventory> inventories = new HashMap<>();

    @Override
    public void load(){
        main.getServer().getPluginManager().registerEvents(new SpecialItemsHandlers(this), main);
        startCheckers();
    }

    public String getMinerInventoryName(){
        return MINER_INVENTORY_NAME;
    }

    public Inventory getInventory(Player player){
        return inventories.getOrDefault(player.getName(), Bukkit.createInventory(player, 27, MINER_INVENTORY_NAME));
    }

    public void updateInventory(Player player, Inventory inventory){
        inventories.put(player.getName(), inventory);
    }

    public void startCheckers(){
        if(Utils.getServerType() == ServerType.SURVIVAL){
            startSpecialItemsChecker();
        }
        if(Utils.getServerType() != ServerType.ONEBLOCK){
            startInventoryChecker();
        }
    }

    private void startSpecialItemsChecker(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            ItemStack helmet = player.getInventory().getHelmet();
            if(helmet != null && helmet.getItemMeta() != null){
                if(helmet.getItemMeta().getDisplayName().equals(ChatColor.of("#CCBC29") + "§lBaltazarova korunka")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                }
            }
        }), 1000L, 100L);
    }

    private void startInventoryChecker(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            if(inventories.containsKey(player.getName())){
                if(Arrays.stream(inventories.get(player.getName()).getContents()).anyMatch(is -> is != null && is.getType() != Material.AIR)){
                    sendAlertMessage(player, "Pozor! Máš v Kopačově inventáři uložené itemy, které ti po restartu zmizí.");
                }
            }
        }), 1000L, 20 * 60 * 5L);
    }
}
