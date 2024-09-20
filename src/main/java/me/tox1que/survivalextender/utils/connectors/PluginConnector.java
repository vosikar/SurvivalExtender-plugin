package me.tox1que.survivalextender.utils.connectors;

import me.tox1que.blockbreaking.events.PlayerBreakMineBlockEvent;
import me.tox1que.challenges.customEvents.ChallengeEndEvent;
import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.managers.stats.PlayerStatistic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PluginConnector implements Listener{

    public PluginConnector(){
        Bukkit.getServer().getPluginManager().registerEvents(this, SurvivalExtender.getInstance());
    }

    @EventHandler
    public void onChallengeEnd(ChallengeEndEvent e){
        String[] players = new String[]{
                e.getFirst(),
                e.getSecond(),
                e.getThird()
        };
        for(String playerName : players){
            Player player = Bukkit.getPlayer(playerName);
            if(player != null){
                SurvivalExtender.getInstance().getStatsManager().increaseStat(player, PlayerStatistic.CHALLENGES_COMPLETED);
                CoreConnector.progressPlayerMissionDataInt(player, 229, 1, 10);
                CoreConnector.progressPlayerMissionDataInt(player, 230, 1, 50);
                CoreConnector.progressPlayerMissionDataInt(player, 231, 1, 100);
            }
        }
    }

    @EventHandler
    public void onMineBlockMine(PlayerBreakMineBlockEvent e){
        String key = e.getBlockType();
        int missionId = switch(key){
            case "easy" -> 237;
            case "medium" -> 238;
            case "hard" -> 239;
            default -> 0;
        };
        if(missionId != 0){
            CoreConnector.checkFinishMission(e.getPlayer(), missionId);
        }
    }
}
