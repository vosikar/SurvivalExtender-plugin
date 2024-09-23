package me.tox1que.survivalextender.managers.stats;

import me.saves.core.perks.fishing.SpecialFishCatchEvent;
import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StatsListener implements Listener{

    @EventHandler
    public void onSpecialFishCatch(SpecialFishCatchEvent e){
        SurvivalExtender.getInstance().getStatsManager().increaseStat(e.getPlayer(), PlayerStatistic.FISHES_CAUGHT, e.getAmount());
    }
}
