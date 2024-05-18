package me.tox1que.survivalextender.plugins.SpecialItems;

import me.tox1que.survivalextender.plugins.SpecialItems.listeners.MinerPickaxeHandlers;
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

    private final String MINER_INVENTORY_NAME;
    private final Map<String, Inventory> minerInventories;

    public SpecialItemsPlugin(){
        MINER_INVENTORY_NAME = ItemUtils.colorize("#00C0CCKopáčův inventář");
        minerInventories = new HashMap<>();
    }

    @Override
    public void load(){
        if(Utils.getServerType() != ServerType.ONEBLOCK){
            main.getServer().getPluginManager().registerEvents(new MinerPickaxeHandlers(this), main);
            startCheckers();
        }
    }

    public boolean addToMinerInventory(Player player, ItemStack... items){
        Inventory inventory = getMinerInventory(player);
        boolean added = inventory.addItem(items).isEmpty();
        if(added){
            updateMinerInventory(player, inventory);
            return true;
        }
        return false;
    }

    public String getMinerInventoryName(){
        return MINER_INVENTORY_NAME;
    }

    public Inventory getMinerInventory(Player player){
        return minerInventories.getOrDefault(player.getName(), Bukkit.createInventory(player, 27, MINER_INVENTORY_NAME));
    }

    public void updateMinerInventory(Player player, Inventory inventory){
        minerInventories.put(player.getName(), inventory);
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
                String displayName = helmet.getItemMeta().getDisplayName();
                if(displayName.equals(ChatColor.of("#CCBC29") + "§lBaltazarova korunka")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                }else if(displayName.equals(ChatColor.of("#009987")+"§lPlavecká čepice")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 200, 5));
                }

            }
        }), 1000L, 100L);
    }

    private void startInventoryChecker(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            if(minerInventories.containsKey(player.getName())){
                if(Arrays.stream(minerInventories.get(player.getName()).getContents()).anyMatch(is -> is != null && is.getType() != Material.AIR)){
                    sendAlertMessage("Pozor! Máš v Kopačově inventáři uložené itemy, které ti po restartu zmizí.", player);
                }
            }
        }), 1000L, 20 * 60 * 5L);
    }
}
