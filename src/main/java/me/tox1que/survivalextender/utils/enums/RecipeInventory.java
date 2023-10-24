package me.tox1que.survivalextender.utils.enums;

import java.util.Arrays;
import java.util.List;

public enum RecipeInventory{

    Crafting(Arrays.asList(11, 12, 13, 20, 21, 22, 29, 30, 31)),
    Furnace(Arrays.asList(12, 30)),
    StoneCutting(Arrays.asList(12, 30)),
    Smithing(Arrays.asList(19, 20, 22)),
    ;

    private final List<Integer> indexes;

    RecipeInventory(List<Integer> indexes){
        this.indexes = indexes;
    }

    public List<Integer> getIndexes(){
        return indexes;
    }
}
