package me.tox1que.survivalextender.managers.stats;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.SQL.SQLUtils;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StatsManager{

    public Map<String, PlayerStats> playerStats;

    public StatsManager(){
        this.playerStats = new HashMap<>();
        SurvivalExtender.getInstance().getServer().getPluginManager().registerEvents(new StatsListener(), SurvivalExtender.getInstance());
    }

    public int increaseStat(Player player, PlayerStatistic statistic){
        return increaseStat(player, statistic, 1);
    }

    public int increaseStat(Player player, PlayerStatistic statistic, int amount){
        int current = playerStats.get(player.getName()).increase(statistic, amount);
        this.saveStats(player);
        return current;
    }

    public void loadStats(Player player){
        SQLUtils.select("player_stats", new String[]{"player"}, new String[]{player.getName()}, 1, result -> {
            PlayerStats stats;
            if(result.first()){
                Map<PlayerStatistic, Integer> playerStatistics = new HashMap<>();
                for(PlayerStatistic value : PlayerStatistic.values()){
                    String column = value.toString();
                    try{
                        result.findColumn(column);
                        playerStatistics.put(value, result.getInt(column));
                    }catch(SQLException e){
                        playerStatistics.put(value, 0);
                        Logger.Console.SEVERE("Could not find column "+column+" while loading player stats for "+player.getName());
                    }
                }
                stats = new PlayerStats(player, playerStatistics);
            }else{
                stats = new PlayerStats(player);
            }
            playerStats.put(player.getName(), stats);
        });
    }

    public void saveStats(Player player){
        Map<String, Object> data = new HashMap<>();
        data.put("player", player.getName());

        PlayerStats stats = playerStats.get(player.getName());
        for(PlayerStatistic statistic : PlayerStatistic.values()){
            data.put(statistic.toString(), stats.get(statistic));
        }
        SQLUtils.insertOrUpdate("player_stats", data);
    }

    public PlayerStats getStats(Player player){
        return playerStats.get(player.getName());
    }
}
