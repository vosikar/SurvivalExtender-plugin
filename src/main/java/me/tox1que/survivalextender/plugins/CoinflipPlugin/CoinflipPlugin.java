package me.tox1que.survivalextender.plugins.CoinflipPlugin;

import me.tox1que.survivalextender.plugins.CoinflipPlugin.commands.CoinflipCommand;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.listeners.CoinflipListeners;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipGame;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipStats;
import me.tox1que.survivalextender.utils.Builder.ItemBuilder;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinflipPlugin extends BasePlugin{

    private final List<CoinflipGame> games;
    private final Map<String, CoinflipStats> stats;
    private int gameId;
    private ItemStack fillItem1;
    private ItemStack fillItem2;

    public CoinflipPlugin(){
        super("Coinflip", "§e", "§6");
        this.games = new ArrayList<>();
        this.stats = new HashMap<>();
    }

    @Override
    public void load(){
        main.getCommand("coinflip").setExecutor(new CoinflipCommand(this));
        main.getServer().getPluginManager().registerEvents(new CoinflipListeners(), main);

        gameId = 0;
        fillItem1 = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build();
        fillItem2 = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build();
    }

    @Override
    public void unload(){
        for(CoinflipGame game:games){
            game.refund();
        }
        Logger.Console.INFO("Paid back for Coinflips in /coinflip");
    }

    public void createCoinflip(Player player, int amount){
        CoinflipGame game = new CoinflipGame(player, amount, gameId);
        gameId++;
        games.add(game);
        sendMessage(player, String.format("Založil jsi Coinflip za [sc]$%s[pc].", Utils.formatNumber(amount)));
    }

    public void openOverview(Player player){
        Inventory inv = Bukkit.createInventory(null, 5*9, "§6Coinflip - přehled");
        Utils.borderInventory(inv, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build());

        //Create stats item
        CoinflipStats playerStats = getStats(player);
        int wins = playerStats.getWins();
        int losses = playerStats.getLosses();
        inv.setItem(40,
                new ItemBuilder(Material.BOOK)
                        .setDisplayName(secondaryColor+"Statistiky")
                        .setLores(
                                "",
                                String.format("%sVýhry: %s%s (%s)", primaryColor, secondaryColor, wins, Utils.getPercentage(wins, losses)+" %"),
                                String.format("%sProhry: %s%s (%s)", primaryColor, secondaryColor, losses, Utils.getPercentage(losses, wins)+" %"),
                                primaryColor+"Celkem profit: "+secondaryColor+"$"+Utils.formatNumber(playerStats.getProfit())
                                )
                        .build()
        );

        //Create item for every game
        for(CoinflipGame game:games){
            ItemBuilder item = new ItemBuilder(Material.CLOCK)
                    .setDisplayName(getSecondaryColor()+game.getCreator().getName())
                    .setLores(
                            "§8§o"+game.getGameId(),
                            "",
                            primaryColor+"Vytvořil: "+secondaryColor+game.getCreator().getName(),
                            primaryColor+"Vklad: "+secondaryColor+"$"+Utils.formatNumber(game.getMoney()),
                            primaryColor+"Celková výhra: "+secondaryColor+"$"+Utils.formatNumber(game.getPrize())
                    );
            if(game.getCreator().getName().equals(player.getName())){
                item.addLore("", primaryColor+"Klikni pravým pro zrušení");
            }
            if(game.getWinner() != null){
                item.addLore(primaryColor+"Výherce: "+secondaryColor+game.getWinner().getName());
            }
            inv.addItem(item.build());
        }

        player.openInventory(inv);
    }

    public void removeCoinflip(int gameId){
        games.remove(getGameById(gameId));
    }

    public void setStats(Player player, CoinflipStats coinflipStats){
        stats.put(player.getName(), coinflipStats);
    }

    /*
        Getters
     */

    public CoinflipGame getGameById(int id){
        for(CoinflipGame game:games){
            if(game.getGameId() == id)
                return game;
        }
        return null;
    }

    public List<CoinflipGame> getGames(){
        return games;
    }

    public ItemStack getFillItem1(){
        return fillItem1;
    }

    public ItemStack getFillItem2(){
        return fillItem2;
    }

    public CoinflipStats getStats(OfflinePlayer player){
        return stats.getOrDefault(player.getName(), new CoinflipStats(player));
    }
}
