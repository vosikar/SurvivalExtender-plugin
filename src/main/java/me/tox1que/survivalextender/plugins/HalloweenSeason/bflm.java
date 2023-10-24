package me.tox1que.survivalextender.plugins.HalloweenSeason;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class bflm implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!sender.isOp())
            return false;

        SurvivalExtender.getInstance().getHalloweenPlugin().load();
        sender.sendMessage("reloaded");

        return false;
    }
}
