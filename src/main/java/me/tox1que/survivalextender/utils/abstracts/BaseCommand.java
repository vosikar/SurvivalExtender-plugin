package me.tox1que.survivalextender.utils.abstracts;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter{

    protected final SurvivalExtender main;

    protected final String name;
    protected final String[] usages;
    protected BasePlugin plugin;
    protected List<String[]> completions;

    public BaseCommand(String name){
        this(name, null);
    }

    public BaseCommand(String name, BasePlugin plugin){
        this(name, plugin, "/" + name);
    }

    public BaseCommand(String name, BasePlugin plugin, String... usages){
        this.name = name;
        this.plugin = plugin;
        this.usages = usages;
        this.completions = new ArrayList<>();
        main = SurvivalExtender.getInstance();

        PluginCommand command = main.getCommand(name);
        if(command != null){
            command.setExecutor(this);
            command.setTabCompleter(this);
        }else{
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND " + name);
        }
    }

    protected void sendUsage(CommandSender sender){
        plugin.sendMessage("Špatné použití příkazu, příklady použití:", sender);
        for(String s : usages){
            plugin.sendMessage("/" + this.name + " " + s, sender);
        }
    }

    protected void sendMessage(String message, Player... players){
        plugin.sendMessage(message, players);
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        if(completions == null)
            return null;
        int index = args.length - 1;
        if(completions.size() < index + 1)
            return null;

        List<String> result = new ArrayList<>();
        String[] completions = this.completions.get(index);
        for(String s : completions){
            if(s.toLowerCase().startsWith(args[index].toLowerCase())){
                result.add(s);
            }
        }

        return result;
    }

}
