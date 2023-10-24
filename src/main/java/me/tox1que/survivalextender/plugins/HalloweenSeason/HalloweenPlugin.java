package me.tox1que.survivalextender.plugins.HalloweenSeason;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import me.tox1que.survivalextender.plugins.HalloweenSeason.listeners.DialogListeners;
import me.tox1que.survivalextender.plugins.HalloweenSeason.listeners.EggCatcher;
import me.tox1que.survivalextender.plugins.HalloweenSeason.listeners.EntitySpawn;
import me.tox1que.survivalextender.plugins.HalloweenSeason.listeners.JoinListener;
import me.tox1que.survivalextender.plugins.HalloweenSeason.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.HalloweenSeason.utils.PlayerProfile;
import me.tox1que.survivalextender.plugins.HalloweenSeason.utils.QuestType;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HalloweenPlugin extends BasePlugin{

    private final List<String> interacted;
    private final Map<String, PlayerProfile> playerProfiles;
    private List<DialogNPC> npcs;
    private boolean itemsLoaded;

    public HalloweenPlugin(){
        super();
        this.interacted = new ArrayList<>();
        this.playerProfiles = new HashMap<>();
        this.itemsLoaded = true;
    }

    @Override
    public void load(){
        this.npcs = new ArrayList<>();
        List<DialogNPC> npcs = new ArrayList<>();

        ItemStack kopriva = getKitItem("halloweenevent_kopriva");
        kopriva.setAmount(2);
        npcs.add(new DialogNPC("Ježibaba", getDialog("jezibaba"), QuestType.JEZIBABA,
                getKitItem("halloweenevent_hnata"), kopriva, getKitItem("halloweenevent_turin")));

        ItemStack vdova = Utils.createItem(Material.CAVE_SPIDER_SPAWN_EGG, ChatColor.of("#2E2E33")+"§lČerná vdova", null);
        vdova.setAmount(4);
        ItemStack vrk = Utils.createItem(Material.POLAR_BEAR_SPAWN_EGG, ChatColor.of("#331A00")+"§lVrk", null);
        vrk.setAmount(3);
        ItemStack netopyr = Utils.createItem(Material.BAT_SPAWN_EGG, ChatColor.of("#404040")+"§lNetopýr", null);
        netopyr.setAmount(4);
        ItemStack krysa = Utils.createItem(Material.SILVERFISH_SPAWN_EGG, ChatColor.of("#808080")+"§lKrysa", null);
        krysa.setAmount(2);
        npcs.add(new DialogNPC("Překupník", Arrays.asList(getDialog("prekupnik1"), getDialog("prekupnik2")),
                QuestType.PREKUPNIK, Arrays.asList(null, getKitItem("halloweenevent_vejce")),
                Arrays.asList(new ItemStack[]{vdova, vrk}, new ItemStack[]{netopyr, krysa}), true));

        ItemStack bobule = getKitItem("halloweenevent_bobule");
        bobule.setAmount(2);
        npcs.add(new DialogNPC("Průzkumník", getDialog("pruzkumnik"), QuestType.PRUZKUMNIK,
                getKitItem("halloweenevent_fazole"), bobule));

        ItemStack dyne = getKitItem("halloweenevent_dyne");
        dyne.setAmount(2);
        npcs.add(new DialogNPC("Lupič", Arrays.asList(getDialog("lupic1"), getDialog("lupic2"), getDialog("lupic3")),
                QuestType.LUPIC, Arrays.asList(null, null, null),
                Arrays.asList(new ItemStack[]{dyne}, new ItemStack[]{getKitItem("halloweenevent_koren")},
                        new ItemStack[]{getKitItem("halloweenevent_vejce")}
                ), true));

        this.npcs = npcs;

        if(itemsLoaded){
            main.getServer().getPluginManager().registerEvents(new DialogListeners(), main);
            main.getServer().getPluginManager().registerEvents(new JoinListener(), main);
            main.getServer().getPluginManager().registerEvents(new EntitySpawn(), main);
            main.getServer().getPluginManager().registerEvents(new EggCatcher(), main);
            main.getCommand("bflm").setExecutor(new bflm());
        }
    }

    public boolean addInteracted(Player player){
        if(!interacted.contains(player.getName())){
            interacted.add(player.getName());
            return true;
        }
        return false;
    }

    public void addProfile(Player player, PlayerProfile profile){
        playerProfiles.put(player.getName(), profile);
    }

    public String getDialog(String name){
        List<String> result = new ArrayList<>();
        return main.getConfig().getString("halloweenDialogs."+name);
    }

    public ItemStack getKitItem(String name){
        Kit kit = CMI.getInstance().getKitsManager().getKit(name, true);
        if(kit == null){
            main.getLogger().severe("Kit "+name+" is null! !");
            itemsLoaded = false;
            return null;
        }
        return kit.getFirstNotNullItem();
    }

    public DialogNPC getNPC(String name){
        for(DialogNPC npc:npcs){
            if(name.toLowerCase().contains(npc.getName().toLowerCase()))
                return npc;
        }
        return null;
    }

    public PlayerProfile getProfile(Player player){
        return playerProfiles.get(player.getName());
    }

    public void removeInteracted(Player player){
        interacted.remove(player.getName());
    }
}
