package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.olds;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.SeasonalQuests.BaseSeason;
import me.tox1que.survivalextender.plugins.SeasonalQuests.Season;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.QuestType;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.enums.ServerType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Calendar;
import java.util.Date;

@Season(from = "2024-06-30", until = "2024-09-29", server = ServerType.SURVIVAL)
public class SummerOldSurvival extends BaseSeason{

    @Override
    public void load(){
        SurvivalExtender.getInstance().getServer().getPluginManager().registerEvents(new SummerOldSurvivalListeners(), SurvivalExtender.getInstance());
        plugin.addNPC(
            new DialogNPC("Blaze", "quests_summer")
                .setDialog("Zdravím tě, artefakt ohně ti klidně dám, ale potřeboval bych 32 Blaze Rodů, 64 Blaze Powderů, 128 Magma Blocků a 128 Nether Wart Blocků. Jakmile mi to doneseš, odměna tě nemine.")
                .setFinalDialog("Děkuji ti mockrát. Tady máš artefakt ohně.")
                .setQuestType(QuestType.BLAZE)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.BLAZE_ROD, 32),
                    new ItemStack(Material.BLAZE_POWDER, 64),new ItemStack(Material.MAGMA_BLOCK, 128), new ItemStack(Material.NETHER_WART_BLOCK, 128)})
                .setChatColor("#F39C52")
                .setNameColor("#F58A0A")
                .setAvailableAfter(new Date(2024-1900, Calendar.JULY, 1))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_ohne"))
        );
        plugin.addNPC(
                new DialogNPC("Glowing squid", "quests_summer")
                        .setDialog("Zdravím, artefakt světla dostaneš, pokud mi přineseš 32 Shroomlightu, 64 Glowstonu a 64 Lantern.")
                        .setFinalDialog("Díky moc, tady máš artefakt světla.")
                        .setQuestType(QuestType.GLOWING_SQUID)
                        .setRequestedItems(new ItemStack[]{new ItemStack(Material.SHROOMLIGHT, 32), new ItemStack(Material.GLOWSTONE, 64),
                                new ItemStack(Material.LANTERN, 64)})
                        .setChatColor("#D1ED23")
                        .setNameColor("#C3E104")
                        .setAvailableAfter(new Date(2024-1900, Calendar.JULY, 15))
                        .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_svetla"))
        );
        plugin.addNPC(
            new DialogNPC("Squid", "quests_summer")
                .setDialog("Ahoj, artefakt vody ti dám, ale přines mi 16 Tube Coralů, 64 Dried kelp Blocků, 64 Sponge Blocků a 128 Prismarine Blocků. Pokud to dokážeš, artefakt ti předám.")
                .setFinalDialog("Díky, tady máš svůj artefakt vody.")
                .setQuestType(QuestType.SQUID)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.TUBE_CORAL, 16), new ItemStack(Material.DRIED_KELP_BLOCK, 64),
                    new ItemStack(Material.SPONGE, 64), new ItemStack(Material.PRISMARINE_BRICKS, 128)})
                .setChatColor("#72C1E9")
                .setNameColor("#1A8BC4")
                .setAvailableAfter(new Date(2024-1900, Calendar.JULY, 8))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_vody"))
        );
        plugin.addNPC(
            new DialogNPC("Králík", "quests_summer")
                .setDialog("Čau, chceš-li artefakt skoku, přines 64 Brown Mushroom 128 Carrot a 128 Grass.")
                .setFinalDialog("Děkuji moc, tady máš artefakt skoku.")
                .setQuestType(QuestType.RABBIT)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.BROWN_MUSHROOM, 64), new ItemStack(Material.CARROT, 128),
                    new ItemStack(Material.GRASS, 128)})
                .setChatColor("#967523")
                .setNameColor("#9C7106")
                .setAvailableAfter(new Date(2024-1900, Calendar.JULY, 22))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_skoku"))
        );
        plugin.addNPC(
            new DialogNPC("Slime", "quests_summer")
                .setDialog("Zdravím tě, přines mi 32 Sticky Pistonů a 128 Slime Blocků, jakmile mi to přineseš, dám ti artefakt slizu.")
                .setFinalDialog("Díky, tady je tvůj artefakt slizu.")
                .setQuestType(QuestType.SLIME)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.STICKY_PISTON, 32), new ItemStack(Material.SLIME_BLOCK, 128)})
                .setChatColor("#82E641")
                .setNameColor("#5DDE09")
                .setAvailableAfter(new Date(2024-1900, Calendar.JULY, 29))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_slizu"))
        );
        plugin.addNPC(
            new DialogNPC("Lední medvěd", "quests_summer")
                .setDialog("Čau, pokud mi přineseš 128 Ice blocků, 128 Packed Ice Blocků, 128 Blue Ice Blocků a 128 Snow Blocků, dám ti artefakt ledu.")
                .setFinalDialog("Děkuji, tady máš artefakt ledu.")
                .setQuestType(QuestType.POLAR_BEAR)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.ICE, 128), new ItemStack(Material.PACKED_ICE, 128),
                    new ItemStack(Material.BLUE_ICE, 128)})
                .setChatColor("#629AC5")
                .setNameColor("#1484DA")
                .setAvailableAfter(new Date(2024-1900, Calendar.AUGUST, 5))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_ledu"))
        );
        plugin.addNPC(
            new DialogNPC("Enderman", "quests_summer")
                .setDialog("Ahoj, jestli chceš artefakt teleportace, přines mi 16 Ender Pearl, 32 End Crystalů, 64 Eye of Ender a 128 Obsidian Blocků.")
                .setFinalDialog("Díky moc, tady máš artefakt teleportace.")
                .setQuestType(QuestType.ENDERMAN)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.END_CRYSTAL, 32),
                    new ItemStack(Material.ENDER_EYE, 64), new ItemStack(Material.OBSIDIAN, 128)})
                .setChatColor("#588EAC")
                .setNameColor("#0684CA")
                .setAvailableAfter(new Date(2024-1900, Calendar.AUGUST, 12))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_teleportace"))
        );
        plugin.addNPC(
            new DialogNPC("Shulker", "quests_summer")
                .setDialog("Čau, artefakt létání ti dám, pokud mi přineseš 128 Shulker Shellů, 128 Chorus Fruitů a 128 Popped Chorus Fruitů.")
                .setFinalDialog("Děkuji moc, tady máš svůj artefakt létání.")
                .setQuestType(QuestType.SHULKER)
                .setRequestedItems(new ItemStack[]{new ItemStack(Material.SHULKER_SHELL, 128), new ItemStack(Material.CHORUS_FRUIT, 128),
                    new ItemStack(Material.POPPED_CHORUS_FRUIT, 128)})
                .setChatColor("#AF73B9")
                .setNameColor("#C15ED2")
                .setAvailableAfter(new Date(2024-1900, Calendar.AUGUST, 19))
                    .setCompleteAction(player -> ItemUtils.giveKit(player, "summer2024_artefakt_letani"))
        );
    }
}
