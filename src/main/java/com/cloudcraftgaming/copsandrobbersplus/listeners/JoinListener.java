package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.UpdateChecker;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class JoinListener implements Listener {
    Main plugin;
    public JoinListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void notifyUpdate(PlayerJoinEvent event) {
        if (plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
            Player player = event.getPlayer();
            if (player.hasPermission("CARP.notify.update")) {
                UpdateChecker.checkForUpdates(player);
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void updatePlayerCache(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("CARP.use.command.set")) {
            YamlConfiguration playerCache = FileManager.getPlayerCacheYml();
            playerCache.set("Players." + player.getUniqueId().toString() + ".ArenaTool.Enabled", false);
            plugin.saveCustomConfig(playerCache, FileManager.getPlayerCacheFile());
        }
    }
}