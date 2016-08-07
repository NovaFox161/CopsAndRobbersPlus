package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class RespawnListener implements Listener {
    Main plugin;
    public RespawnListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            if (arena.getGameState().equals(GameState.INGAME)) {
                if (arena.getCops().contains(player.getUniqueId())) {
                    event.setRespawnLocation(ArenaDataGetters.getRandomCopSpawn(arena.getId()));
                } else {
                    event.setRespawnLocation(ArenaDataGetters.getRandomPrisonerSpawn(arena.getId()));
                }
            } else {
                event.setRespawnLocation(ArenaDataGetters.getLobbyPosition(arena.getId()));
            }
        } else if (ArenaManager.getManager().isSpectating(player)) {
            event.setRespawnLocation(ArenaDataGetters.getSpectatePosition(ArenaManager.getManager().getArena(player).getId()));
        }
    }
}