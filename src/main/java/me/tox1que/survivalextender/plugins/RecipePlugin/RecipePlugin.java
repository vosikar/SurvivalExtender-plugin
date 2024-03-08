package me.tox1que.survivalextender.plugins.RecipePlugin;

import me.tox1que.survivalextender.plugins.RecipePlugin.commands.RecipeCommand;
import me.tox1que.survivalextender.plugins.RecipePlugin.listeners.RecipeListeners;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;

public class RecipePlugin extends BasePlugin{

    public RecipePlugin(){
        super();
    }

    @Override
    public void load(){
        main.getServer().getPluginManager().registerEvents(new RecipeListeners(), main);
        new RecipeCommand(this);
    }
}
