package com.cloudcraftgaming.copsandrobbersplus.getters;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaFileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.Cuboid;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class ArenaDataGetters {

    //General getters
    public static String getName(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getString("Name");
    }
    public static String getDisplayName(int id) {
        String disNameOr = ArenaFileManager.getArenaConfigYml(id).getString("DisplayName");
        return ChatColor.translateAlternateColorCodes('&', disNameOr);
    }
    public static String getChatPrefix(int id) {
        String prefixOr = Main.plugin.getConfig().getString("Chat.Prefix");
        String prefix = prefixOr.replaceAll("%arenaName%", getDisplayName(id));
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    //Player count getters
    public static int getMinPlayers(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Players.General.Min");
    }
    public static int getMaxPlayers(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Players.General.Max");
    }
    public static int getMinCops(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Players.Cops.Min");
    }
    public static int getMaxCops(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Players.Cops.Max");
    }

    //Time getters
    public static int getWaitDelay(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Time.Delay.Wait");
    }
    public static int getStartDelay(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Time.Delay.Start");
    }
    public static int getGameLength(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getInt("Time.Game.Length");
    }

    //Game getters
    public static Material getWinBlock(int id) {
        return Material.getMaterial(ArenaFileManager.getArenaConfigYml(id).getString("Game.WinBlock"));
    }
    public static String getDefaultCopKitName(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getString("Game.Kits.Cops.Default");
    }
    public static String getDefaultRobberKitName(int id) {
        return ArenaFileManager.getArenaConfigYml(id).getString("Game.Kits.Robbers.Default");
    }

    //Rules getters
    public static Boolean canBreakBlocks(int id) {
        return Boolean.valueOf(ArenaFileManager.getArenaConfigYml(id).getString("Rules.Block.Break"));
    }
    public static Boolean canPlaceBlocks(int id) {
        return Boolean.valueOf(ArenaFileManager.getArenaConfigYml(id).getString("Rules.Block.Place"));
    }
    public static Boolean useKits(int id) {
        return Boolean.valueOf(ArenaFileManager.getArenaConfigYml(id).getString("Rules.Kits.Use"));
    }
    public static Boolean allowLateJoin(int id) {
        if (ArenaFileManager.getArenaConfigYml(id).contains("Rules.LateJoin.Allowed")) {
            return Boolean.valueOf(ArenaFileManager.getArenaConfigYml(id).getString("Rules.LateJoin.Allowed"));
        } else return false;
    }

    //Location getters
    public static Location getLobbyPosition(int id) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Lobby.world"));
        Double x = config.getDouble("Locations.Lobby.x");
        Double y = config.getDouble("Locations.Lobby.y");
        Double z = config.getDouble("Locations.Lobby.z");
        Integer ya = config.getInt("Locations.Lobby.yaw");
        Integer pi = config.getInt("Locations.Lobby.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getEndPosition(int id) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.End.world"));
        Double x = config.getDouble("Locations.End.x");
        Double y = config.getDouble("Locations.End.y");
        Double z = config.getDouble("Locations.End.z");
        Integer ya = config.getInt("Locations.End.yaw");
        Integer pi = config.getInt("Locations.End.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getQuitPosition(int id) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Quit.world"));
        Double x = config.getDouble("Locations.Quit.x");
        Double y = config.getDouble("Locations.Quit.y");
        Double z = config.getDouble("Locations.Quit.z");
        Integer ya = config.getInt("Locations.Quit.yaw");
        Integer pi = config.getInt("Locations.Quit.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getSpectatePosition(int id) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Spectate.world"));
        Double x = config.getDouble("Locations.Spectate.x");
        Double y = config.getDouble("Locations.Spectate.y");
        Double z = config.getDouble("Locations.Spectate.z");
        Integer ya = config.getInt("Locations.Spectate.yaw");
        Integer pi = config.getInt("Locations.Spectate.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getPrisonerSpawn(int id, int spawnNumber) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Prisoner." + String.valueOf(spawnNumber) + ".world"));
        Double x = config.getDouble("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".x");
        Double y = config.getDouble("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".y");
        Double z = config.getDouble("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".z");
        Integer ya = config.getInt("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".yaw");
        Integer pi = config.getInt("Locations.Spawn.Prisoner." + String.valueOf(spawnNumber) + ".pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getCopSpawn(int id, int spawnNumber) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Cop." + String.valueOf(spawnNumber) + ".world"));
        Double x = config.getDouble("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".x");
        Double y = config.getDouble("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".y");
        Double z = config.getDouble("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".z");
        Integer ya = config.getInt("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".yaw");
        Integer pi = config.getInt("Locations.Spawn.Cop." + String.valueOf(spawnNumber) + ".pitch");
        return new Location(world, x, y, z, ya, pi);
    }

    public static Cuboid getRegenArea(int id) {
        YamlConfiguration config = ArenaFileManager.getArenaConfigYml(id);
        World w1 = Bukkit.getWorld(config.getString("Locations.Regen.1.world"));
        Double x1 = config.getDouble("Locations.Regen.1.x");
        Double y1 = config.getDouble("Locations.Regen.1.y");
        Double z1 = config.getDouble("Locations.Regen.1.z");

        World w2 = Bukkit.getWorld(config.getString("Locations.Regen.2.world"));
        Double x2 = config.getDouble("Locations.Regen.2.x");
        Double y2 = config.getDouble("Locations.Regen.2.y");
        Double z2 = config.getDouble("Locations.Regen.2.z");

        Location loc1 = new Location(w1, x1, y1, z1);
        Location loc2 = new Location(w2, x2, y2, z2);
        return new Cuboid(loc1, loc2);
    }

    //Random location getters
    public static Location getRandomPrisonerSpawn(int id) {
        ArrayList<Location> spawns = new ArrayList<>();
        for (String spawnNum : ArenaFileManager.getArenaConfigYml(id).getStringList("Lists.Spawns.Prisoner")) {
            String worldName = ArenaFileManager.getArenaConfigYml(id).getString("Locations.Spawn.Prisoner." + spawnNum + ".world");
            Double x = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Prisoner." + spawnNum + ".x");
            Double y = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Prisoner." + spawnNum + ".y");
            Double z = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Prisoner." + spawnNum + ".z");
            Integer ya = ArenaFileManager.getArenaConfigYml(id).getInt("Locations.Spawn.Prisoner." + spawnNum + ".yaw");
            Integer pi = ArenaFileManager.getArenaConfigYml(id).getInt("Locations.Spawn.Prisoner." + spawnNum + ".pitch");
            World world = Bukkit.getWorld(worldName);
            Location spawn = new Location(world, x, y, z, ya, pi);
            spawns.add(spawn);
        }
        Collections.shuffle(spawns);
        return spawns.get(0);
    }
    public static Location getRandomCopSpawn(int id) {
        ArrayList<Location> spawns = new ArrayList<>();
        for (String spawnNum : ArenaFileManager.getArenaConfigYml(id).getStringList("Lists.Spawns.Cop")) {
            String worldName = ArenaFileManager.getArenaConfigYml(id).getString("Locations.Spawn.Cop." + spawnNum + ".world");
            Double x = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Cop." + spawnNum + ".x");
            Double y = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Cop." + spawnNum + ".y");
            Double z = ArenaFileManager.getArenaConfigYml(id).getDouble("Locations.Spawn.Cop." + spawnNum + ".z");
            Integer ya = ArenaFileManager.getArenaConfigYml(id).getInt("Locations.Spawn.Cop." + spawnNum + ".yaw");
            Integer pi = ArenaFileManager.getArenaConfigYml(id).getInt("Locations.Spawn.Cop." + spawnNum + ".pitch");
            World world = Bukkit.getWorld(worldName);
            Location spawn = new Location(world, x, y, z, ya, pi);
            spawns.add(spawn);
        }
        Collections.shuffle(spawns);
        return spawns.get(0);
    }
}