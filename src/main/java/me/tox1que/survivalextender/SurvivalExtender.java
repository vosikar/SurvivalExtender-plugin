package me.tox1que.survivalextender;

import me.tox1que.survivalextender.plugins.HalloweenSeason.HalloweenPlugin;
import me.tox1que.survivalextender.plugins.RecipePlugin.RecipePlugin;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.ThreeWiseMenPlugin;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class SurvivalExtender extends JavaPlugin{

    private static SurvivalExtender main;
    private Economy economy;

    private HalloweenPlugin halloweenPlugin;
    private ThreeWiseMenPlugin threeWiseMenPlugin;

    private String prefix;
    private ChatColor primaryColor;
    private ChatColor secondaryColor;

    private List<BasePlugin> plugins;

    @Override
    public void onEnable(){
        if(!setupEconomy()){
            this.getLogger().info("Missing Vault plugin, disabling SurvivalExtender...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        main = this;

        this.prefix = "§3Systém";
        this.primaryColor = ChatColor.AQUA;
        this.secondaryColor = ChatColor.DARK_AQUA;

        this.plugins = new ArrayList<>();

        if(Utils.getServerType() == ServerType.SURVIVAL){
            specialItemsChecker();
        }

        loadManagers();
    }

    @Override
    public void onDisable(){
        plugins.forEach(BasePlugin::unload);
    }

    private void loadManagers(){
        new RecipePlugin();
//        if(Utils.getServerName().equals("OldSurvival") || Bukkit.getServer().getPort() == 30015){
//            halloweenPlugin = new HalloweenPlugin();
//        }
        if(Utils.getServerType() == ServerType.SURVIVAL){
//            threeWiseMenPlugin = new ThreeWiseMenPlugin();
        }

        plugins.forEach(BasePlugin::load);
    }

    public void addPlugin(BasePlugin plugin){
        plugins.add(plugin);
    }

    public void specialItemsChecker(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                ItemStack helmet = player.getInventory().getHelmet();
                if(helmet != null && helmet.getItemMeta() != null){
                    if(helmet.getItemMeta().getDisplayName().equals(ChatColor.of("#CCBC29")+"§lBaltazarova korunka")){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
                    }
                }
            });
        }, 100L, 100L);
    }

    public static SurvivalExtender getInstance(){
        return main;
    }

    private boolean setupEconomy(){
        if(Bukkit.getPluginManager().getPlugin("Vault") == null)
            return false;

        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null)
            return false;

        economy = rsp.getProvider();
        return true;
    }

    public Economy getEconomy(){
        return economy;
    }

    public String getPrefix(){
        return prefix;
    }

    public ChatColor getPrimaryColor(){
        return primaryColor;
    }

    public ChatColor getSecondaryColor(){
        return secondaryColor;
    }

    /*
        Plugin getters
     */
    public ThreeWiseMenPlugin getThreeWiseMenPlugin(){
        return threeWiseMenPlugin;
    }
}
