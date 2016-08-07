package com.cloudcraftgaming.copsandrobbersplus.getters;

import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class KitGetter {
    public static ArrayList<ItemStack> getKit(String kitName) {
        ArrayList<ItemStack> items = new ArrayList<>();
        YamlConfiguration kits = FileManager.getKitsYml();
        List<String> itemStringList = kits.getStringList("Kits." + kitName + ".Items.List");

        for (String itemName : itemStringList) {
            Integer itemAmount = kits.getInt("Kits." + kitName + ".Items." + itemName + ".Amount");
            Material itemMat = Material.getMaterial(itemName);
            ItemStack item = new ItemStack(itemMat, itemAmount);
            ItemMeta meta = item.getItemMeta();

            if (kits.contains("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName")) {
                String displayName = kits.getString("Kits." + kitName + ".Items." + item.getType().name() + ".Meta.DisplayName");
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            }
            if (kits.contains("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List")) {
                for (String enchantName : kits.getStringList("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants.List")) {
                    Integer level = kits.getInt("Kits." + kitName + ".Items." + item.getType().name() + ".Enchants." + enchantName + ".Level");
                    Enchantment enchant = Enchantment.getByName(enchantName);
                    meta.addEnchant(enchant, level, false);
                }
            }

            item.setItemMeta(meta);
            items.add(item);
        }
        return items;
    }
}
