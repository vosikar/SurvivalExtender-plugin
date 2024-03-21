package me.tox1que.survivalextender.plugins.SeasonalQuests;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.Logger;

public abstract class BaseSeason{

    protected final SeasonalPlugin plugin;
    private final boolean loaded;

    public BaseSeason(){
        Class<? extends BaseSeason> subClass = this.getClass();
        loaded = subClass.isAnnotationPresent(Season.class);
        if(!loaded){
            Logger.Console.SEVERE(subClass.getName()+", extending BaseSeason, must be annotated with @Season!");
        }
        this.plugin = SurvivalExtender.getInstance().getSeasonalPlugin();
    }

    public boolean isLoaded(){
        return loaded;
    }

    public abstract void load();
}
