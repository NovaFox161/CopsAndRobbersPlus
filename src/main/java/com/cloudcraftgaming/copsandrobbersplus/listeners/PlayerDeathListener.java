package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerDeathListener implements Listener {
    Main plugin;
    public PlayerDeathListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            if (arena.getGameState().equals(GameState.INGAME)) {
                if (event.getDeathMessage() != null) {
                    event.setDeathMessage(null);
                }
            } else {
                if (event.getDeathMessage() != null) {
                    event.setDeathMessage(null);
                }
            }
        } else if (ArenaManager.getManager().isSpectating(player)) {
            if (event.getDeathMessage() != null) {
                event.setDeathMessage(null);
            }
        }
    }
}