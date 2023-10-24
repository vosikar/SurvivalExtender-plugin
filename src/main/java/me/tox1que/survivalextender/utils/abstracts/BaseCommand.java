package me.tox1que.survivalextender.utils.abstracts;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.Utils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseCommand implements CommandExecutor, TabCompleter{

    protected final SurvivalExtender main;

    protected final String name;
    protected final String[] usages;

    public BaseCommand(String name){
        this(name, "/"+name);
    }

    public BaseCommand(String name, String... usages){
        this.name = name;
        this.usages = usages;
        main = SurvivalExtender.getInstance();

        PluginCommand command = main.getCommand(name);
        if(command != null){
            command.setExecutor(this);
            command.setTabCompleter(this);
        }else{
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND "+name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND "+name);
            Logger.Console.SEVERE("UNABLE TO REGISTER COMMAND "+name);
        }
    }

    protected void sendUsage(CommandSender sender){
        sender.sendMessage(Utils.getPluginMessage("Špatné použití příkazu, příklady použití:"));
        for(String s:usages){
            sender.sendMessage(Utils.getPluginMessage("/"+this.name+" "+s));
        }
    }

    protected void sendMessage(Player player, String message){
        player.sendMessage(Utils.getPluginMessage(main.getSecondaryColor()+"Recept", message));
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        return null;
    }

}
