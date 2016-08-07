package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerMoveListener implements Listener {
    Main plugin;
    public PlayerMoveListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void setGameModeOnMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArenaManager.getManager().isInGame(player)) {
            if (ArenaManager.getManager().getArena(player).getGameState().equals(GameState.INGAME)) {
                if (!(player.getGameMode().equals(GameMode.SURVIVAL))) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        } else if (ArenaManager.getManager().isSpectating(player)) {
            if (!(player.getGameMode().equals(GameMode.SPECTATOR))) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}
