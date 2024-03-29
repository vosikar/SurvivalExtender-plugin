package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

public enum QuestType{

    SMUDLA("Šmudla"),
    PROFA("Prófa"),
    KEJCHAL("Kejchal"),
    REJPAL("Rejpal"),
    STYDLIN("Stydlín"),
    STISTKO("Štístko"),
    DRIMAL("Dřímal"),
    SNEHURKA("Sněhurka"),
    ANICKA("Anička"),
    SARKA("Šárka"),
    ELISKA("Eliška"),
    ZUZKA(),
    MARKETA("Markéta"),
    //oneblock
    ANCA("Anča"),
    TONDA("Tonda"),
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
            return name().charAt(0)+name().substring(1).toLowerCase();
        return name;
    }
}
