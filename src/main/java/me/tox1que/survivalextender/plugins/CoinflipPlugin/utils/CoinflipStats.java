package me.tox1que.survivalextender.plugins.CoinflipPlugin.utils;

import org.bukkit.OfflinePlayer;

public class CoinflipStats{

    private final OfflinePlayer player;
    private int wins;
    private int losses;
    private double profit;
    private boolean announcements;

    public CoinflipStats(OfflinePlayer player){
        this(player, 0, 0, 0, true);
    }

    public CoinflipStats(OfflinePlayer player, int wins, int losses, double profit, boolean announcements){
        this.player = player;
        this.wins = wins;
        this.losses = losses;
        this.profit = profit;
        this.announcements = announcements;
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

    public boolean hasAnnouncements(){
        return announcements;
    }

    public String toggleAnnouncements(){
        announcements = !announcements;
        CoinflipSQL.updateStats(this);
        return announcements ? "Zapnul" : "Vypnul";
    }
}
