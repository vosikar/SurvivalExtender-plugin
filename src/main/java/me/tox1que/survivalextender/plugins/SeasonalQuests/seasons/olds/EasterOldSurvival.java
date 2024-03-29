package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.olds;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.SeasonalQuests.BaseSeason;
import me.tox1que.survivalextender.plugins.SeasonalQuests.Season;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.QuestType;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.enums.ServerType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Season(from = "2024-03-28", until = "2024-04-05", server = ServerType.SURVIVAL)
public class EasterOldSurvival extends BaseSeason{

    @Override
    public void load(){
        SurvivalExtender.getInstance().getCommand("pomlazka").setExecutor(new EasterOldsPomlazkaCommand());
        SurvivalExtender.getInstance().getServer().getPluginManager().registerEvents(new EasterOldSurvivalListeners(), SurvivalExtender.getInstance());
        String finalDialog = "Děkuji za tvou ochotu, zde je něco na oplátku.";

        //Anička
        plugin.addNPC(
                new DialogNPC(
                        "Anička",
                        "Ahoj, připravuji výslužky pro koledníky, jenomže mi došla vajíčka. Přinesl bys mi, prosím, 8x vajíčko?",
                        finalDialog,
                        QuestType.ANICKA,
                        new ItemStack[]{new ItemStack(Material.EGG, 8)},
                        player -> ItemUtils.giveKit(player, "easter2024_stuzka_zluta"),
                        "quests_easter"
                )
        );

        //Šárka
        plugin.addNPC(
                new DialogNPC(
                        "Šárka",
                        "Ahoj, rozhodla jsem se pro koledníky upéct sušenky, jenomže mi došlo kakao. Přinesl bys mi, prosím, 4x kakaové boby?",
                        finalDialog,
                        QuestType.SARKA,
                        new ItemStack[]{new ItemStack(Material.COCOA_BEANS, 4)},
                        player -> ItemUtils.giveKit(player, "easter2024_stuzka_oranzova"),
                        "quests_easter"
                )
        );

        //Eliška
        plugin.addNPC(
                new DialogNPC(
                        "Eliška",
                        "Ahoj, chtěla jsem nabarvit pár vajíček jako výslužku pro koledníky, jenomže mi došly barvičky. Přinesl bys mi, prosím, 2x červené, 2x modré a 2x žluté barvivo?",
                        finalDialog,
                        QuestType.ELISKA,
                        new ItemStack[]{new ItemStack(Material.RED_DYE, 2), new ItemStack(Material.BLUE_DYE, 2), new ItemStack(Material.YELLOW_DYE, 2)},
                        player -> ItemUtils.giveKit(player, "easter2024_stuzka_cervena"),
                        "quests_easter"
                )
        );

        //Zuzka
        plugin.addNPC(
                new DialogNPC(
                        "Zuzka",
                        "Ahoj, chtěla bych na velikonoce upéct velikonočního beránka, ale došly mi ingredience. Přinesl bys mi, prosím, 4x cukr, 4x pšenici a 1x mléko?",
                        finalDialog,
                        QuestType.ZUZKA,
                        new ItemStack[]{new ItemStack(Material.SUGAR, 4), new ItemStack(Material.WHEAT, 4), new ItemStack(Material.MILK_BUCKET)},
                        player -> ItemUtils.giveKit(player, "easter2024_stuzka_zelena"),
                        "quests_easter"
                )
        );

        //Markéta
        plugin.addNPC(
                new DialogNPC(
                        "Markéta",
                        "Ahoj, chtěla bych na velikonoce upéct vánočku, ale došly mi ingredience. Přinesl bys mi, prosím, 4x cukr, 4x med a 3x vejce?",
                        finalDialog,
                        QuestType.MARKETA,
                        new ItemStack[]{new ItemStack(Material.SUGAR, 4), new ItemStack(Material.HONEYCOMB, 4), new ItemStack(Material.EGG, 3)},
                        player -> ItemUtils.giveKit(player, "easter2024_stuzka_modra"),
                        "quests_easter"
                )
        );
    }
}
