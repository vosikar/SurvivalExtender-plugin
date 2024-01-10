package me.tox1que.survivalextender.plugins.ThreeWiseMen.utils;

public enum QuestType{

    MELICHAR,
    KASPAR,
    BALTAZAR,
    SKRITEK,
    PLETARKA,
    ;

    private final int limit;

    QuestType(){
        this(1);
    }

    QuestType(int limit){
        this.limit = limit;
    }

    public int getLimit(){
        return limit;
    }
}
