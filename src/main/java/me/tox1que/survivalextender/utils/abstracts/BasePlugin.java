package me.tox1que.survivalextender.utils.abstracts;

import me.tox1que.survivalextender.SurvivalExtender;

public abstract class BasePlugin{

    protected final SurvivalExtender main;

    public BasePlugin(){
        this.main = SurvivalExtender.getInstance();
        main.addPlugin(this);
    }

    public abstract void load();
}
