package me.tox1que.survivalextender.utils.connectors;

import me.saves.core.Main;
import me.saves.core.managers.ItemsManager;
import me.tox1que.survivalextender.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CoreConnector{

    public static void addItemsToInventoryOrSafe(Player player, ItemStack... items){
        Logger.Console.INFO("adding " + items.length + " items through Core to " + player.getName());
        ItemsManager.addItemsToInventoryOrSafe(player, items);
    }

    public static void addScore(Player player, int bonus, String reason){
        Main.getPlugin().getPlayerManager().getUser(player).getLevelAccount().giveScore(bonus, reason);
    }

    public static void checkFinishMission(Player player, int missionId){
        Main.getPlugin().getMissionManager().checkFinishMission(player, missionId);
    }

    public static void progressPlayerMissionDataInt(Player player, int missionId, int addition, int reachLimit){
        Main.getPlugin().getMissionManager().progressPlayerMissionDataInt(player, missionId, addition, reachLimit);
    }
}
