package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.ob;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.SeasonalQuests.BaseSeason;
import me.tox1que.survivalextender.plugins.SeasonalQuests.Season;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.DialogNPC;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.PlayerProfile;
import me.tox1que.survivalextender.plugins.SeasonalQuests.utils.QuestType;
import me.tox1que.survivalextender.utils.ItemUtils;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.SQL.SQLUtils;
import me.tox1que.survivalextender.utils.enums.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Season(from = "2024-03-28", until = "2024-04-05", server = ServerType.ONEBLOCK)
public class EasterOneBlock extends BaseSeason{

    private final Map<Location, Integer> eggsLocations;
    private final Map<String, List<Integer>> playerEggs;

    public EasterOneBlock(){
        World spawn = Bukkit.getWorld("SpawnWorld");
        eggsLocations = new HashMap<>();
        eggsLocations.put(new Location(spawn, 24, 104, -25), 1);
        eggsLocations.put(new Location(spawn, 47, 103, 46), 2);
        eggsLocations.put(new Location(spawn, -4, 106, 34), 3);
        eggsLocations.put(new Location(spawn, 51, 103, -16), 4);
        eggsLocations.put(new Location(spawn, 86, 106, -19), 5);
        playerEggs = new HashMap<>();
    }

    public void addPlayerEgg(String player, Location location){
        if(!playerEggs.containsKey(player))
            playerEggs.put(player, new ArrayList<>());
        int index = eggsLocations.get(location);
        playerEggs.get(player).add(index);
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;

            try{
                con = SQLUtils.getNewConnection();
                ps = con.prepareStatement("INSERT INTO quests_easter_eggs (`player`, `index`) VALUES (?, ?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, player);
                ps.setInt(2, index);
                ps.execute();
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                try{
                    ps.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setPlayerEggs(String player, List<Integer> eggs){
        this.playerEggs.put(player, eggs);
    }

    public int getLocationIndex(Location location){
        return eggsLocations.get(location);
    }

    public boolean hasCompleted(String player, int index){
        return playerEggs.get(player).contains(index);
    }

    public int getCompletedAmount(String player){
        return playerEggs.get(player).size();
    }

    public boolean isEgg(Location location){
        return eggsLocations.containsKey(location);
    }

    @Override
    public void load(){
        SurvivalExtender.getInstance().getServer().getPluginManager().registerEvents(new EasterOneBlockListeners(this), SurvivalExtender.getInstance());

        //Tonda
        plugin.addNPC(
                new DialogNPC(
                    "Tonda",
                    "Ahoj, vidím že jsi přišel bez pomlázky. Ale to nevadí jednu ti upletu, ale potřebuji za to 5x stick a 2x string.",
                    "Super, díky, tady máš jednu krásně upletenou. A můžeš zajít za Ančou, myslím si, že pro tebe něco má.",
                    QuestType.TONDA,
                    new ItemStack[]{new ItemStack(Material.STICK, 5), new ItemStack(Material.STRING, 2)},
                    player -> ItemUtils.giveKit(player, "easter2024_pomlazka"),
                    null,
                    "quests_easter"
                )
        );

        //Anča
        plugin.addNPC(
                new DialogNPC(
                    "Anča",
                    "Jéé, další koledník, zdravím tě, něco pro tebe mám, ale potřebuji na oplátku 2x egg a 1x blue dye. Abych mohla barvit další vajíčka.",
                    "Díky moc, doufám že máš rád čokoládu, za odměnu ti dám čokoládové vajíčko.",
                    QuestType.ANCA,
                    new ItemStack[]{new ItemStack(Material.EGG, 2), new ItemStack(Material.BLUE_DYE)},
                    player -> ItemUtils.giveKit(player, "easter2024_cokoladove_vajicko"),
                    QuestType.TONDA,
                    "quests_easter"
                )
        );
    }

    public void loadPlayerProfile(Player player){
        Bukkit.getScheduler().runTaskAsynchronously(SurvivalExtender.getInstance(), () -> {
            PreparedStatement ps = null;
            Connection con = null;
            ResultSet result = null;

            try{
                con = SQLUtils.getNewConnection();
                ps = con.prepareStatement("SELECT * FROM quests_easter_eggs WHERE player=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, player.getName());
                ps.execute();
                result = ps.getResultSet();

                List<Integer> completed = new ArrayList<>();
                while(result.next()){
                    completed.add(result.getInt("index"));
                }
                setPlayerEggs(player.getName(), completed);
                ps.close();
            }catch(SQLException ex){
                ex.printStackTrace();
            }finally{
                try{
                    ps.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
