package me.tox1que.survivalextender.managers.stats;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public record PlayerStats(Player player, Map<PlayerStatistic, Integer> statistics){

    public PlayerStats(Player player){
        this(player, new HashMap<>());
    }

    public int get(PlayerStatistic statistic){
        return statistics.getOrDefault(statistic, 0);
    }

    public int increase(PlayerStatistic statistic){
        return increase(statistic, 1);
    }

    public int increase(PlayerStatistic statistic, int amount){
        int current = statistics().getOrDefault(statistic, 0)+amount;
        statistics.put(statistic, current);
        return current;
    }
}
