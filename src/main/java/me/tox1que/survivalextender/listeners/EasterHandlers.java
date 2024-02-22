package me.tox1que.survivalextender.listeners;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import com.Zrips.CMI.Modules.Kits.KitsManager;
import me.tox1que.survivalextender.utils.Utils;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class EasterHandlers implements Listener{

    private final KitsManager kitsManager = CMI.getInstance().getKitsManager();

    private final String infiniteBucketDisplayName = ChatColor.of("#89A6FA") + "§lKbelík s vodou";
    private final String flowersDisplayName = ChatColor.of("#E6392B") + "§lKvětiny";
    private final String twigDisplayName = ChatColor.of("#674D1E") + "§lPomlázka";
    private final List<Material> voteFlowers = Arrays.asList(Material.AZURE_BLUET, Material.ORANGE_TULIP, Material.WITHER_ROSE, Material.ALLIUM,
            Material.PINK_TULIP, Material.RED_TULIP, Material.OXEYE_DAISY, Material.BLUE_ORCHID, Material.POPPY, Material.WHITE_TULIP,
            Material.LILY_OF_THE_VALLEY, Material.CORNFLOWER);

    @EventHandler
    public void infiniteBucket(PlayerBucketEmptyEvent e){
        if(e.isCancelled())
            return;
        if(Utils.getServerType() != ServerType.SURVIVAL)
            return;

        Player player = e.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();
        if(inHand.getItemMeta() == null)
            return;

        String displayName = inHand.getItemMeta().getDisplayName();
        if(!displayName.equals(infiniteBucketDisplayName))
            return;
        e.setCancelled(true);
        e.getBlock().getLocation().clone().getBlock().setType(Material.WATER);
    }

    @EventHandler
    public void flowers(PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player player = e.getPlayer();
        ItemStack inHand = player.getInventory().getItemInMainHand();
        if(inHand.getItemMeta() == null)
            return;
        if(!inHand.getItemMeta().getDisplayName().equals(flowersDisplayName))
            return;

        e.setCancelled(true);
        ItemMeta inHandMeta = inHand.getItemMeta();
        List<String> lore = inHandMeta.getLore();
        String usageLine = lore.get(1);

        int usages = Integer.parseInt(usageLine.substring(usageLine.indexOf('(') + 1, usageLine.indexOf('/')));
        if(usages >= 10){
            player.sendMessage("§cTento item už použít nejde.");
            return;
        }
        Material canCompleteWith = null;
        for(Material m : voteFlowers){
            if(player.getInventory().containsAtLeast(new ItemStack(m), 16)){
                canCompleteWith = m;
                break;
            }
        }

        if(canCompleteWith != null){
            player.getInventory().removeItem(new ItemStack(canCompleteWith, 16));
            usages++;
            lore.set(1, String.format(ChatColor.of("#FF847A") + "(%d/10)", usages));
            inHandMeta.setLore(lore);
            inHand.setItemMeta(inHandMeta);

            Kit kit = kitsManager.getKit("kytka_" + (voteFlowers.indexOf(canCompleteWith) + 1), true);
            kitsManager.giveKit(player, kit);
            player.sendMessage(ChatColor.of("#E6392B") + "Květiny §7» " + ChatColor.of("#FF847A") + "Obdržel jsi kit" + kit.getDisplayName());
        }
    }
}
