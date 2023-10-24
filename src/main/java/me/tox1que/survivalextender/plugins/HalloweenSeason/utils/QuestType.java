package me.tox1que.survivalextender.plugins.HalloweenSeason.utils;

public enum QuestType{


    JEZIBABA(1),
    PREKUPNIK(2),
    PRUZKUMNIK(1),
    LUPIC(3)
    ;

    private final int limit;

    QuestType(int limit){
        this.limit = limit;
    }

    public int getLimit(){
        return limit;
    }
}
