package me.tox1que.survivalextender.utils.SQL;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerSQL{

    public static void addPermission(Player player, String permission, String server){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet result = null;

            try{
                con = SQLUtils.getNewConnection();
                ps = con.prepareStatement(
                        "INSERT INTO `minehub_global`.`user_permissions`(name, permission, value, server) VALUES(?, ?, 1, ?)",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE
                );
                ps.setString(1, player.getName());
                ps.setString(2, permission);
                ps.setString(3, server);
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
}
