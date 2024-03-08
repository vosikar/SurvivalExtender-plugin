package me.tox1que.survivalextender.plugins.CoinflipPlugin.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CoinflipStats{

    private final OfflinePlayer player;
    private int wins;
    private int losses;
    private double profit;

    public CoinflipStats(OfflinePlayer player){
        this(player, 0, 0, 0);
    }

    public CoinflipStats(OfflinePlayer player, int wins, int losses, double profit){
        this.player = player;
        this.wins = wins;
        this.losses = losses;
        this.profit = profit;
    }

    public void addWin(double money){
        wins++;
        profit += money;
        CoinflipSQL.updateStats(this);
    }

    public void addLose(double money){
        losses++;
        profit -= money;
        CoinflipSQL.updateStats(this);
    }

    public OfflinePlayer getPlayer(){
        return player;
    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public double getProfit(){
        return Double.parseDouble(String.format("%.2f", profit));
    }
}
