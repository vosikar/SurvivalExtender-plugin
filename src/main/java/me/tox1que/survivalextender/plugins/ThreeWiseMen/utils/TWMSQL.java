package me.tox1que.survivalextender.plugins.ThreeWiseMen.utils;

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

public class TWMSQL{

    public static void completeQuest(Player player, QuestType type){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet result = null;

            try{
                con = SQLUtils.getNewConnection();
                String value = type.toString().toLowerCase();
                ps = con.prepareStatement(
                        String.format("INSERT INTO quests_tri_kralove (player, %s) VALUES (?, 1) ON DUPLICATE KEY UPDATE %s=%s + 1", value, value, value),
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
                ps = con.prepareStatement("SELECT * FROM quests_tri_kralove WHERE player=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, player.getName());
                ps.execute();
                result = ps.getResultSet();

                List<QuestType> completed = new ArrayList<>();
                if(result.first()){
                    for(QuestType questType:QuestType.values()){
                        int amount = result.getInt(questType.toString().toLowerCase());
                        for(int i = 0; i < amount; i++){
                            completed.add(questType);
                        }
                    }
                }
                SurvivalExtender.getInstance().getThreeWiseMenPlugin().addProfile(player, new PlayerProfile(player, completed));
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
