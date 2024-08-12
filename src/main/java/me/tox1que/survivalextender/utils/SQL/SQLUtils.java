package me.tox1que.survivalextender.utils.SQL;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SQLUtils{

    private static final String[] ALL = new String[]{"*"};

    public static Connection getNewConnection(){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(SurvivalExtender.getInstance().getDataFolder(), "database.yml"));
        return getNewConnection(config.getString("database"));
    }

    public static Connection getNewConnection(String database){
        try{
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(SurvivalExtender.getInstance().getDataFolder(), "database.yml"));
            String ip = config.getString("ip");
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
                condition.append(!condition.isEmpty() ? "AND " : "")
                        .append(s).append("=? ");
            }
            connection = getNewConnection();
            String query = String.format(
                    "DELETE FROM %s %s", table, (!condition.isEmpty() ? "WHERE " + condition : "")
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
        insert(table, data, true);
    }

    public static void insert(String table, Map<String, ?> data, boolean async){
        if(async){
            Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> dbInsert(table, data));
        }else{
            dbInsert(table, data);
        }
    }

    private static void dbInsert(String table, Map<String, ?> data){
        PreparedStatement ps;
        Connection connection;
        try{
            connection = getNewConnection();
            String keys = String.join(",", data.keySet());
            String values = getValuesPlaceholder(data.values().size());
            String query = String.format(
                    "INSERT INTO %s (%s) VALUES (%s)", table, keys, values
            );
            ps = connection.prepareStatement(query);
            int i = 1;
            for(Object o : data.values()){
                ps.setObject(i, o.toString());
                i++;
            }
            ps.execute();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertOrUpdate(String table, Map<String, ?> data){
        insertOrUpdate(table, data, true);
    }

    public static void insertOrUpdate(String table, Map<String, ?> data, boolean async){
        if(async){
            Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> dbInsertOrUpdate(table, data));
        }else{
            dbInsertOrUpdate(table, data);
        }
    }

    private static void dbInsertOrUpdate(String table, Map<String, ?> data){
        PreparedStatement ps;
        Connection connection;
        try{
            connection = getNewConnection();
            String queryValues = getValuesPlaceholder(data.values().size());
            String update = getUpdateValues(data);
            String query = String.format("INSERT INTO %s (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
                    table, String.join(",", data.keySet()), queryValues, update);
            ps = connection.prepareStatement(query);

            Object[] dataValues = data.values().toArray(new Object[0]);
            for(int i = 0; i < dataValues.length; i++){
                ps.setObject(i + 1, dataValues[i]);
                ps.setObject(i + dataValues.length + 1, dataValues[i]);
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
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
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
                String query = "SELECT %s FROM %s %s".formatted(String.join(",", columns), table, !condition.toString().equals("WHERE ") ? condition : "");
                if(!order.isEmpty())
                    query += " ORDER BY " + order;
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
        });
    }

    public static void update(String table, String[] keys, Object[] values, String condition){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps;
            Connection connection;
            try{
                connection = getNewConnection();
                String valuesBuilder = getUpdateValues(keys, values);
                String query = String.format(
                        "UPDATE %s SET %s %s", table, valuesBuilder, (!condition.isEmpty() ? "WHERE " + condition : "")
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
        });
    }

    public static void query(String query, SQLRunnable runnable, String... parameters){
        PreparedStatement ps;
        Connection connection;
        ResultSet result;
        try{
            connection = getNewConnection();
            ps = connection.prepareStatement(query);
            int i = 1;
            for(String s : parameters){
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

    private static String getUpdateValues(Map<String, ?> data){
        StringBuilder valuesBuilder = new StringBuilder();
        String[] keys = data.keySet().toArray(new String[0]);
        int repeat = keys.length;
        for(int i = 0; i < repeat; i++){
            String key = keys[i];
            valuesBuilder.append(i < repeat - 1 ? key + "=?, " : key + "=?");
        }
        return valuesBuilder.toString();
    }

    private static String getUpdateValues(String[] keys, Object[] values){
        Map<String, Object> data = new HashMap<>();
        for(int i = 0; i < keys.length; i++){
            data.put(keys[i], values[i]);
        }
        return getUpdateValues(data);
    }

    private static String getValuesPlaceholder(int size){
        StringBuilder values = new StringBuilder();
        for(int i = 0; i < size; i++){
            values.append(i < size - 1 ? "?, " : "?");
        }
        return values.toString();
    }
}
