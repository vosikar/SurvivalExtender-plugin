package me.tox1que.survivalextender.plugins.CoinflipPlugin.commands;

import me.tox1que.survivalextender.plugins.CoinflipPlugin.CoinflipPlugin;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.abstracts.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinflipCommand extends BaseCommand{

    private final CoinflipPlugin plugin;

    public CoinflipCommand(CoinflipPlugin plugin){
        super("coinflip", plugin, "oznameni", "announcement", "create <částka>");
        this.plugin = plugin;
        this.completions.add(new String[]{"announcement", "create", "oznameni"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player player))
            return false;

        if(args.length <= 1){
            if(args.length > 0){
                if(args[0].equalsIgnoreCase("oznameni") || args[0].equalsIgnoreCase("announcement")){
                    plugin.sendMessage(plugin.getStats(player).toggleAnnouncements() + " sis oznámení z Coinflipů.", player);
                    return false;
                }
            }
            int page;
            try{
                page = Integer.parseInt(args[0]);
            }catch(NumberFormatException | ArrayIndexOutOfBoundsException ignored){
                page = 0;
            }
            plugin.openOverview(player, page);
            return false;
        }

        if(args[0].equalsIgnoreCase("create")){
            try{
                int amount = Integer.parseInt(args[1]);
                if(plugin.getGames().stream().filter(game -> game.getCreator().getName().equals(player.getName())).count() >= 5){
                    plugin.sendMessage("Nemůžeš vytvořit více než 5 her.", player);
                    return false;
                }
                if(amount < 1000){
                    plugin.sendMessage("Minimální vsazená částka je $1 000.", player);
                    return false;
                }
                if(amount > 1_000_000){
                    plugin.sendMessage("Maximální vsazená částka je $1 000 000.", player);
                    return false;
                }
                if(!PaymentUtils.hasMoney(player, amount)){
                    plugin.sendMessage("Nemáš dostatek financí.", player);
                    return false;
                }
                plugin.createCoinflip(player, amount);
            }catch(NumberFormatException e){
                sendUsage(player);
            }
            return false;
        }

        sendUsage(player);
        return false;
    }
}
