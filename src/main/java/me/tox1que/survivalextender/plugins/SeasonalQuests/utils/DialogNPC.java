package me.tox1que.survivalextender.plugins.SeasonalQuests.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.PlayerAction;
import me.tox1que.survivalextender.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogNPC{

    private final String name;
    private String dialog;
    private String finalDialog;
    private QuestType questType;
    private ItemStack[] requestedItems;
    private PlayerAction completeAction;
    private QuestType prerequisite;
    private Date availableAfter = null;
    private final String tableName;
    private String nameColor = "#732626";
    private String chatColor = "#732626";

    public DialogNPC(String name, String tableName){
        this.name = name;
        this.tableName = tableName;
    }

    public DialogNPC(String name, String dialog, String finalDialog, QuestType questType, ItemStack[] requestedItems, PlayerAction completeAction, String tableName){
        this(name, dialog, finalDialog, questType, requestedItems, completeAction, null, tableName);
    }

    public DialogNPC(String name, String dialog, String finalDialog, QuestType questType, ItemStack[] requestedItems, PlayerAction completeAction, QuestType prerequisite, String tableName){
        this.name = name;
        this.dialog = dialog;
        this.finalDialog = finalDialog;
        this.questType = questType;
        this.requestedItems = requestedItems;
        this.completeAction = completeAction;
        this.prerequisite = prerequisite;
        this.tableName = tableName;
    }

    /* Setters */

    public DialogNPC setAvailableAfter(Date availableAfter){
        this.availableAfter = availableAfter;
        return this;
    }

    public DialogNPC setDialog(String dialog){
        this.dialog = dialog;
        return this;
    }

    public DialogNPC setFinalDialog(String finalDialog){
        this.finalDialog = finalDialog;
        return this;
    }

    public DialogNPC setQuestType(QuestType questType){
        this.questType = questType;
        return this;
    }

    public DialogNPC setRequestedItems(ItemStack[] requestedItems){
        this.requestedItems = requestedItems;
        return this;
    }

    public DialogNPC setCompleteAction(PlayerAction completeAction){
        this.completeAction = completeAction;
        return this;
    }

    public DialogNPC setPrerequisite(QuestType prerequisite){
        this.prerequisite = prerequisite;
        return this;
    }

    public DialogNPC setChatColor(String chatColor){
        this.chatColor = chatColor;
        return this;
    }

    public DialogNPC setNameColor(String nameColor){
        this.nameColor = nameColor;
        return this;
    }

    /* Setters end */

    public String getName(){
        return name;
    }

    public QuestType getQuestType(){
        return questType;
    }

    public void interact(Player player){
        PlayerProfile profile = SurvivalExtender.getInstance().getSeasonalPlugin().getProfile(player);
        if(profile.completedQuest(questType)){
            sendMessage(player, "Slíbenou odměnu jsi již dostal, více nemám.");
            return;
        }
        if(new Date().before(availableAfter)){
            sendMessage(player, "Ke mně ještě nemůžeš, až "+Utils.getFormattedDate(availableAfter)+".");
            return;
        }
        if(prerequisite != null && !profile.completedQuest(prerequisite)){
            sendMessage(player, "Sakra. Než pomůžeš mně, musíš pomoct " + prerequisite.getName() + ".");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        List<ItemStack> toRemove = new ArrayList<>();

        for(ItemStack is : requestedItems){
            if(inventory.containsAtLeast(is, is.getAmount())){
                toRemove.add(is);
                continue;
            }
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

        profile.completeQuest(questType, tableName);
        SurvivalExtender.getInstance().getSeasonalPlugin().removeInteracted(player);
        sendMessage(player, finalDialog);
        completeAction.run(player);
    }

    public void sendMessage(Player player, String message){
        player.sendMessage(String.format(ChatColor.of(nameColor) + "§l%s §7» " + ChatColor.of(chatColor) + "%s", name, message));
    }
}
