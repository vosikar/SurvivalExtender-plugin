package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.PlayerAction;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;

public class DialogNPC{

    private final String name;
    private final String dialog;
    private final String finalDialog;
    private final QuestType questType;
    private final ItemStack[] requestedItems;
    private final PlayerAction completeAction;
    private final QuestType prerequisite;

    public DialogNPC(String name, String dialog, String finalDialog, QuestType questType, ItemStack[] requestedItems, PlayerAction completeAction){
        this(name, dialog, finalDialog, questType, requestedItems, completeAction, null);
    }

    public DialogNPC(String name, String dialog, String finalDialog, QuestType questType, ItemStack[] requestedItems, PlayerAction completeAction, QuestType prerequisite){
        this.name = name;
        this.dialog = dialog;
        this.finalDialog = finalDialog;
        this.questType = questType;
        this.requestedItems = requestedItems;
        this.completeAction = completeAction;
        this.prerequisite = prerequisite;
    }

    public String getName(){
        return name;
    }

    public QuestType getQuestType(){
        return questType;
    }

    public void interact(Player player){
        PlayerProfile profile = SurvivalExtender.getInstance().getSeasonalPlugin().getProfile(player);
        if(profile.completedQuest(questType)){
            sendMessage(player, "Sice jsi odvedl dobrou práci, ale tvojí pomoc už nepotřebuji.");
            return;
        }
        if(prerequisite != null && !profile.completedQuest(prerequisite)){
            sendMessage(player, "Sakra. Než pomůžeš mně, musíš pomoct " + prerequisite.getName() + ".");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        List<ItemStack> toRemove = new ArrayList<>();

        for(ItemStack is : requestedItems){
            for(ItemStack inInventory : inventory.getContents()){
                if(inInventory == null || inInventory.getType() == Material.AIR || inInventory.getItemMeta() == null)
                    continue;
                if(inInventory.getType() != is.getType())
                    continue;
                if(!inInventory.getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName()))
                    continue;
                if(inInventory.getAmount() < is.getAmount()){
                    continue;
                }
                if(is.getType().toString().contains("POTION")){
                    PotionMeta itemMeta = (PotionMeta) is.getItemMeta();
                    PotionMeta currentItemMeta = (PotionMeta) inInventory.getItemMeta();
                    if(itemMeta.getBasePotionData().getType() != currentItemMeta.getBasePotionData().getType()){
                        continue;
                    }
                    if(itemMeta.getBasePotionData().isExtended() != currentItemMeta.getBasePotionData().isExtended()){
                        continue;
                    }
                    if(itemMeta.getBasePotionData().isUpgraded() != currentItemMeta.getBasePotionData().isUpgraded()){
                        continue;
                    }
                }
                if(player.getInventory().containsAtLeast(is, is.getAmount())){
                    toRemove.add(is);
                }
            }
        }

        if(toRemove.size() != requestedItems.length){
            sendMessage(player, dialog);
            return;
        }

        for(ItemStack is : toRemove){
            inventory.removeItem(is);
        }

        profile.completeQuest(questType);
        SurvivalExtender.getInstance().getSeasonalPlugin().removeInteracted(player);
        sendMessage(player, finalDialog);
        completeAction.run(player);
    }

    public void sendMessage(Player player, String message){
        player.sendMessage(String.format(ChatColor.of("#732626") + "%s §7» " + ChatColor.of("#993333") + "%s", name, message));
    }
}
