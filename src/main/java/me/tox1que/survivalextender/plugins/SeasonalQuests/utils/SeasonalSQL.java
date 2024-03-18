package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.SQL.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeasonalSQL{

    private final static String TABLE_NAME = "quests_spring";

    public static void completeQuest(Player player, QuestType type){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet result = null;

            try{
                con = SQLUtils.getNewConnection();
                String value = type.toString().toLowerCase();
                ps = con.prepareStatement(
                        String.format("INSERT INTO %s (`player`, `character`, `completed`) VALUES (?, '%s', 1) ON DUPLICATE KEY UPDATE `completed`=`completed`+1", TABLE_NAME, value),
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
                );
                ps.setString(1, player.getName());
                ps.execute();
                ps.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                try{
                    ps.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadPlayerProfile(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet result = null;

            try{
                con = SQLUtils.getNewConnection();
                ps = con.prepareStatement("SELECT * FROM "+TABLE_NAME+" WHERE player=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, player.getName());
                ps.execute();
                result = ps.getResultSet();

                List<QuestType> completed = new ArrayList<>();
                while(result.next()){
                    completed.add(QuestType.valueOf(result.getString("character").toUpperCase()));
                }
                SurvivalExtender.getInstance().getSeasonalPlugin().addProfile(player, new PlayerProfile(player, completed));
                ps.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                try{
                    ps.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
