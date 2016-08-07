package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class ArenaFileManager {
    public static void createArena(CommandSender sender) {
        Integer arenaId = FileManager.getPluginCacheYml().getInt("DoNotEdit.NextId");
        createArenaFiles();

        String msgOr = MessageManager.getMessageYml().getString("Command.Create");
        String msg = msgOr.replaceAll("%id%", String.valueOf(arenaId));
        sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
    }
    public static void createArenaFiles() {
        int id = FileManager.getPluginCacheYml().getInt("DoNotEdit.NextId");

        YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
        pluginCache.set("DoNotEdit.NextId", id + 1);
        Main.plugin.saveCustomConfig(pluginCache, FileManager.getPluginCacheFile());

        File arenaConfigFile = new File(Main.plugin.getDataFolder() + "/Arenas/" + String.valueOf(id) + "/config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(arenaConfigFile);

        config.addDefault("Id", String.valueOf(id));
        config.addDefault("Name", "Arena " + String.valueOf(id));
        config.addDefault("DisplayName", "&6Arena " + String.valueOf(id));

        config.addDefault("Players.General.Min", 2);
        config.addDefault("Players.General.Max", 16);
        config.addDefault("Players.Cops.Min", 1);
        config.addDefault("Players.Cops.Max", 2);

        config.addDefault("Time.Delay.Wait", 60);
        config.addDefault("Time.Delay.Start", 10);
        config.addDefault("Time.Game.Length", 15);

        config.addDefault("Game.WinBlock", Material.SPONGE.name());
        config.addDefault("Game.Kits.Cops.Default", "DefaultCops");
        config.addDefault("Game.Kits.Robbers.Default", "DefaultRobbers");

        config.addDefault("Rules.Block.Break", false);
        config.addDefault("Rules.Block.Place", false);
        config.addDefault("Rules.Kits.Use", true);
        config.addDefault("Rules.LateJoin.Allowed", false);

        config.options().copyDefaults(true);
        Main.plugin.saveCustomConfig(config, arenaConfigFile);

        config.options().copyDefaults(true);
        Main.plugin.saveCustomConfig(config, arenaConfigFile);


        List<String> arenas = pluginCache.getStringList("Arenas.All");
        arenas.add(String.valueOf(id));
        pluginCache.set("Arenas.All", arenas);
        Main.plugin.saveCustomConfig(pluginCache, FileManager.getPluginCacheFile());
    }

    //File getters
    public static File getArenaConfigFile(int id) {
        if (arenaExists(id)) {
            return new File(Main.plugin.getDataFolder() + "/Arenas/" + String.valueOf(id) + "/config.yml");
        } else {
            return null;
        }
    }
    public static YamlConfiguration getArenaConfigYml(int id) {
        return YamlConfiguration.loadConfiguration(getArenaConfigFile(id));
    }

    //Arena checking methods
    public static Boolean arenaExists(int i) {
        if (FileManager.getPluginCacheYml().contains("Arenas.All")) {
            return FileManager.getPluginCacheYml().getStringList("Arenas.All").contains(String.valueOf(i));
        } else {
            return false;
        }
    }
    public static Boolean canLoadArena(int id) {
        if (!arenaExists(id)) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.Lobby")) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.End")) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.Quit")) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.Spectate")) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.Spawn.Prisoner")) {
            return false;
        }
        if (!getArenaConfigYml(id).contains("Locations.Spawn.Cop")) {
            return false;
        }
        return true;
    }

    //Enable and Disable arena methods.
    public static void enableArena(int id) {
        YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
        List<String> enabledArenas = pluginCache.getStringList("Arenas.Enabled");
        enabledArenas.add(String.valueOf(id));
        pluginCache.set("Arenas.Enabled", enabledArenas);
        Main.plugin.saveCustomConfig(pluginCache, FileManager.getPluginCacheFile());
        ArenaManager.getManager().safeLoadArena(id);
    }
    public static void disableArena(int id) {
        if (arenaExists(id)) {
            YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
            List<String> enabledArenas = pluginCache.getStringList("Arenas.Enabled");
            enabledArenas.remove(String.valueOf(id));
            pluginCache.set("Arenas.Enabled", enabledArenas);
            Main.plugin.saveCustomConfig(pluginCache, FileManager.getPluginCacheFile());
            ArenaManager.getManager().safeUnloadArena(id);
        }
    }
}
