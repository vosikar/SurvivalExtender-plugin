package me.tox1que.survivalextender.managers.QoL;

import me.tox1que.survivalextender.SurvivalExtender;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class QoLManager{

    private final BossBar mineWorldBossBar;

    public QoLManager(){
        Bukkit.getServer().getPluginManager().registerEvents(new QoLListeners(), SurvivalExtender.getInstance());

        this.mineWorldBossBar = Bukkit.createBossBar(
            "§6§lPrávě se nacházíš v MineWorldu, který se každý pátek restartuje",
            BarColor.RED,
            BarStyle.SOLID
        );

        //MineWorld checker
        Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalExtender.getInstance(), () -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.getWorld().getName().equals("MineWorld")){
                    if(!mineWorldBossBar.getPlayers().contains(player)){
                        addMineWorldBossBar(player);
                    }
                }else if(mineWorldBossBar.getPlayers().contains(player)){
                    removeMineWorldBossBar(player);
                }
            }
        }, 100L, 20L);
    }

    public void addMineWorldBossBar(Player player){
        mineWorldBossBar.addPlayer(player);
    }

    public void removeMineWorldBossBar(Player player){
        mineWorldBossBar.removePlayer(player);
    }
}
