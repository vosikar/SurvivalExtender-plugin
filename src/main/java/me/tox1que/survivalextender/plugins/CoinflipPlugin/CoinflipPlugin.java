package me.tox1que.survivalextender.plugins.CoinflipPlugin;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.commands.CoinflipCommand;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.listeners.CoinflipListeners;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipGame;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipStats;
import me.tox1que.survivalextender.utils.Builder.ItemBuilder;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.abstracts.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinflipPlugin extends BasePlugin implements Listener{

    private final List<CoinflipGame> games;
    private final Map<String, CoinflipStats> stats;
    private final int perPage;
    private int gameId;
    private ItemStack fillItem1;
    private ItemStack fillItem2;
    private ItemBuilder rightArrow;
    private ItemBuilder leftArrow;

    public CoinflipPlugin(){
        super("Coinflip", "§e", "§6");
        games = new ArrayList<>();
        stats = new HashMap<>();
        perPage = 21;
    }

    @Override
    public void load(){
        main.getCommand("coinflip").setExecutor(new CoinflipCommand(this));
        main.getServer().getPluginManager().registerEvents(new CoinflipListeners(), main);

        gameId = 0;
        fillItem1 = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build();
        fillItem2 = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onHdbLoad(DatabaseLoadEvent e){
        rightArrow = new ItemBuilder(Material.PLAYER_HEAD).setHDB("7929")
                .setDisplayName("§6Další stránka");
        leftArrow = new ItemBuilder(Material.PLAYER_HEAD).setHDB("7930")
                .setDisplayName("§6Předchozí stránka");
    }


    @Override
    public void unload(){
        for(CoinflipGame game : games){
            game.refund(false);
        }
        Logger.Console.INFO("Paid back for Coinflips in /coinflip");
    }

    public void createCoinflip(Player player, int amount){
        CoinflipGame game = new CoinflipGame(player, amount, gameId);
        gameId++;
        games.add(game);
        sendMessage(player, String.format("Založil jsi Coinflip za [sc]$%s[pc].", Utils.formatNumber(amount)));
    }

    public void openOverview(Player player, int page){
        Inventory inv = Bukkit.createInventory(null, 5 * 9, "§6Coinflip - přehled");
        Utils.borderInventory(inv, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(" ").fakeEnchant().build());

        //Create stats item
        CoinflipStats playerStats = getStats(player);
        int wins = playerStats.getWins();
        int losses = playerStats.getLosses();
        inv.setItem(40,
                new ItemBuilder(Material.BOOK)
                        .setDisplayName(secondaryColor + "Statistiky")
                        .setLores(
                                "",
                                String.format("%sVýhry: %s%s (%s)", primaryColor, secondaryColor, wins, Utils.getPercentage(wins, losses) + " %"),
                                String.format("%sProhry: %s%s (%s)", primaryColor, secondaryColor, losses, Utils.getPercentage(losses, wins) + " %"),
                                primaryColor + "Celkem profit: " + secondaryColor + "$" + Utils.formatNumber(playerStats.getProfit())
                        )
                        .build()
        );

        int maxPage = games.size() / (perPage + 1);
        if(page > maxPage){
            page = maxPage;
        }

        if(page > 0){
            inv.setItem(36, leftArrow.setLores("", "§ePřechod na stránku " + (page)).build());
        }
        if(page < maxPage){
            inv.setItem(44, rightArrow.setLores("", "§ePřechod na stránku " + (page + 2)).build());
        }

        //Create item for every game
        for(int i = page * perPage; i < (page + 1) * perPage && i < games.size(); i++){
            CoinflipGame game = games.get(i);
            ItemBuilder item = new ItemBuilder(Material.CLOCK)
                    .setDisplayName(getSecondaryColor() + game.getCreator().getName())
                    .setLores(
                            "§8§o" + game.getGameId(),
                            "",
                            primaryColor + "Vytvořil: " + secondaryColor + game.getCreator().getName(),
                            primaryColor + "Vklad: " + secondaryColor + "$" + Utils.formatNumber(game.getMoney()),
                            primaryColor + "Celková výhra: " + secondaryColor + "$" + Utils.formatNumber(game.getPrize())
                    );
            if(game.getCreator().getName().equals(player.getName())){
                item.addLore("", primaryColor + "Klikni pravým pro zrušení");
            }
            if(game.getWinner() != null){
                item.addLore(primaryColor + "Výherce: " + secondaryColor + game.getWinner().getName());
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
        for(CoinflipGame game : games){
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
