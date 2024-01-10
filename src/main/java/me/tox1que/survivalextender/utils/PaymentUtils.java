package me.tox1que.survivalextender.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.OfflinePlayer;

public class PaymentUtils{

    public static void giveMoney(double amount, String comment, OfflinePlayer... players){
        for(OfflinePlayer player:players){
            double oldBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player);
            double newBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player)+amount;
            SurvivalExtender.getInstance().getEconomy().depositPlayer(player, amount);
            Logger.Database.Balance.write(player.getName(), oldBalance, newBalance, Math.abs(amount), PlayerUtils.getParsedLocation(player), comment);
        }
    }

    public static boolean hasMoney(OfflinePlayer player, double amount){
        return SurvivalExtender.getInstance().getEconomy().has(player, amount);
    }

    public static void takeMoney(double amount, String comment,  OfflinePlayer... players){
        for(OfflinePlayer player:players){
            double oldBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player);
            double newBalance = SurvivalExtender.getInstance().getEconomy().getBalance(player)-amount;
            SurvivalExtender.getInstance().getEconomy().withdrawPlayer(player, amount);
            Logger.Database.Balance.write(player.getName(), oldBalance, newBalance, Math.abs(amount), PlayerUtils.getParsedLocation(player), comment);
        }
    }
}
