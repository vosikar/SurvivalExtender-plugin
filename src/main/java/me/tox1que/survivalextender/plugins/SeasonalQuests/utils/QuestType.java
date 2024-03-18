package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

public enum QuestType{

    SMUDLA("Šmudla"),
    PROFA("Prófa"),
    KEJCHAL("Kejchal"),
    REJPAL("Rejpal"),
    STYDLIN("Stydlín"),
    STISTKO("Štístko"),
    DRIMAL("Dřímal"),
    SNEHURKA("Sněhurka")
    ;

    private final String name;

    QuestType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
