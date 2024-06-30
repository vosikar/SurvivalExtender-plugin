package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

public enum QuestType{

    BLAZE,
    SQUID,
    GLOWING_SQUID,
    RABBIT("Králík"),
    SLIME,
    POLAR_BEAR("Lední medvěd"),
    ENDERMAN,
    SHULKER,
    ;

    private final String name;

    QuestType(){
        this(null);
    }

    QuestType(String name){
        this.name = name;
    }

    public String getName(){
        if(name == null)
            return name().charAt(0)+name().substring(1).toLowerCase().replace("_", " ");
        return name;
    }
}
