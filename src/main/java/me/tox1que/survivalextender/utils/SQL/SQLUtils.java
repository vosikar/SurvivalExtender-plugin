package me.tox1que.survivalextender.utils.SQL;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;
import java.util.Map;

public class SQLUtils{

    private static final String[] ALL = new String[]{"*"};

    public static Connection getNewConnection(){
        try{
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(SurvivalExtender.getInstance().getDataFolder(), "database.yml"));
            String ip = config.getString("ip");
            String database = config.getString("database");
            String user = config.getString("user");
            String password = config.getString("password");
            return DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + database + "?autoReconnect=true&allowMultiQueries=true&useUnicode=yes&characterEncoding=UTF-8", user, password);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /*
        General methods
     */

//    public static void delete(String[] keys, Object[] values){
//        delete(TABLE, keys, values);
//    }

    public static void delete(String table, String[] keys, Object[] values){
        PreparedStatement ps;
        Connection connection;
        try{
            StringBuilder condition = new StringBuilder();
            for(String s : keys){
                condition.append(condition.length() > 0 ? "AND " : "")
                        .append(s).append("=? ");
            }
            connection = getNewConnection();
            String query = String.format(
                    "DELETE FROM %s %s", table, (condition.length() > 0 ? "WHERE "+condition : "")
            );
            ps = connection.prepareStatement(query);
            for(int i = 0; i < values.length; i++){
                ps.setObject(i + 1, values[i]);
            }
            ps.execute();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

//    public static void insert(Map<String, Object> data){
//        insert(TABLE, data);
//    }

    public static void insert(String table, Map<String, ?> data){
        PreparedStatement ps;
        Connection connection;
        try{
            connection = getNewConnection();
            String keys = String.join(",", data.keySet());
            StringBuilder values = new StringBuilder();
            int repeat = data.values().size();
            for(int i = 0; i < repeat; i++){
                values.append(i < repeat-1 ? "?, " : "?");
            }
            String query = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)", table, keys, values
            );
            ps = connection.prepareStatement(query);
            int i = 1;
            for(Object o:data.values()){
                ps.setObject(i, o.toString());
                i++;
            }
            ps.execute();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void select(String table, String[] keys, String[] values, int limit, SQLRunnable runnable){
        select(table, ALL, keys, values, limit, runnable);
    }

    public static void select(String table, String[] columns, String[] keys, String[] values, int limit, SQLRunnable runnable){
        select(table, columns, keys, values, limit, "", runnable);
    }

    public static void select(String table, String[] columns, String[] keys, String[] values, int limit, String order, SQLRunnable runnable){
        PreparedStatement ps;
        Connection connection;
        ResultSet result;
        try{
            connection = getNewConnection();
            StringBuilder condition = new StringBuilder("WHERE ");
            int len = values.length;
            for(int i = 0; i < len; i++){
                condition.append(keys[i]).append(i + 1 == len ? "=?" : "=? AND ");
            }
            String query = "SELECT " + String.join(",", columns) + " FROM " + table + " " + (!condition.toString().equals("WHERE ") ? condition : "");
            if(!order.equals(""))
                query += "ORDER BY "+order;
            if(limit > 0)
                query += " LIMIT " + limit;
            ps = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            for(int i = 0; i < len; i++){
                ps.setString(i + 1, values[i]);
            }
            ps.execute();
            result = ps.getResultSet();
            runnable.run(result);
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void update(String table, String[] keys, Object[] values, String condition){
        PreparedStatement ps;
        Connection connection;
        try{
            connection = getNewConnection();
            StringBuilder valuesBuilder = new StringBuilder();
            int repeat = keys.length;
            for(int i = 0; i < repeat; i++){
                String key = keys[i];
                valuesBuilder.append(i < repeat-1 ? key+"=?, " : key+"=?");
            }
            String query = String.format(
                    "UPDATE %s SET %s %s", table, valuesBuilder, (!condition.equals("") ? "WHERE "+condition : "")
            );
            ps = connection.prepareStatement(query);
            for(int i = 0; i < repeat; i++){
                ps.setObject(i+1, values[i]);
            }
            ps.execute();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void query(String query, SQLRunnable runnable, String... parameters){
        PreparedStatement ps;
        Connection connection;
        ResultSet result;
        try{
            connection = getNewConnection();
            ps = connection.prepareStatement(query);
            int i = 1;
            for(String s:parameters){
                ps.setString(i, s);
                i++;
            }
            ps.execute();
            if(runnable != null){
                result = ps.getResultSet();
                runnable.run(result);
            }
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
