package me.tox1que.survivalextender.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.SQL.SQLUtils;
import me.tox1que.survivalextender.utils.enums.ServerType;

import java.util.HashMap;
import java.util.Map;

public class Logger{

    public enum Database{
        Balance("balance", "player", "old", "new", "value", "location", "comment"),
        ;

        private final String name;
        private final String[] keys;

        Database(String name, String... keys){
            if(Utils.getServerType() == ServerType.SURVIVAL_REWORK){
                name += "_rework";
            }else if(Utils.getServerType() == ServerType.ONEBLOCK){
                name = "oneblock_"+name;
            }
            this.name = name;
            this.keys = keys;
        }

        public void write(boolean async, Object... values){
            if(values.length != this.keys.length){
                Console.SEVERE("Lengths of KEYS and VALUES does not match for Log "+this.name());
                return;
            }
            Map<String, Object> data = new HashMap<>();
            for(int i = 0; i < keys.length; i++){
                data.put(this.keys[i], values[i]);
            }
            SQLUtils.insert("minehub_logs."+this.name, data, async);
        }
    }

    public static class Console{

        public static void INFO(String msg){
            SurvivalExtender.getInstance().getLogger().info(msg);
        }

        public static void SEVERE(String msg){
            SurvivalExtender.getInstance().getLogger().severe(msg);
        }

        public static void WARNING(String msg){
            SurvivalExtender.getInstance().getLogger().warning(msg);
        }
    }

}
