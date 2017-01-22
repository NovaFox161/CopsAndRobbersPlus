package com.cloudcraftgaming.copsandrobbersplus;

import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.arena.GameManager;
import com.cloudcraftgaming.copsandrobbersplus.commands.BaseCommand;
import com.cloudcraftgaming.copsandrobbersplus.listeners.*;
import com.cloudcraftgaming.copsandrobbersplus.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class Main extends JavaPlugin {
    public static Main plugin;
    public Plugin perWorldChatPlus;

    public void onDisable() {
        //Disable all arenas safely.
        unloadArenasDisable();
    }
    public void onEnable() {
        plugin = this;

        //Generate all needed files.
        FileManager.createConfig();
        FileManager.createPluginCache();
        FileManager.createPlayerCache();
        FileManager.createKitFile();

        MessageManager.createMessageFiles();

        //Register all listeners
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(this), this);

        //Register all commands.
        getCommand("car").setExecutor(new BaseCommand(this));
        getCommand("carp").setExecutor(new BaseCommand(this));
        getCommand("copsandrobbers").setExecutor(new BaseCommand(this));


        FileManager.checkFileVersions();

        //Check for plugins we may want to use.
        PluginChecker.checkForPerWorldChatPlus();

        //Check for plugin updates.
        checkForUpdatesStart();

        //Load all enabled arenas to ensure it is automated.
        loadArenasStartUp();
    }

    private void loadArenasStartUp() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
                    if (getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                        getLogger().info("Loading all enabled arenas...");
                    }
                    for (String idString : FileManager.getPluginCacheYml().getStringList("Arenas.Enabled")) {
                        Integer id = Integer.valueOf(idString);
                        if (!(ArenaManager.getManager().arenaExists(id))) {
                            ArenaManager.getManager().safeLoadArena(id);
                        }
                    }
                    if (getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                        getLogger().info("All enabled arenas loaded!");
                    }
                }
            }
        }, 20L * 5);
    }
    private void unloadArenasDisable() {
        if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
            if (getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                getLogger().info("Safely unloading all loaded and/or active arenas... Plugin will be disabled shortly.");
            }
            for (String idString : FileManager.getPluginCacheYml().getStringList("Arenas.Enabled")) {
                Integer id = Integer.valueOf(idString);
                if (ArenaManager.getManager().arenaExists(id)) {
                    Arena arena = ArenaManager.getManager().getArena(id);
                    if (arena.getGameState().equals(GameState.INGAME)) {
                        GameManager.endGame(id);
                    } else {
                        ArenaManager.getManager().safeUnloadArena(id);
                    }
                }
            }
            if (getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                getLogger().info("Unloaded all loaded/active arenas... Plugin will now be disabled.");
            }
        }
    }

    private void checkForUpdatesStart() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                UpdateChecker.checkForUpdates();
            }
        }, 20L);
    }

    public void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}