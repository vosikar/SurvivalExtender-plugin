package me.tox1que.survivalextender.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.connectors.CoreConnector;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PaymentUtils{

    public static void addScore(Player player, int bonus, String comment){
        CoreConnector.addScore(player, bonus, comment);
    }

    public static void giveMoney(double amount, String comment, OfflinePlayer... players){
        for(OfflinePlayer player : players){
            double oldBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player);
            double newBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player) + amount;
            SurvivalExtender.getInstance().getEconomy().depositPlayer(player, amount);
            Logger.Database.Balance.write(false, player.getName(), oldBalance, newBalance, Math.abs(amount), PlayerUtils.getParsedLocation(player), comment);
            if(PlayerUtils.isOnline(player)){
                player.getPlayer().sendMessage("§8§8+$" + Utils.formatNumber(amount));
            }
        }
    }

    public static boolean hasMoney(OfflinePlayer player, double amount){
        return SurvivalExtender.getInstance().getEconomy().has(player, amount);
    }

    public static void takeMoney(double amount, String comment, OfflinePlayer... players){
        for(OfflinePlayer player : players){
            double oldBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player);
            double newBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player) - amount;
            SurvivalExtender.getInstance().getEconomy().withdrawPlayer(player, amount);
            Logger.Database.Balance.write(true, player.getName(), oldBalance, newBalance, Math.abs(amount), PlayerUtils.getParsedLocation(player), comment);
            if(PlayerUtils.isOnline(player)){
                player.getPlayer().sendMessage("§8§8-$" + Utils.formatNumber(amount));
            }
        }
    }
}
