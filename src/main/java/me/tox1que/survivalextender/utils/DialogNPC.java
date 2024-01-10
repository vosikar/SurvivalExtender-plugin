package me.tox1que.survivalextender.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.PlayerProfile;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.QuestType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DialogNPC{

    private final String name;
    private final List<String> dialogs;
    private final String finalDialog;
    private final QuestType questType;
    private final List<ItemStack> rewards;
    private final List<ItemStack[]> items;

    public DialogNPC(String name, List<String> dialog, String finalDialog, QuestType questType, List<ItemStack> rewards, List<ItemStack[]> items){
        this.name = name;
        this.dialogs = dialog;
        this.finalDialog = finalDialog;
        this.questType = questType;
        this.rewards = rewards;
        this.items = items;
    }

    public DialogNPC(String name, String dialog, String finalDialog, QuestType questType, ItemStack reward, ItemStack... items){
        this(name, Collections.singletonList(dialog), finalDialog, questType, Collections.singletonList(reward),
                Collections.singletonList(items));
    }

    public String getName(){
        return name;
    }

    public QuestType getQuestType(){
        return questType;
    }

    public void interact(Player player){
        PlayerProfile profile = SurvivalExtender.getInstance().getThreeWiseMenPlugin().getProfile(player);
        if(profile.completedQuest(questType)){
            sendMessage(player, "Můj úkol máš již dokončený.");
            return;
        }

        int progress = profile.getProgress(questType);

        if(SurvivalExtender.getInstance().getThreeWiseMenPlugin().addInteracted(player)){
            sendMessage(player, dialogs.get(progress));
            return;
        }

        PlayerInventory inventory = player.getInventory();
        List<ItemStack> toRemove = new ArrayList<>();

        itemsLoop:
        for(ItemStack is:items.get(progress)){
            for(ItemStack inInventory:inventory.getContents()){
                if(inInventory == null || inInventory.getType() == Material.AIR || inInventory.getItemMeta() == null)
                    continue;
                if(inInventory.getType() == is.getType() && inInventory.getItemMeta().getDisplayName().equals(is.getItemMeta().getDisplayName())
                        && inInventory.getAmount() >= is.getAmount()){
                    ItemStack itemToRemove = inInventory.clone();
                    itemToRemove.setAmount(is.getAmount());
                    toRemove.add(itemToRemove);
                    continue itemsLoop;
                }
            }
        }

        if(toRemove.size() == items.get(progress).length){
            for(ItemStack is:toRemove){
                inventory.removeItem(is);
            }

            ItemStack reward = rewards.get(progress);
            if(reward != null){
                inventory.addItem(reward);
            }

            profile.completeQuest(questType);
            SurvivalExtender.getInstance().getThreeWiseMenPlugin().removeInteracted(player);
            if(profile.getProgress(questType) == questType.getLimit()){
                sendMessage(player, finalDialog);
            }else{
                SurvivalExtender.getInstance().getThreeWiseMenPlugin().removeInteracted(player);
                sendMessage(player, dialogs.get(progress+1));
            }
        }else{
            sendMessage(player, dialogs.get(progress));
        }
    }

    public void sendMessage(Player player, String message){
        player.sendMessage(String.format(ChatColor.of("#732626")+"%s §7» "+ChatColor.of("#993333")+"%s", name, message));
    }
}
