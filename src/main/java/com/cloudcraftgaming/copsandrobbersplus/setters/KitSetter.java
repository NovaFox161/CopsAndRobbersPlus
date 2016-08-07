package com.cloudcraftgaming.copsandrobbersplus.setters;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class KitSetter {
    public static void setKit(Player player, String kitName) {
        if (!FileManager.getKitsYml().contains("Kits." + kitName)) {
            YamlConfiguration kits = FileManager.getKitsYml();
            List<String> kitItems = kits.getStringList("Kits." + kitName + ".Items.List");
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null) {
                    if (!kitItems.contains(item.getType().name())) {
                        kitItems.add(item.getType().name());
                        kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Amount", item.getAmount());
                        if (item.hasItemMeta()) {
                            ItemMeta meta = item.getItemMeta();
                            if (meta.hasDisplayName()) {
                                kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName", meta.getDisplayName());
                            }
                            if (meta.hasEnchants()) {
                                List<String> enchants = kits.getStringList("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List");
                                for (Enchantment enchant : meta.getEnchants().keySet()) {
                                    Integer level = meta.getEnchants().get(enchant);
                                    String enchantName = enchant.getName();
                                    enchants.add(enchantName);
                                    kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants." + enchantName + ".Level", level);
                                }
                                kits.set("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List", enchants);
                            }
                        }
                    }
                }
            }
            kits.set("Kits." + kitName + ".Items.List", kitItems);
            Main.plugin.saveCustomConfig(kits, FileManager.getKitsFile());
            String msgOr = MessageManager.getMessageYml().getString("Command.Kit.Create");
            String msg = msgOr.replaceAll("%kitName%", kitName);
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Kit.AlreadyExists"));
        }
    }
    public static void removeKit(Player player, String kitName) {
        if (FileManager.getKitsYml().contains("Kits." + kitName)) {
            YamlConfiguration kits = FileManager.getKitsYml();
            kits.set("Kits." + kitName, null);
            Main.plugin.saveCustomConfig(kits, FileManager.getKitsFile());
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Kit.Delete"));
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Kit.DoesNotExist"));
        }
    }
}