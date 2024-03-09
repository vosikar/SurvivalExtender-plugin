package me.tox1que.survivalextender.plugins.CoinflipPlugin.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.SQL.SQLUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CoinflipSQL{

    public static void updateStats(CoinflipStats stats){
        Map<String, String> data = new HashMap<>();
        data.put("player", stats.getPlayer().getName());
        data.put("wins", stats.getWins()+"");
        data.put("losses", stats.getLosses()+"");
        data.put("profit", stats.getProfit()+"");
        data.put("announcements", stats.hasAnnouncements() ? "1" : "0");
        SQLUtils.insertOrUpdate("coinflip_stats", data);
    }

    public static void loadPlayer(Player player){
        SQLUtils.select("coinflip_stats", new String[]{"player"}, new String[]{player.getName()}, 1, result -> {
            CoinflipStats stats;
            if(result.first()){
                stats = new CoinflipStats(player, result.getInt("wins"), result.getInt("losses"),
                        result.getDouble("profit"), result.getInt("announcements") == 1);
            }else{
                stats = new CoinflipStats(player);
            }
            SurvivalExtender.getInstance().getCoinflipPlugin().setStats(player, stats);
        });
    }

}
