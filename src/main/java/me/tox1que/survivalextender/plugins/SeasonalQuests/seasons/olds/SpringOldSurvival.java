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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

@Season(from = "2024-03-22", until = "2024-06-21", server = ServerType.SURVIVAL)
public class SpringOldSurvival extends BaseSeason{

    @Override
    public void load(){
        SurvivalExtender.getInstance().getServer().getPluginManager().registerEvents(new SpringOldSurvivalListeners(), SurvivalExtender.getInstance());
        
        //Šmudla
        plugin.addNPC(
                new DialogNPC(
                        "Šmudla",
                        "Ahoj a zdar, dobrodruhu! Měl bych pro tebe menší nabídku. Ty bys za mě dneska odpracoval šichtu a já ti naoplátku dám nějaké drobné. Každý den musíme vytěžit 16 diamantů, 20 železa a 32 uhlí.",
                        "Díky moc, díky tobě si mohu dneska zdřímnout.",
                        QuestType.SMUDLA,
                        new ItemStack[]{new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.IRON_INGOT, 20), new ItemStack(Material.COAL, 32)},
                        player -> {
                            PaymentUtils.giveMoney(18000, "spring quest Šmudla", player);
                            PaymentUtils.addScore(player, 0, "spring quest Šmudla");
                        },
                        "quests_spring"
                )
        );

        //Prófa
        plugin.addNPC(
                new DialogNPC(
                        "Prófa",
                        "Dobrý den. Potřeboval bych doplnit zásoby svých léků. Abych mohl dalších pár let léčit bez problémů, potřebuji 3 zlatá jablka a 1 prázdnou lahvičku. Samozřejmě tě odměna nemine.",
                        "Díky moc! Ušetřil jsi mi spoustu času.",
                        QuestType.PROFA,
                        new ItemStack[]{new ItemStack(Material.GOLDEN_APPLE, 3), new ItemStack(Material.GLASS_BOTTLE)},
                        player -> {
                            PaymentUtils.giveMoney(12000, "spring quest Prófa", player);
                            PaymentUtils.addScore(player, 0, "spring quest Prófa");
                        },
                        QuestType.SMUDLA,
                        "quests_spring"
                )
        );

        //Kejchal
        plugin.addNPC(
                new DialogNPC(
                        "Kejchal",
                        "Ahoj. Poslední dobou mam rýmu jako blázen. Stále kašlu a mám plný nos. Potřeboval bych po tobě, aby jsi mi donesl květinu, která roste ve smrkových lesích. Tato rostlina se jmenuje rýmovník, ale je také známa jako kapradí, proto mi prosím jednu dones. S odměnou počítej.",
                        "Děkuji ti za tvoje služby *pšík*. Tady máš svou odměnu.",
                        QuestType.KEJCHAL,
                        new ItemStack[]{new ItemStack(Material.FERN)},
                        player -> {
                            PaymentUtils.giveMoney(5000, "spring quest Kejchal", player);
                            PaymentUtils.addScore(player, 0, "spring quest Kejchal");
                        },
                        QuestType.PROFA,
                        "quests_spring"
                )
        );

        //Rejpal
        plugin.addNPC(
                new DialogNPC(
                        "Rejpal",
                        "Čau velkonosále. Došly mi všechny předměty, se kterýma bych si mohl z někoho vystřelit. Chtěl bych něco, s čím někoho hodně naštvu, proto mi dones zvon a prázdnou lahvičku. Uvidíme jestli si odměnu zasloužíš.",
                        "Tak dík. Trvalo ti to, ale moc dlouho, takže si odměnu nezasloužíš, haha.",
                        QuestType.REJPAL,
                        new ItemStack[]{new ItemStack(Material.BELL), new ItemStack(Material.GLASS_BOTTLE)},
                        player -> {},
                        QuestType.KEJCHAL,
                        "quests_spring"
                )
        );

        //Stydlín
        plugin.addNPC(
                new DialogNPC(
                        "Stydlín",
                        "A... Ahoj. Chtěl bych pro Sněhurku něco upéct, ale stydím se jít do obchodu... Pomohl bys mi, prosím? Stačí mi 2 vajíčka, 1 mléko a 2 obilí. Určitě ti to zaplatím!",
                        "Ty už jsi tady? Děkuji ti. Tady máš a drobný si nech.",
                        QuestType.STYDLIN,
                        new ItemStack[]{new ItemStack(Material.EGG, 2), new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.WHEAT, 2)},
                        player -> {
                            PaymentUtils.giveMoney(9000, "spring quest Stydlín", player);
                            PaymentUtils.addScore(player, 0, "spring quest Stydlín");
                        },
                        QuestType.REJPAL,
                        "quests_spring"
                )
        );

        //Štístko
        plugin.addNPC(
                new DialogNPC(
                        "Štístko",
                        "Zdravím. Jdeš přímo načas. Ztratil jsem svojí králičí pacičku pro štěstí, máme jí v rodině několik let a hodně pro mě znamená. Pomůžeš mi najít 1 králičí pacičku? Pokud to zvládneš, odměnu určitě dostaneš!",
                        "Díky, jsi můj zachránce! Tady máš vše, co u sebe mám.",
                        QuestType.STISTKO,
                        new ItemStack[]{new ItemStack(Material.RABBIT_FOOT)},
                        player -> {
                            PaymentUtils.giveMoney(7000, "spring quest Štístko", player);
                            PaymentUtils.addScore(player, 0, "spring quest Štístko");
                        },
                        QuestType.STYDLIN,
                        "quests_spring"
                )
        );

        //Dřímal
        plugin.addNPC(
                new DialogNPC(
                        "Dřímal",
                        "Ahoj. Mám takový problém *zív*. Dneska nemohu na šichtě usnout a potřeboval bych něco, co by mě probudilo. Dones mi prosím 1 mléko a 3 kakaové boby. Jestli to zvládneš, dám ti za to něco.",
                        "Díky moc za tyto suroviny. Jsem velice vděčný, že jsi mi je pomohl najít. Tady máš něco malého.",
                        QuestType.DRIMAL,
                        new ItemStack[]{new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.COCOA_BEANS, 3)},
                        player -> {
                            PaymentUtils.giveMoney(11000, "spring quest Dřímal", player);
                            PaymentUtils.addScore(player, 0, "spring quest Dřímal");
                        },
                        QuestType.STISTKO,
                        "quests_spring"
                )
        );

        //Sněhurka
        plugin.addNPC(
                new DialogNPC(
                        "Sněhurka",
                        "Ahoj, milý příteli. Jsem ráda, že jsi tady, docela mi vyhládlo. Nemáš náhodou obyčejné čerstvé jablko?",
                        "Gratuluji, otrávil jsi Sněhurku.",
                        QuestType.SNEHURKA,
                        new ItemStack[]{new ItemStack(Material.APPLE)},
                        player -> {
                            ItemUtils.giveKit(player, "spring_otravene_jablko");
                            PaymentUtils.addScore(player, 0, "spring quest Sněhurka");
                        },
                        QuestType.DRIMAL,
                        "quests_spring"
                )
        );
    }
}
