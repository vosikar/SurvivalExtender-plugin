package me.tox1que.survivalextender.plugins.HalloweenSeason.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.SQL.PlayerSQL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DialogNPC{

    private final String name;
    private final List<String> dialogs;
    private final QuestType questType;
    private final List<ItemStack> rewards;
    private final List<ItemStack[]> items;
    private final boolean multi;

    public DialogNPC(String name, List<String> dialog, QuestType questType, List<ItemStack> rewards, List<ItemStack[]> items, boolean multi){
        this.name = name;
        this.dialogs = dialog;
        this.questType = questType;
        this.rewards = rewards;
        this.items = items;
        this.multi = multi;
    }

    public DialogNPC(String name, String dialog, QuestType questType, ItemStack reward, ItemStack... items){
        this(name, Collections.singletonList(dialog), questType, Collections.singletonList(reward),
                Collections.singletonList(items), false);
    }

    public String getName(){
        return name;
    }

    public void interact(Player player){
        PlayerProfile profile = SurvivalExtender.getInstance().getHalloweenPlugin().getProfile(player);
        if(profile.completedQuest(questType)){
            sendMessage(player, "Můj úkol máš již dokončený.");
            return;
        }

        int progress = profile.getProgress(questType);

        if(SurvivalExtender.getInstance().getHalloweenPlugin().addInteracted(player)){
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
                    toRemove.add(inInventory);
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
            SurvivalExtender.getInstance().getHalloweenPlugin().removeInteracted(player);
            if(profile.getProgress(questType) == questType.getLimit()){
                if(name.equals("Lupič")){
                    sendMessage(player, "Jen pro pořádek, neřekl jsem nic o tom, že ti něco dám. :)");
                    PlayerSQL.addPermission(player.getName(), "core.tag.halloween2023", "all");
                }else{
                    sendMessage(player, "Děkuji ti za pomoc! Zde je tvá odměna.");
                }
            }else{
                SurvivalExtender.getInstance().getHalloweenPlugin().removeInteracted(player);
                sendMessage(player, dialogs.get(progress+1));
            }
        }else{
            sendMessage(player, dialogs.get(progress));
        }
    }

    private void sendMessage(Player player, String message){
        player.sendMessage(String.format(ChatColor.of("#732626")+"%s §7» "+ChatColor.of("#993333")+"%s", name, message));
    }
}
