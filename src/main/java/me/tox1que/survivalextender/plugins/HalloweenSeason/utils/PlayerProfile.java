package me.tox1que.survivalextender.plugins.HalloweenSeason.utils;

import me.tox1que.survivalextender.plugins.HalloweenSeason.HalloweenSQL;
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

    public void completeQuest(QuestType questType){
        completed.add(questType);
        HalloweenSQL.completeQuest(player, questType);
    }

    public boolean completedQuest(QuestType questType){
        return getProgress(questType) == questType.getLimit();
    }

    public Player getPlayer(){
        return player;
    }

    public int getProgress(QuestType questType){
        return Collections.frequency(completed, questType);
    }
}
