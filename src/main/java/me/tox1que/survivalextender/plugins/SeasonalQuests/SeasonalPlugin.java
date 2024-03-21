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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        try{
            today = dateFormat.parse(dateFormat.format(today));
        }catch(Exception e){
            e.printStackTrace();
        }
        ServerType server = Utils.getServerType();
        for(BaseSeason season:seasons.stream().filter(BaseSeason::isLoaded).toList()){
            Class<? extends BaseSeason> class_ = season.getClass();
            if(class_.isAnnotationPresent(Season.class)){
                Season annotation = class_.getAnnotation(Season.class);
                if(server == annotation.server()){
                    try{
                        Date from = dateFormat.parse(annotation.from());
                        Date until = dateFormat.parse(annotation.until());
                        if((from.before(today) && until.after(today)) || today.equals(from) || today.equals(until)){
                            Logger.Console.INFO("Loading season class "+class_.getSimpleName());
                            season.load();
                        }else{
                            Logger.Console.WARNING("Season "+class_.getSimpleName()+" is not set to be today");
                        }
                    }catch(ParseException e){
                        Logger.Console.SEVERE("Wrong date format in season annotation for class "+class_.getSimpleName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void registerSeason(BaseSeason season){
        seasons.add(season);
    }

    public void addNPC(DialogNPC npc){
        npcs.add(npc);
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
