package me.tox1que.survivalextender.plugins.SeasonalQuests;

import me.tox1que.survivalextender.plugins.SeasonalQuests.listeners.SpringListener;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.PlayerProfile;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.QuestType;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeasonalPlugin extends BasePlugin{

    private final List<Player> interacted;
    private final Map<String, PlayerProfile> playerProfiles;
    private final List<DialogNPC> npcs;

    public SeasonalPlugin(){
        this.interacted = new ArrayList<>();
        this.playerProfiles = new HashMap<>();
        this.npcs = new ArrayList<>();
    }

    @Override
    public void load(){
        main.getServer().getPluginManager().registerEvents(new SpringListener(), main);

        //Šmudla
        npcs.add(
                new DialogNPC(
                        "Šmudla",
                        "Ahoj a zdar, dobrodruhu! Měl bych pro tebe menší nabídku. Ty bys za mě dneska odpracoval šichtu a já ti naoplátku dám nějaké drobné. Každý den musíme vytěžit 16 diamantů, 20 železa a 32 uhlí.",
                        "Díky moc, díky tobě si mohu dneska zdřímnout.",
                        QuestType.SMUDLA,
                        new ItemStack[]{new ItemStack(Material.DIAMOND, 16), new ItemStack(Material.IRON_INGOT, 20), new ItemStack(Material.COAL, 32)},
                        player -> PaymentUtils.giveMoney(18000, "spring quest Šmudla", player)
                )
        );

        //Prófa
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.WEAKNESS));
        potion.setItemMeta(meta);
        npcs.add(
                new DialogNPC(
                        "Prófa",
                        "Dobrý den. Potřeboval bych doplnit zásoby svých léků. Abych mohl dalších pár let léčit bez problémů, potřebuji 3 zlatá jablka a 1 házecí lektvar oslabení. Samozřejmě tě odměna nemine.",
                        "Díky moc! Ušetřil jsi mi spoustu času.",
                        QuestType.PROFA,
                        new ItemStack[]{new ItemStack(Material.GOLDEN_APPLE, 3), potion},
                        player -> PaymentUtils.giveMoney(12000, "spring quest Prófa", player),
                        QuestType.SMUDLA
                )
        );

        //Kejchal
        npcs.add(
                new DialogNPC(
                        "Kejchal",
                        "Ahoj. Poslední dobou mam rýmu jako blázen. Stále kašlu a mám plný nos. Potřeboval bych po tobě, aby jsi mi donesl květinu, která roste ve smrkových lesích. Tato rostlina se jmenuje rýmovník, ale je také známa jako kapradí, proto mi prosím jednu dones. S odměnou počítej.",
                        "Děkuji ti za tvoje služby *pšík*. Tady máš svou odměnu.",
                        QuestType.KEJCHAL,
                        new ItemStack[]{new ItemStack(Material.FERN)},
                        player -> PaymentUtils.giveMoney(5000, "spring quest Kejchal", player),
                        QuestType.PROFA
                )
        );

        //Rejpal
        potion = new ItemStack(Material.POTION);
        meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        potion.setItemMeta(meta);
        npcs.add(
                new DialogNPC(
                        "Rejpal",
                        "Čau velkonosále. Došly mi všechny předměty, se kterýma bych si mohl z někoho vystřelit. Chtěl bych něco, s čím někoho hodně naštvu, proto mi dones zvon a lektvar neviditelnosti na 8 minut. Uvidíme jestli si odměnu zasloužíš.",
                        "Tak dík. Trvalo ti to, ale moc dlouho, takže si odměnu nezasloužíš, haha.",
                        QuestType.REJPAL,
                        new ItemStack[]{new ItemStack(Material.BELL), potion},
                        player -> {
                        },
                        QuestType.KEJCHAL
                )
        );

        //Stydlín
        npcs.add(
                new DialogNPC(
                        "Stydlín",
                        "A... Ahoj. Chtěl bych pro Sněhurku něco upéct, ale stydím se jít do obchodu... Pomohl bys mi, prosím? Stačí mi 2 vajíčka, 1 mléko a 2 obilí. Určitě ti to zaplatím!",
                        "Ty už jsi tady? Děkuji ti. Tady máš a drobný si nech.",
                        QuestType.STYDLIN,
                        new ItemStack[]{new ItemStack(Material.EGG, 2), new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.WHEAT, 2)},
                        player -> PaymentUtils.giveMoney(9000, "spring quest Stydlín", player),
                        QuestType.REJPAL
                )
        );

        //Štístko
        npcs.add(
                new DialogNPC(
                        "Štístko",
                        "Zdravím. Jdeš přímo načas. Ztratil jsem svojí králičí pacičku pro štěstí, máme jí v rodině několik let a hodně pro mě znamená. Pomůžeš mi najít 1 králičí pacičku? Pokud to zvládneš, odměnu určitě dostaneš!",
                        "Díky, jsi můj zachránce! Tady máš vše, co u sebe mám.",
                        QuestType.STISTKO,
                        new ItemStack[]{new ItemStack(Material.RABBIT_FOOT)},
                        player -> PaymentUtils.giveMoney(7000, "spring quest Štístko", player),
                        QuestType.STYDLIN
                )
        );

        //Dřímal
        npcs.add(
                new DialogNPC(
                        "Dřímal",
                        "Ahoj. Mám takový problém *zív*. Dneska nemohu na šichtě usnout a potřeboval bych něco, co by mě probudilo. Dones mi prosím 1 mléko a 3 kakaové boby. Jestli to zvládneš, dám ti za to něco.",
                        "Díky moc za tyto suroviny. Jsem velice vděčný, že jsi mi je pomohl najít. Tady máš něco malého.",
                        QuestType.DRIMAL,
                        new ItemStack[]{new ItemStack(Material.MILK_BUCKET), new ItemStack(Material.COCOA_BEANS, 3)},
                        player -> PaymentUtils.giveMoney(11000, "spring quest Dřímal", player),
                        QuestType.STISTKO
                )
        );

        //Sněhurka
        npcs.add(
                new DialogNPC(
                        "Sněhurka",
                        "Ahoj, milý příteli. Jsem ráda, že jsi tady, docela mi vyhládlo. Nemáš náhodou obyčejné čerstvé jablko?",
                        "Gratuluji, otrávil jsi Sněhurku.",
                        QuestType.SNEHURKA,
                        new ItemStack[]{new ItemStack(Material.APPLE)},
                        player -> ItemUtils.giveKit(player, "spring_otravene_jablko"),
                        QuestType.DRIMAL
                )
        );
    }

    public boolean addInteracted(Player player){
        if(interacted.contains(player))
            return false;
        interacted.add(player);
        return true;
    }

    public void addProfile(Player player, PlayerProfile profile){
        playerProfiles.put(player.getName(), profile);
    }

    public PlayerProfile getProfile(Player player){
        return playerProfiles.get(player.getName());
    }

    public DialogNPC getNPC(String name){
        for(DialogNPC npc : npcs){
            if(name.toLowerCase().contains(npc.getName().toLowerCase()))
                return npc;
        }
        return null;
    }

    public void removeInteracted(Player player){
        interacted.remove(player);
    }
}
