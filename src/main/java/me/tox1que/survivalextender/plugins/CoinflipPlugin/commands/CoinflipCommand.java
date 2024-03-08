package me.tox1que.survivalextender.plugins.CoinflipPlugin.commands;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.CoinflipPlugin;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.abstracts.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoinflipCommand extends BaseCommand{

    private final CoinflipPlugin plugin;

    public CoinflipCommand(CoinflipPlugin plugin){
        super("coinflip", plugin, "<create> <částka>");
        this.plugin = plugin;
        this.completions.add(new String[]{"create"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player player))
            return false;

        if(args.length == 0){
            plugin.openOverview(player);
            return false;
        }
        if(args.length >= 2){
            if(args[0].equalsIgnoreCase("create")){
                try{
                    int amount = Integer.parseInt(args[1]);
                    if(plugin.getGames().stream().filter(game -> game.getCreator().getName().equals(player.getName())).count() >= 3){
                        plugin.sendMessage(player, "Nemůžeš vytvořit více než 3 hry.");
                        return false;
                    }
                    if(plugin.getGames().size() >= 21){
                        plugin.sendMessage(player, "Byl vytvořen maximální počet her.");
                        return false;
                    }
                    if(amount < 1000){
                        plugin.sendMessage(player, "Minimální vsazená částka je $1 000.");
                        return false;
                    }
                    if(!PaymentUtils.hasMoney(player, amount)){
                        plugin.sendMessage(player, "Nemáš dostatek financí.");
                        return false;
                    }
                    plugin.createCoinflip(player, amount);
                }catch(NumberFormatException e){
                    sendUsage(player);
                }
                return false;
            }
        }

        sendUsage(player);
        return false;
    }
}