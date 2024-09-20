package me.tox1que.survivalextender.plugins.CoinflipPlugin.utils;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Date;

public class CoinflipGame{

    private final OfflinePlayer creator;
    private final int money;
    private final int gameId;
    private final double prize;
    private final Date dateCreated;
    private OfflinePlayer challenger;
    private OfflinePlayer winner;
    private OfflinePlayer loser;
    private int taskId;

    public CoinflipGame(OfflinePlayer creator, int money, int gameId){
        this.creator = creator;
        this.money = money;
        this.gameId = gameId;
        this.prize = Double.parseDouble(String.format("%.2f", money * 0.9 * 2));
        this.dateCreated = new Date();
        this.challenger = null;
        this.winner = null;
        PaymentUtils.takeMoney(money, "coinflip create", creator);
    }

    public void rollWinner(OfflinePlayer challenger){
        this.challenger = challenger;
        PaymentUtils.takeMoney(money, "coinflip join", challenger);

        int result = Utils.randomNumber(2);
        if(result == 0){
            winner = creator;
            loser = challenger;
        }else{
            loser = creator;
            winner = challenger;
        }

        Inventory inventory = Bukkit.createInventory(null, 3 * 9, getInventoryTitle());
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvivalExtender.getInstance(), new Runnable(){
            int flips = Utils.randomNumber(12) + 6;
            boolean showCreator = false;

            @Override
            public void run(){
                try{
                    if(flips <= 0){
                        if((creator == winner && showCreator) || (challenger == winner && !showCreator)){
                            swapView(inventory, showCreator);
                        }
                        Bukkit.getScheduler().runTaskLater(SurvivalExtender.getInstance(), () -> updateInventories(flips, inventory), 60L);

                        String message = String.format("[sc]%s [pc]vyhrál Coinflip o [sc]$%s [pc]proti [sc]%s.", winner.getName(), Utils.formatNumber(prize), loser.getName());
                        Logger.Console.INFO(SurvivalExtender.getInstance().getCoinflipPlugin().getFinalMessage(message));
                        Logger.Database.Coinflip.write(true, winner.getName(), loser.getName(), money, prize);
                        for(Player player : Bukkit.getOnlinePlayers()){
                            if(SurvivalExtender.getInstance().getCoinflipPlugin().getStats(player).hasAnnouncements()){
                                SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage(message, player);
                            }
                        }

                        PaymentUtils.giveMoney(prize, "coinflip win", winner);
                        SurvivalExtender.getInstance().getCoinflipPlugin().getStats(winner).addWin(money);
                        SurvivalExtender.getInstance().getCoinflipPlugin().getStats(loser).addLose(money);
                        Bukkit.getScheduler().cancelTask(taskId);
                        SurvivalExtender.getInstance().getCoinflipPlugin().removeCoinflip(gameId);
                        if(PlayerUtils.isOnline(winner)){
                            winner.getPlayer().playSound(winner.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        }
                        return;
                    }
                    swapView(inventory, showCreator);
                    updateInventories(flips, inventory);
                    flips--;
                    showCreator = !showCreator;
                }catch(Exception e){
                    Bukkit.getScheduler().cancelTask(taskId);
                    PaymentUtils.giveMoney(money, "coinflip refund - error", creator);
                    PaymentUtils.giveMoney(money, "coinflip refund - error", challenger);
                    SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage("Nepodařilo se spustit coinflip, byly ti navráceny peníze.", creator.getPlayer(), challenger.getPlayer());
                    SurvivalExtender.getInstance().getCoinflipPlugin().removeCoinflip(gameId);
                    e.printStackTrace();
                }
            }
        }, 0L, 10L);
    }

    public void swapView(Inventory inventory, boolean showCreator){
        ItemUtils.fillInventory(inventory, showCreator ? SurvivalExtender.getInstance().getCoinflipPlugin().getFillItem1() : SurvivalExtender.getInstance().getCoinflipPlugin().getFillItem2());
        inventory.setItem(13, PlayerUtils.getPlayerHead(showCreator ? creator : challenger));
    }

    public void updateInventories(int i, Inventory inventory){
        updateInventory(i, inventory, creator);
        updateInventory(i, inventory, challenger);
    }

    private void updateInventory(int i, Inventory inventory, OfflinePlayer offlinePlayer){
        if(PlayerUtils.isOnline(offlinePlayer) && offlinePlayer.getPlayer() != null){
            Player player = offlinePlayer.getPlayer();
            if(i <= 0){
                if(player.getOpenInventory().getTitle().equals(getInventoryTitle())){
                    player.closeInventory();
                }
            }else{
                if(player.getOpenInventory().getType() == InventoryType.CRAFTING || player.getOpenInventory().getTitle().contains("Coinflip - ")){
                    player.openInventory(inventory);
                }
                player.playSound(offlinePlayer.getPlayer().getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_HIT, 1f, 1f);
            }
        }
    }

    public void refund(boolean remove){
        if(winner != null)
            return;

        PaymentUtils.giveMoney(money, "coinflip refund", creator);
        if(remove)
            SurvivalExtender.getInstance().getCoinflipPlugin().removeCoinflip(gameId);

        if(challenger != null && challenger.isOnline() && challenger.getPlayer() != null){
            if(challenger.isOnline() && challenger.getPlayer() != null){
                SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage(String.format("Tvá hra byla zrušena a bylo ti navráceno $%s.", Utils.formatNumber(money)), challenger.getPlayer());
                challenger.getPlayer().closeInventory();
            }
        }
        if(creator.isOnline() && creator.getPlayer() != null){
            SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage(String.format("Tvá hra byla zrušena a bylo ti navráceno $%s.", Utils.formatNumber(money)), creator.getPlayer());
            creator.getPlayer().closeInventory();
        }
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public OfflinePlayer getCreator(){
        return creator;
    }

    public OfflinePlayer getChallenger(){
        return challenger;
    }

    public void setChallenger(Player challenger){
        this.challenger = challenger;
    }

    public int getGameId(){
        return gameId;
    }

    public int getMoney(){
        return money;
    }

    public double getPrize(){
        return prize;
    }

    public String getInventoryTitle(){
        return String.format("§6Coinflip - %s vs %s", creator.getName(), challenger != null ? challenger.getName() : "?");
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public OfflinePlayer getWinner(){
        return winner;
    }
}
