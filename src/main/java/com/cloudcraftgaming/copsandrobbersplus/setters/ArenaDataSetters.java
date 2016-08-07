package com.cloudcraftgaming.copsandrobbersplus.setters;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaFileManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class ArenaDataSetters {
    //General Setters
    public static void setName(int id, String name) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Name", name);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setDisplayName(int id, String displayName) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("DisplayName", displayName);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    //Player count setters
    public static void setMinPlayers(int id, int minPlayers) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Players.General.Min", minPlayers);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setMaxPlayers(int id, int maxPlayers) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Players.General.Max", maxPlayers);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setMinCops(int id, int minCops) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Players.Cops.Min", minCops);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setMaxCops(int id, int maxCops) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Players.Cops.Max", maxCops);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    //Time setters
    public static void setWaitDelay(int id, int waitDelay) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Time.Delay.Wait", waitDelay);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setStartDelay(int id, int startDelay) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Time.Delay.Start", startDelay);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setGameLength(int id, int gameLength) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Time.Game.Length", gameLength);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    //Game setters
    @SuppressWarnings("deprecation")
    public static void setWinBlock(int id, int blockId) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        try {
            Material item = Material.getMaterial(blockId);
            config.set("Game.WinBlock", item.name());
            Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
        } catch (Exception e) {
            Main.plugin.getLogger().warning("An error has occurred in Cops'nRobbers. This is probably nothing, just ignore the warn.");
        }
    }
    public static void setWinBlock(int id, String blockName) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        try {
            Material item = Material.getMaterial(blockName);
            config.set("Game.WinBlock", item.name());
            Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
        } catch (Exception e) {
            Main.plugin.getLogger().warning("An error has occurred in Cops'nRobbers. This is probably nothing, just ignore the warn.");
        }
    }
    public static void setWinBlock(int id, ItemStack blockItem) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        try {
            Material item = blockItem.getType();
            config.set("Game.WinBlock", item.name());
            Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
        } catch (Exception e) {
            Main.plugin.getLogger().warning("An error has occurred in Cops'nRobbers. This is probably nothing, just ignore the warn.");
        }
    }

    public static void setCopsDefaultKit(int id, String kitName) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Game.Kits.Cops.Default", kitName);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setRobbersDefaultKit(int id, String kitName) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Game.Kits.Robbers.Default", kitName);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    //Rules setters
    public static void setBreakBlocks(int id, Boolean canBreak) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Rules.Block.Break", canBreak);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setPlaceBlocks(int id, Boolean canPlace) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Rules.Block.Place", canPlace);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setUseKits(int id, Boolean canUse) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Rules.Kits.Use", canUse);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setAllowLateJoin(int id, Boolean allowed) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Rules.LateJoin.Allowed", allowed);
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    //Location setters
    public static void setLobbyPosition(int id, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Lobby.world", loc.getWorld().getName());
        config.set("Locations.Lobby.x", loc.getX());
        config.set("Locations.Lobby.y", loc.getY());
        config.set("Locations.Lobby.z", loc.getZ());
        config.set("Locations.Lobby.yaw", loc.getYaw());
        config.set("Locations.Lobby.pitch", loc.getPitch());
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setEndPosition(int id, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.End.world", loc.getWorld().getName());
        config.set("Locations.End.x", loc.getX());
        config.set("Locations.End.y", loc.getY());
        config.set("Locations.End.z", loc.getZ());
        config.set("Locations.End.yaw", loc.getYaw());
        config.set("Locations.End.pitch", loc.getPitch());
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setQuitPosition(int id, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Quit.world", loc.getWorld().getName());
        config.set("Locations.Quit.x", loc.getX());
        config.set("Locations.Quit.y", loc.getY());
        config.set("Locations.Quit.z", loc.getZ());
        config.set("Locations.Quit.yaw", loc.getYaw());
        config.set("Locations.Quit.pitch", loc.getPitch());
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setSpectatePosition(int id, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Spectate.world", loc.getWorld().getName());
        config.set("Locations.Spectate.x", loc.getX());
        config.set("Locations.Spectate.y", loc.getY());
        config.set("Locations.Spectate.z", loc.getZ());
        config.set("Locations.Spectate.yaw", loc.getYaw());
        config.set("Locations.Spectate.pitch", loc.getPitch());
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setRobberSpawn(int id, int spawnNumber, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".world", loc.getWorld().getName());
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".x", loc.getX());
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".y", loc.getY());
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".z", loc.getZ());
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".yaw", loc.getYaw());
        config.set("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".pitch", loc.getPitch());

        List<String> spawnList = config.getStringList("Lists.Spawns.Prisoner");
        if (!(spawnList.contains(String.valueOf(spawnNumber)))) {
            spawnList.add(String.valueOf(spawnNumber));
            config.set("Lists.Spawns.Prisoner", spawnList);
        }
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
    public static void setCopSpawn(int id, int spawnNumber, Location loc) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".world", loc.getWorld().getName());
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".x", loc.getX());
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".y", loc.getY());
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".z", loc.getZ());
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".yaw", loc.getYaw());
        config.set("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".pitch", loc.getPitch());

        List<String> spawnList = config.getStringList("Lists.Spawns.Cop");
        if (!(spawnList.contains(String.valueOf(spawnNumber)))) {
            spawnList.add(String.valueOf(spawnNumber));
            config.set("Lists.Spawns.Cop", spawnList);
        }
        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }

    public static void setRegenArea(int id, Location loc1, Location loc2) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        config.set("Locations.Regen.1.world", loc1.getWorld().getName());
        config.set("Locations.Regen.1.x", loc1.getX());
        config.set("Locations.Regen.1.y", loc1.getY());
        config.set("Locations.Regen.1.z", loc1.getZ());

        config.set("Locations.Regen.2.world", loc2.getWorld().getName());
        config.set("Locations.Regen.2.x", loc2.getX());
        config.set("Locations.Regen.2.y", loc2.getY());
        config.set("Locations.Regen.2.z", loc2.getZ());

        Main.plugin.saveCustomConfig(config, ArenaFileManager.getArenaConfigFile(id));
    }
}