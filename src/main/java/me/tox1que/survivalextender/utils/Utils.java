package me.tox1que.survivalextender.utils;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import me.tox1que.survivalextender.SurvivalExtender;
import me.tox1que.survivalextender.utils.enums.ServerType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Utils{

    private static final SurvivalExtender main = SurvivalExtender.getInstance();
    private static final String prefix = main.getPrefix();
    private static final ChatColor primaryColor = main.getPrimaryColor();
    private static final ChatColor secondaryColor = main.getSecondaryColor();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss");

    public static void actionBarMessage(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static ItemStack createEnchantedItem(Material material, String name, List<String> lore){
        ItemStack item = createItem(material, name, lore);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.SOUL_SPEED, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(Material material, String name, List<String> lore, ItemFlag... itemFlags){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        if(itemFlags != null)
            meta.addItemFlags(itemFlags);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enchantItem(ItemStack item, Enchantment ench, int level){
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(ench, level, true);
        item.setItemMeta(meta);
        return item;
    }

    public static void borderInventory(Inventory inventory, ItemStack fill){
        for(int i : getBorders(inventory.getSize())){
            inventory.setItem(i, fill);
        }
    }

    private static int[] getBorders(int size){
        int width = 9;
        int height = size / width;
        List<Integer> indexesList = new ArrayList<>();

        for(int i = 0; i < size; i++){
            int row = i / width;
            int col = i % width;

            if(row == 0 || row == height - 1 || col == 0 || col == width - 1){
                indexesList.add(i);
            }
        }

        // Convert List<Integer> to int[]
        int[] indexes = new int[indexesList.size()];
        for(int i = 0; i < indexesList.size(); i++){
            indexes[i] = indexesList.get(i);
        }

        return indexes;
    }

    public static double getPercentage(int x, int y){
        if(y == 0)
            return x > 0 ? 100.0 : (x < 0 ? -100.0 : 0.0);
        return Double.parseDouble(String.format("%.2f", (double) x / (x + y) * 100));
    }

    public static String formatNumber(String number){
        try{
            return formatNumber(Integer.parseInt(number));
        }catch(Exception e){
            e.printStackTrace();
            return number;
        }
    }

    public static String formatNumber(double number){
        DecimalFormat formatter;
        if(number % 1 == 0){
            formatter = new DecimalFormat("#,##0");
        }else{
            formatter = new DecimalFormat("#,##0.00");
        }
        return formatter.format(number).replaceAll(",", " ");
    }

    public static String getFormattedTime(int time){
        int minutes = (int) Math.floor(time / 60.0);
        int seconds = time % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public static String getFormattedTime(Date date){
        return dateFormat.format(date);
    }

    public static ItemStack getKitItem(String name){
        Kit kit = CMI.getInstance().getKitsManager().getKit(name, true);
        if(kit == null){
            main.getLogger().severe("Kit "+name+" is null! !");
            return null;
        }
        return kit.getFirstNotNullItem();
    }

    public static String getPluginMessage(String msg){
        return getPluginMessage(prefix, msg);
    }

    public static String getPluginMessage(String prefix, String msg){
        return getPluginColoredMessage(ChatColor.translateAlternateColorCodes('&',
                prefix + " &7» [pc]" + msg));
    }

    public static String getPluginColoredMessage(String msg){
        return msg
                .replace("[pc]", primaryColor + "")
                .replace("[sc]", secondaryColor + "");
    }

    public static ServerType getServerType(){
        int port = SurvivalExtender.getInstance().getServer().getPort();
        return switch(port){
            case 30029 -> ServerType.SURVIVAL_REWORK;
            case 30003,30015 -> ServerType.SURVIVAL;
            case 30001 -> ServerType.ONEBLOCK;
            default -> null;
        };
    }

    public static String getWordVariant(int amount, String singular, String plural, String secondPlural){
        return amount == 1 ? singular : amount <= 4 && amount >= 2 ? plural : secondPlural;
    }

    public static Location parseLocation(String string){
        String[] split = string.split(";");

        World w = Bukkit.getServer().getWorld(split[0]);
        float x = Float.parseFloat(split[1]);
        float y = Float.parseFloat(split[2]);
        float z = Float.parseFloat(split[3]);

        if(split.length > 4){
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static Location parseLocation(String string, World w){
        String[] split = string.split(";");

        float x = Float.parseFloat(split[0]);
        float y = Float.parseFloat(split[1]);
        float z = Float.parseFloat(split[2]);

        if(split.length > 3){
            float yaw = Float.parseFloat(split[3]);
            float pitch = Float.parseFloat(split[4]);
            return new Location(w, x, y, z, yaw, pitch);
        }
        return new Location(w, x, y, z);
    }

    public static String parseLocation(Location location){
        return String.join(";", location.getWorld().getName(), String.format("%.5f", location.getX()),
                String.format("%.5f", location.getY()), String.format("%.5f", location.getZ()));
    }

    public static void performConsoleCommand(String command){
        Bukkit.dispatchCommand(SurvivalExtender.getInstance().getServer().getConsoleSender(), command);
    }

    /**
     * Generates random number, 0 => result < bound
     * @param bound - result will be lower than this
     * @return new Random().nextInt(bound);
     */
    public static int randomNumber(int bound){
        return new Random().nextInt(bound);
    }
}
