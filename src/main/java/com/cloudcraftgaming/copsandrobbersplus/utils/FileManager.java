package com.cloudcraftgaming.copsandrobbersplus.utils;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class FileManager {
    static Double configVersion = 1.0;

    public static void createConfig() {
        File file = new File(Main.plugin.getDataFolder() + "/config.yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating config.yml...");

            Main.plugin.getConfig().addDefault("DO NOT DELETE", "CopsAndRobbersPlus is developed and managed by Shades161");
            Main.plugin.getConfig().addDefault("Config Version", configVersion);
            Main.plugin.getConfig().addDefault("Check for Updates", true);
            Main.plugin.getConfig().addDefault("Language", "English");

            Main.plugin.getConfig().addDefault("Console.Verbose", true);

            Main.plugin.getConfig().addDefault("Game.Announce.End", true);

            Main.plugin.getConfig().addDefault("Chat.PerGame", true);
            Main.plugin.getConfig().addDefault("Chat.Prefix", "&4[C'nR: &5%arenaName%&4]");
            Main.plugin.getConfig().addDefault("Chat.PerWorldChatPlus.CompatibilityMode", true);

            List<String> blockedCommands = Main.plugin.getConfig().getStringList("Commands.Blocked");
            blockedCommands.add("Spawn");
            blockedCommands.add("Warp");
            blockedCommands.add("Hub");
            blockedCommands.add("Lobby");
            blockedCommands.add("Home");
            blockedCommands.add("tpa");
            blockedCommands.add("tp");
            Main.plugin.getConfig().set("Commands.Blocked", blockedCommands);

            Main.plugin.getConfig().options().copyDefaults(true);
            Main.plugin.saveConfig();

            Main.plugin.getConfig().options().copyDefaults(true);
            Main.plugin.saveConfig();
        }
    }
    public static void createPluginCache() {
        File file = new File(Main.plugin.getDataFolder() + "/Cache/pluginCache.yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating pluginCache.yml...");
            YamlConfiguration cache = YamlConfiguration.loadConfiguration(file);

            cache.addDefault("DO NOT DELETE", "CopsAndRobbersPlus is developed and managed by Shades161");
            cache.addDefault("DoNotEdit.NextId", 1);

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, file);

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, file);
        }
    }
    public static void createPlayerCache() {
        File file = new File(Main.plugin.getDataFolder() + "/Cache/playerCache.yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating playerCache.yml...");
            YamlConfiguration cache = YamlConfiguration.loadConfiguration(file);

            cache.addDefault("DO NOT DELETE", "CopsAndRobbersPlus is developed and managed by Shades161");

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, file);

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, file);
        }
    }
    public static void createKitFile() {
        File file = new File(Main.plugin.getDataFolder() + "/Kits/Kits.yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating kits.yml...");
            YamlConfiguration kits = YamlConfiguration.loadConfiguration(file);

            kits.addDefault("DO NOT DElETE", "CopsAndRobbersPlus is developed and managed by Shades161");

            //Create an example kit.
            List<String> exampleKit = kits.getStringList("Kits.Example.Items.List");
            exampleKit.add(Material.STICK.name());
            exampleKit.add(Material.MUSHROOM_SOUP.name());
            exampleKit.add(Material.WOOD_SWORD.name());
            kits.set("Kits.Example.Items.List", exampleKit);
            kits.addDefault("Kits.Example.Items." + Material.STICK.name() + ".Amount", 1);
            kits.addDefault("Kits.Example.Items." + Material.MUSHROOM_SOUP.name() + ".Amount", 3);
            kits.addDefault("Kits.Example.Items." + Material.WOOD_SWORD.name() + ".Amount", 1);

            //Create the default kit for cops.
            List<String> defaultCopKit = kits.getStringList("Kits.DefaultCops.Items.List");
            defaultCopKit.add(Material.STICK.name());
            defaultCopKit.add(Material.COOKED_BEEF.name());
            defaultCopKit.add(Material.STONE_SWORD.name());
            kits.set("Kits.DefaultCops.Items.List", defaultCopKit);
            kits.addDefault("Kits.DefaultCops.Items." + Material.STICK.name() + ".Amount", 1);
            kits.addDefault("Kits.DefaultCops.Items." + Material.COOKED_BEEF.name() + ".Amount", 5);
            kits.addDefault("Kits.DefaultCops.Items." + Material.STONE_SWORD.name() + ".Amount", 1);

            //Create the default kit for robbers.
            List<String> defaultRobberKit = kits.getStringList("Kits.DefaultRobbers.Items.List");
            defaultRobberKit.add(Material.COOKED_BEEF.name());
            kits.set("Kits.DefaultRobbers.Items.List", defaultRobberKit);
            kits.addDefault("Kits.DefaultRobbers.Items." + Material.COOKED_BEEF.name() + ".Amount", 16);

            kits.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(kits, file);

            kits.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(kits, file);
        }
    }

    public static void checkFileVersions() {
        if (Main.plugin.getConfig().getDouble("Config Version") != configVersion) {
            Main.plugin.getLogger().severe("Config.yml outdated!! Plugin will not work until file is updated!");
            Main.plugin.getLogger().severe("Please copy your settings, delete the config, and restart the server!");
            Main.plugin.getLogger().severe("Disabling plugin to prevent further issues...");
            Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
        }
        if (MessageManager.getMessageYml().getDouble("Messages Version") != MessageManager.messageVersion) {
            Main.plugin.getLogger().severe("Message file outdated!! Plugin will not work until file is updated!");
            Main.plugin.getLogger().severe("Please copy your messages, delete the message folder/file, and restart the server!");
            Main.plugin.getLogger().severe("Disabling plugin to prevent further issues...");
            Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
        }
    }


    public static File getPluginCacheFile() {
        return new File(Main.plugin.getDataFolder() + "/Cache/pluginCache.yml");
    }
    public static YamlConfiguration getPluginCacheYml() {
        return YamlConfiguration.loadConfiguration(getPluginCacheFile());
    }
    public static File getPlayerCacheFile() {
        return new File(Main.plugin.getDataFolder() + "/Cache/playerCache.yml");
    }
    public static YamlConfiguration getPlayerCacheYml() {
        return YamlConfiguration.loadConfiguration(getPlayerCacheFile());
    }
    public static File getKitsFile() {
        return new File(Main.plugin.getDataFolder() + "/Kits/Kits.yml");
    }
    public static YamlConfiguration getKitsYml() {
        return YamlConfiguration.loadConfiguration(getKitsFile());
    }
}