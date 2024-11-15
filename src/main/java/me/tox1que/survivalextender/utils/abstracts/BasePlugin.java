package me.tox1que.survivalextender.utils.abstracts;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BasePlugin{

    protected final SurvivalExtender main;
    protected final String prefix;
    protected final String primaryColor;
    protected final String secondaryColor;

    public BasePlugin(){
        this("Systém", "§b", "§3");
    }

    public BasePlugin(String prefix, String primary, String secondary){
        this.main = SurvivalExtender.getInstance();
        this.prefix = prefix;
        this.primaryColor = primary;
        this.secondaryColor = secondary;
        main.addPlugin(this);
    }

    public String getPrefix(){
        return prefix;
    }

    public String getPrimaryColor(){
        return primaryColor;
    }

    public String getSecondaryColor(){
        return secondaryColor;
    }

    public String getFinalMessage(String message){
        return message.replace("[pc]", primaryColor).replace("[sc]", secondaryColor);
    }

    public void broadcastMessage(String message){
        Bukkit.broadcastMessage(getFinalMessage("[sc]" + prefix + " §7» [pc]" + message));
    }

    public void sendAlertMessage(String message, Player... players){
        for(Player player:players){
            player.sendMessage(getFinalMessage("§4" + prefix + " §7» §c" + message));
        }
    }

    public void sendMessage(String message, CommandSender... senders){
        for(CommandSender sender:senders){
            if(sender instanceof Player){
                sender.sendMessage(getFinalMessage("[sc]" + prefix + " §7» [pc]" + message));
            }
        }
    }

    public abstract void load();

    public void unload(){}
}
