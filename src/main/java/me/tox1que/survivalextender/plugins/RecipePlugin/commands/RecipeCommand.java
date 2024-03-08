package me.tox1que.survivalextender.plugins.RecipePlugin.commands;

import me.tox1que.survivalextender.plugins.RecipePlugin.RecipePlugin;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BaseCommand;
import me.tox1que.survivalextender.utils.enums.RecipeInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import java.util.*;
import java.util.stream.Stream;

public class RecipeCommand extends BaseCommand{

    private final ItemStack fill;
    private final int furnaceIndex;
    private final int recipeResultIndex;

    public RecipeCommand(RecipePlugin plugin){
        super("recipe", plugin, "<item>");

        this.fill = Utils.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", null);
        this.furnaceIndex = 21;
        this.recipeResultIndex = 24;

        this.completions.add(Stream.of(Material.values())
                .map(Material::name)
                .toArray(String[]::new));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player player)){
            return false;
        }

        if(args.length < 1){
            sendUsage(player);
            return false;
        }

        Material material = Material.matchMaterial(args[0].toUpperCase());
        if(material == null){
            sendMessage(player, "Zadaný item neexistuje.");
            return false;
        }
        ItemStack item = new ItemStack(material);

        List<Recipe> recipes = Bukkit.getServer().getRecipesFor(item);
        int index = 0;
        if(args.length >= 2){
            try{
                index = Integer.parseInt(args[1]);
                if(index >= recipes.size())
                    index = recipes.size()-1;
            }catch(NumberFormatException ignored){}
        }

        if(!recipes.isEmpty()){
            Recipe recipe = recipes.get(index);
            Inventory inv = createRecipeInventory(recipe, index+1, recipes.size());
            if(inv != null){
                player.openInventory(inv);
            }else{
                sendMessage(player, "Pro recept tohoto typu ještě nebyla přidána podpora. Pokud si myslíš, že se jedná o chybu, kontaktuj AT.");
            }
        }else{
            sendMessage(player, "Tento item nemá recept.");
        }

        return false;
    }

    private Inventory createRecipeInventory(Recipe recipe, int page, int max){
        Inventory inv = Bukkit.createInventory(null, 6*9, "Recept - "+recipe.getResult().getType().toString().toLowerCase());

        List<Integer> recipeIndexes;

        if(recipe instanceof ShapedRecipe shapedRecipe){
            recipeIndexes = RecipeInventory.Crafting.getIndexes();
            String[] shape = shapedRecipe.getShape();
            Map<Character, ItemStack> ingredientMap = shapedRecipe.getIngredientMap();

            for(int row = 0; row < shape.length; row++){
                for(int col = 0; col < shape[row].length(); col++){
                    char ingredientChar = shape[row].charAt(col);
                    ItemStack ingredient = ingredientMap.get(ingredientChar);
                    inv.setItem(recipeIndexes.get(row*3+col), ingredient);
                }
            }
        }else if(recipe instanceof ShapelessRecipe shapelessRecipe){
            recipeIndexes = RecipeInventory.Crafting.getIndexes();
            int index = 0;
            for(ItemStack i: shapelessRecipe.getIngredientList()){
                inv.setItem(recipeIndexes.get(index), i);
                index++;
            }
        }else if(recipe instanceof CookingRecipe<?> cookingRecipe){
            recipeIndexes = RecipeInventory.Furnace.getIndexes();
            inv.setItem(recipeIndexes.get(0), cookingRecipe.getInput());
            inv.setItem(recipeIndexes.get(1), new ItemStack(Material.COAL));

            Material furnace = Material.FURNACE;
            if(cookingRecipe instanceof BlastingRecipe){
                furnace = Material.BLAST_FURNACE;
            }else if(cookingRecipe instanceof CampfireRecipe){
                furnace = Material.CAMPFIRE;
            }else if(cookingRecipe instanceof SmokingRecipe){
                furnace = Material.SMOKER;
            }
            inv.setItem(furnaceIndex, Utils.createItem(furnace, "",
                    Arrays.asList("", "§7Doba pečení: "+(cookingRecipe.getCookingTime()/20)+" sekund", "§7Zkušenosti: "+cookingRecipe.getExperience())));
        }else if(recipe instanceof StonecuttingRecipe stonecuttingRecipe){
            recipeIndexes = RecipeInventory.StoneCutting.getIndexes();
            inv.setItem(recipeIndexes.get(0), stonecuttingRecipe.getInput());
            inv.setItem(recipeIndexes.get(1), new ItemStack(Material.STONECUTTER));
        }else if(recipe instanceof SmithingRecipe smithingRecipe){
            recipeIndexes = RecipeInventory.Smithing.getIndexes();
            inv.setItem(recipeIndexes.get(0), smithingRecipe.getBase().getItemStack());
            inv.setItem(recipeIndexes.get(1), smithingRecipe.getAddition().getItemStack());
            inv.setItem(recipeIndexes.get(2), new ItemStack(Material.SMITHING_TABLE));
        }else{
            return null;
        }

        for(int i = 0; i < inv.getSize(); i++){
            if(!recipeIndexes.contains(i) && i != furnaceIndex){
                inv.setItem(i, fill);
            }
        }
        inv.setItem(recipeResultIndex, recipe.getResult());

        if(page < max){
            inv.setItem(53, Utils.createItem(Material.GREEN_STAINED_GLASS_PANE, "§aDalší recept",
                    Collections.singletonList(String.format("§a%d/%d", page, max))));
        }
        if(page <= max && page > 1){
            inv.setItem(45, Utils.createItem(Material.GREEN_STAINED_GLASS_PANE, "§aPředchozí recept",
                    Collections.singletonList(String.format("§a%d/%d", page, max))));
        }

        return inv;
    }
}
