package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.arena.GameManager;
import com.cloudcraftgaming.copsandrobbersplus.arena.PlayerHandler;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerInteractListener implements Listener {
    Main plugin;
    public PlayerInteractListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).contains("[CnR]")) {
                    if (player.hasPermission("CARP.sign.use")) {
                        if (sign.getLine(1).contains("Quit")) {
                            PlayerHandler.quitArena(player);
                        } else if (sign.getLine(1).contains("Spectate")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                PlayerHandler.spectateArena(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessageYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        } else if (sign.getLine(1).contains("Join")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                PlayerHandler.joinArena(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessageYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
                    }
                }
            } else {
                if (!(event.getItem() == null)) {
                    if (event.getItem().getType().equals(Material.STICK)) {
                        if (player.hasPermission("CARP.use.command.set")) {
                            YamlConfiguration playerCache = FileManager.getPlayerCacheYml();
                            if (playerCache.getString("Players." + uuid + ".ArenaTool.Enabled").equalsIgnoreCase("True")) {
                                Location blockLoc = event.getClickedBlock().getLocation();
                                playerCache.set("Players." + uuid + ".Pos.Loc2.world", blockLoc.getWorld().getName());
                                playerCache.set("Players." + uuid + ".Pos.Loc2.x", blockLoc.getX());
                                playerCache.set("Players." + uuid + ".Pos.Loc2.y", blockLoc.getY());
                                playerCache.set("Players." + uuid + ".Pos.Loc2.z", blockLoc.getZ());
                                plugin.saveCustomConfig(playerCache, FileManager.getPlayerCacheFile());
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc2"));
                                if (playerCache.contains("Players." + uuid + ".Pos.Loc1")) {
                                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Both"));
                                } else {
                                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc2Only"));
                                }
                            }
                        }
                    }
                }
            }
        } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(0).contains("[CnR]")) {
                    if (player.hasPermission("CARP.sign.use")) {
                        if (sign.getLine(1).contains("Join")) {
                            try {
                                Integer arenaId = Integer.valueOf(sign.getLine(2));
                                PlayerHandler.sendArenaInfo(player, arenaId);
                            } catch (NumberFormatException e) {
                                String msgOr = MessageManager.getMessageYml().getString("Notifications.ArenaDoesNotExist");
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                            }
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
                    }
                }
            } else {
                if (!(event.getItem() == null)) {
                    if (event.getItem().getType().equals(Material.STICK)) {
                        if (player.hasPermission("CARP.use.command.set")) {
                            YamlConfiguration playerCache = FileManager.getPlayerCacheYml();
                            if (playerCache.getString("Players." + uuid + ".ArenaTool.Enabled").equalsIgnoreCase("True")) {
                                Location blockLoc = event.getClickedBlock().getLocation();
                                playerCache.set("Players." + uuid + ".Pos.Loc1.world", blockLoc.getWorld().getName());
                                playerCache.set("Players." + uuid + ".Pos.Loc1.x", blockLoc.getX());
                                playerCache.set("Players." + uuid + ".Pos.Loc1.y", blockLoc.getY());
                                playerCache.set("Players." + uuid + ".Pos.Loc1.z", blockLoc.getZ());
                                plugin.saveCustomConfig(playerCache, FileManager.getPlayerCacheFile());
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc1"));
                                if (playerCache.contains("Players." + uuid + ".Pos.Loc2")) {
                                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Both"));
                                } else {
                                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Loc1Only"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractTwo(PlayerInteractEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            Arena arena = ArenaManager.getManager().getArena(uuid);
            Material winBlock = ArenaDataGetters.getWinBlock(arena.getId());

            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(winBlock)) {
                        if (!arena.getCops().contains(uuid)) {
                            GameManager.determineWinningTeam(arena.getId(), player);
                        }
                    }
                }
            } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(winBlock)) {
                        if (!arena.getCops().contains(uuid)) {
                            GameManager.determineWinningTeam(arena.getId(), player);
                        }
                    }
                }
            }
        }
    }
}