package me.tox1que.survivalextender.plugins.CoinflipPlugin.listeners;

import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipGame;
import me.tox1que.survivalextender.plugins.CoinflipPlugin.utils.CoinflipSQL;
import me.tox1que.survivalextender.utils.Logger;
import me.tox1que.survivalextender.utils.PaymentUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class CoinflipListeners implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        CoinflipSQL.loadPlayer(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        if(!title.startsWith("§6Coinflip -"))
            return;

        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);

        if(title.equals("§6Coinflip - přehled")){
            ItemStack itemStack = e.getCurrentItem();
            if(itemStack == null || itemStack.getItemMeta() == null)
                return;

            if(itemStack.getType() == Material.PLAYER_HEAD){
                //Previous page
                int page = Integer.parseInt(ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1)).replace("Přechod na stránku ", ""));
                if(e.getRawSlot() == 36)
                    page--;
                player.performCommand("coinflip "+page);
                return;
            }

            if(itemStack.getType() != Material.CLOCK)
                return;

            String id = itemStack.getItemMeta().getLore().get(0);
            CoinflipGame game = SurvivalExtender.getInstance().getCoinflipPlugin().getGameById(Integer.parseInt(ChatColor.stripColor(id)));
            if(game == null)
                return;
            if(e.getClick() == ClickType.RIGHT && game.getCreator().getName().equals(player.getName())){
                game.refund(true);
                return;
            }
            if(player.getName().equals(game.getCreator().getName()) || game.getChallenger() != null){
                SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage(player, "Do této hry se nemůžeš připojit.");
                return;
            }
            if(PaymentUtils.hasMoney(player, game.getMoney())){
                game.rollWinner(player);
            }else{
                SurvivalExtender.getInstance().getCoinflipPlugin().sendMessage(player, "Nemáš dostatek financí.");
            }
        }
    }
}
