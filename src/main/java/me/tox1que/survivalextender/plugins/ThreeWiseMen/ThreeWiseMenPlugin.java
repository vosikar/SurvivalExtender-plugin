package me.tox1que.survivalextender.plugins.ThreeWiseMen;

import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.QuestType;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.listeners.TWMListeners;
import me.tox1que.survivalextender.plugins.ThreeWiseMen.utils.PlayerProfile;
import me.tox1que.survivalextender.utils.DialogNPC;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeWiseMenPlugin extends BasePlugin{

    private final List<Player> interacted;
    private final Map<String, PlayerProfile> playerProfiles;
    private final List<DialogNPC> npcs;

    public ThreeWiseMenPlugin(){
        this.interacted = new ArrayList<>();
        this.playerProfiles = new HashMap<>();
        this.npcs = new ArrayList<>();
    }

    @Override
    public void load(){
        main.getServer().getPluginManager().registerEvents(new TWMListeners(), main);

        //Pletarka
        ItemStack decka = Utils.getKitItem("trikralove_jeziskova_decka");
        npcs.add(
                new DialogNPC(
                        "Pletařka",
                        "Ahoj slyšela jsem, že bys potřeboval uplést dečku pro Ježíška. Já ti ji upletu, ale potřebuji na to 5x String, 3x Brown Carpet a 2x Gold Nugget. Musím zaručit nejvyšší kvalitu.",
                        "Znovu tě zdravím, zde máš tu dečku, hlavně na ni dávej pozor, byla upletena s láskou.",
                        QuestType.PLETARKA,
                        decka,
                        new ItemStack(Material.STRING, 5), new ItemStack(Material.BROWN_CARPET, 3), new ItemStack(Material.GOLD_NUGGET, 2)
                )
        );

        //Melichar
        npcs.add(
                new DialogNPC(
                        "Melichar",
                        "My tři králo..., Jéé, ahoj! Na cestě za Ježíškem jsme ztratili naše dary. Pomůžeš nám je najít? Odměna tě nemine. Jako první bych potřeboval, abys mi našel a přinesl Ježíškovu dečku. Pokud ji nemáš, jdi se zeptat Pletařky. Děkuji ti a ať se ti daří.",
                        "Ahoj, děkuji ti moc za pomoc. Heleď, dojdi ještě za Kašparem, určitě bude něco potřebovat.",
                        QuestType.MELICHAR,
                        null,
                        decka
                )
        );

        //Kaspar
        ItemStack mesec = Utils.getKitItem("trikralove_mesec");
        npcs.add(
                new DialogNPC(
                        "Kašpar",
                        "Ahoj, vidím, že jsi přinesl dečku Melicharovi. Já bych potřeboval přinést svůj ztracený měšec zlaťáků. Prosím, zajdi za Skřítkem. On ti měšec sežene. Díky.",
                        "Ahoj, ahoj, díky ti za pomoc! Ale ještě od tebe něco potřebuje Baltazar, tak za ním zaskoč!",
                        QuestType.KASPAR,
                        null,
                        mesec
                )
        );

        //Baltazar
        ItemStack korunka = Utils.getKitItem("trikralove_korunka");
        ItemStack kadidlo = Utils.getKitItem("trikralove_kadidlo");
        npcs.add(
                new DialogNPC(
                        "Baltazar",
                        "Díky, že jsi pomohl Melicharovi a Kašparovi. Proto doufám, že pomůžeš i mně. Já potřebuji sehnat zlaté kadidlo, které musíš vyrobit podle receptu na spawnu. Potom mi ho hned přines a já ti za odměnu dám moji korunku.",
                        "Díky, že jsi nám našel naše dary. Za odměnu ti dám moji korunku. Užij ji dobře, je dost mocná.",
                        QuestType.BALTAZAR,
                        korunka,
                        kadidlo
                )
        );

        //Skritek
        npcs.add(
                new DialogNPC(
                        "Skřítek",
                        "Ahoj človíčku, nechtěně jsem zlomil své koště a potřeboval bych ho opravit, když mi doneseš 2x stick, 1x string a 1x gold ingot, tak mi dost pomůžeš. Neboj odměna čeká jen na tebe!",
                        "Ahhh, děkuji ti člověče! jako odměnu máš tady měšec plný zlaťáku.",
                        QuestType.SKRITEK,
                        mesec,
                        new ItemStack(Material.STICK, 2), new ItemStack(Material.STRING), new ItemStack(Material.GOLD_INGOT)
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
        for(DialogNPC npc:npcs){
            if(name.toLowerCase().contains(npc.getName().toLowerCase()))
                return npc;
        }
        return null;
    }

    public void removeInteracted(Player player){
        interacted.remove(player);
    }
}
