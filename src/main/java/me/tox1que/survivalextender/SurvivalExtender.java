package me.tox1que.survivalextender;

import me.tox1que.survivalextender.plugins.HalloweenSeason.HalloweenPlugin;
import me.tox1que.survivalextender.plugins.RecipePlugin.RecipePlugin;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SurvivalExtender extends JavaPlugin{

    private static SurvivalExtender main;

    private HalloweenPlugin halloweenPlugin;
    private ThreeWiseMenPlugin threeWiseMenPlugin;

    private String prefix;
    private ChatColor primaryColor;
    private ChatColor secondaryColor;

    private List<BasePlugin> plugins;

    @Override
    public void onEnable(){
        main = this;

        this.prefix = "§3Systém";
        this.primaryColor = ChatColor.AQUA;
        this.secondaryColor = ChatColor.DARK_AQUA;

        this.plugins = new ArrayList<>();

        loadManagers();
    }

    @Override
    public void onDisable(){
        // Plugin shutdown logic
    }

    private void loadManagers(){
        new RecipePlugin();
        if(Utils.getServerName().equals("OldSurvival") || Bukkit.getServer().getPort() == 30015){
            halloweenPlugin = new HalloweenPlugin();
        }

        plugins.forEach(BasePlugin::load);
    }

    public void addPlugin(BasePlugin plugin){
        plugins.add(plugin);
    }

    public static SurvivalExtender getInstance(){
        return main;
    }

    /*
        Plugin getters
     */

    public HalloweenPlugin getHalloweenPlugin(){
        return halloweenPlugin;
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
