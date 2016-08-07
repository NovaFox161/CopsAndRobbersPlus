package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaFileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class SignChangeListener implements Listener {
    Main plugin;
    public SignChangeListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent event) {
        if (!(event.isCancelled())) {
            if (event.getLine(0).equalsIgnoreCase("[CnR]")) {
                Player player = event.getPlayer();
                if (player.hasPermission("CARP.sign.place")) {
                    if (event.getLine(1).equalsIgnoreCase("Join")) {
                        if (!(event.getLine(2).isEmpty())) {
                            try {
                                Integer arenaId = Integer.valueOf(event.getLine(2));
                                if (ArenaFileManager.arenaExists(arenaId)) {
                                    event.setLine(0, ChatColor.DARK_RED + "[CnR]");
                                    event.setLine(1, ChatColor.DARK_GREEN + "Join");
                                    event.setLine(2, String.valueOf(arenaId));
                                    String msgOr = MessageManager.getMessageYml().getString("Sign.Place.Join");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                                } else {
                                    String msg = MessageManager.getMessageYml().getString("Notifications.ArenaDoesNotExist");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                                    event.setCancelled(true);
                                }
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                                event.setCancelled(true);
                            }
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                            event.setCancelled(true);
                        }
                    } else if (event.getLine(1).equalsIgnoreCase("Spectate")) {
                        if (!(event.getLine(2).isEmpty())) {
                            try {
                                Integer arenaId = Integer.valueOf(event.getLine(2));
                                if (ArenaFileManager.arenaExists(arenaId)) {
                                    event.setLine(0,ChatColor.DARK_RED + "[CnR]");
                                    event.setLine(1, ChatColor.GREEN + "Spectate");
                                    event.setLine(2, String.valueOf(arenaId));
                                    String msgOr = MessageManager.getMessageYml().getString("Sign.Place.Spectate");
                                    player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                                }
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                                event.setCancelled(true);
                            }
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                            event.setCancelled(true);
                        }
                    } else if (event.getLine(1).equalsIgnoreCase("Quit")) {
                        event.setLine(0, ChatColor.DARK_RED + "[CnR]");
                        event.setLine(1, ChatColor.RED + "Quit");
                        String msgOr = MessageManager.getMessageYml().getString("Sign.Place.Quit");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    }
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
                    event.setCancelled(true);
                }
            }
        }
    }
}