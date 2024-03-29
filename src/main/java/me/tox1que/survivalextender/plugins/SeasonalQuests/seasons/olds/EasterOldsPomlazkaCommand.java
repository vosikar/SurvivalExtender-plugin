package me.tox1que.survivalextender.plugins.SeasonalQuests.seasons.olds;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import me.tox1que.survivalextender.utils.PaymentUtils;
import me.tox1que.survivalextender.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EasterOldsPomlazkaCommand implements CommandExecutor{

    private final List<ItemStack> items;
    private final ItemStack whip;

    public EasterOldsPomlazkaCommand(){
        items = new ArrayList<>();
        for(String s:new String[]{"zluta", "oranzova", "cervena", "zelena", "modra"}){
            Kit kit = CMI.getInstance().getKitsManager().getKit("easter2024_stuzka_"+s, true);
            items.add(kit.getFirstNotNullItem());
        }
        items.add(CMI.getInstance().getKitsManager().getKit("easter2024_vrbovy_proutek", true).getFirstNotNullItem());
        whip = CMI.getInstance().getKitsManager().getKit("easter2024_pomlazka", true).getFirstNotNullItem();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player player))
            return false;

        List<ItemStack> toRemove = new ArrayList<>();
        for(ItemStack is:items){
            if(!player.getInventory().containsAtLeast(is, 1)){
                String missing = is.getItemMeta().getDisplayName().contains("Stužka") ? "všech 5 stužek" : "vrbový proutek";
                PlayerUtils.sendSystemMessage(player, "Nemáš "+missing+", bez toho pomlázku nevytvoříš. :(");
                return false;
            }else{
                toRemove.add(is);
            }
        }

        player.getInventory().removeItem(toRemove.toArray(ItemStack[]::new));
        player.getInventory().addItem(whip);
        PlayerUtils.sendSystemMessage(player, "Úspěšně sis upletl pomlázku. :)");

        return false;
    }
}
