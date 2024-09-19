package me.tox1que.survivalextender;

import me.tox1que.survivalextender.listeners.*;
import me.tox1que.survivalextender.managers.stats.StatsManager;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.CoinflipPlugin;
import me.tox1que.survivalextender.plugins.RecipePlugin.RecipePlugin;
import me.tox1que.survivalextender.plugins.SeasonalQuests.SeasonalPlugin;
import me.tox1que.survivalextender.plugins.SpecialItems.SpecialItemsPlugin;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import me.tox1que.survivalextender.utils.connectors.PluginConnector;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SurvivalExtender extends JavaPlugin{

    private static SurvivalExtender main;
    private Economy economy;

    private CoinflipPlugin coinflipPlugin;
    private SeasonalPlugin seasonalPlugin;

    private StatsManager statsManager;

    private String prefix;
    private ChatColor primaryColor;
    private ChatColor secondaryColor;

    private List<BasePlugin> plugins;

    public static SurvivalExtender getInstance(){
        return main;
    }

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

        this.getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        this.getServer().getPluginManager().registerEvents(new EasterHandlers(), this);
        this.getServer().getPluginManager().registerEvents(new EnchantHandles(), this);
        this.getServer().getPluginManager().registerEvents(new SmithingTableHandlers(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinHandlers(), this);

        loadManagers();
        this.statsManager = new StatsManager();
        new PluginConnector();
    }

    @Override
    public void onDisable(){
        plugins.forEach(BasePlugin::unload);
    }

    private void loadManagers(){
        new RecipePlugin();
        new SpecialItemsPlugin();
        seasonalPlugin = new SeasonalPlugin();
        if(Utils.getServerType() != ServerType.ONEBLOCK){
            coinflipPlugin = new CoinflipPlugin();
        }

        plugins.forEach(BasePlugin::load);
    }

    public void addPlugin(BasePlugin plugin){
        plugins.add(plugin);
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
        Plugins & managers getters
     */

    public CoinflipPlugin getCoinflipPlugin(){
        return coinflipPlugin;
    }

    public StatsManager getStatsManager(){
        return statsManager;
    }

    public SeasonalPlugin getSeasonalPlugin(){
        return seasonalPlugin;
    }
}
