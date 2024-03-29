package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayerProfile{

    private final Player player;
    private final List<QuestType> completed;

    public PlayerProfile(Player player, List<QuestType> completed){
        this.player = player;
        this.completed = completed;
    }

    public void completeQuest(QuestType questType, String tableName){
        completed.add(questType);
        SeasonalSQL.completeQuest(player, questType, tableName);
    }

    public boolean completedQuest(QuestType questType){
        return completed.contains(questType);
    }

    public Player getPlayer(){
        return player;
    }

    public int getProgress(QuestType questType){
        return Collections.frequency(completed, questType);
    }
}
