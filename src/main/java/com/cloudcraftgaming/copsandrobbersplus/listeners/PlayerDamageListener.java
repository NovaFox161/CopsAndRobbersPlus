package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerDamageListener implements Listener {
    Main plugin;
    public PlayerDamageListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (ArenaManager.getManager().isInGame(player)) {
                if (event.getDamager() instanceof Player) {
                    Player damager = (Player)event.getDamager();
                    Arena arena = ArenaManager.getManager().getArena(player);
                    if (arena.getCops().contains(damager.getUniqueId()) && !arena.getCops().contains(player.getUniqueId())) {
                        player.teleport(ArenaDataGetters.getRandomPrisonerSpawn(arena.getId()));
                        //Send prisoner message.
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.Robber.Caught"));
                        //Send cop message.
                        damager.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.Cop.Caught"));
                    } else if (arena.getCops().contains(damager.getUniqueId()) && arena.getCops().contains(player.getUniqueId())) {
                        event.setCancelled(true);
                        damager.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.Cop.HitCop"));
                    }
                }
            }
        }
    }
}