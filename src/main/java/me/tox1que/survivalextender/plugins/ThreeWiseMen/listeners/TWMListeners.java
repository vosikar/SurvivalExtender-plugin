package me.tox1que.survivalextender.plugins.ThreeWiseMen.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.PlayerProfile;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.QuestType;
import me.tox1que.survivalextender.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.TWMSQL;
import me.tox1que.survivalextender.utils.Logger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TWMListeners implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        TWMSQL.loadPlayerProfile(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        DialogNPC npc = SurvivalExtender.getInstance().getThreeWiseMenPlugin().getNPC(e.getRightClicked().getName());
        if(npc == null)
            return;

        Player player = e.getPlayer();
        PlayerProfile profile = SurvivalExtender.getInstance().getThreeWiseMenPlugin().getProfile(player);
        if(npc.getQuestType() == QuestType.KASPAR && !profile.completedQuest(QuestType.MELICHAR)){
            npc.sendMessage(player, "Nejdříve pomoz Melicharovi.");
            return;
        }else if(npc.getQuestType() == QuestType.BALTAZAR && !profile.completedQuest(QuestType.KASPAR)){
            npc.sendMessage(player, "Nejdříve pomoz Kašparovi.");
            return;
        }

        npc.interact(player);
    }
}
