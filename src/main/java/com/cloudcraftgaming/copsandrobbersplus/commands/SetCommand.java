package com.cloudcraftgaming.copsandrobbersplus.commands;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaFileManager;
import com.cloudcraftgaming.copsandrobbersplus.setters.ArenaDataSetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class SetCommand {
    public static void setCommand(Player player, String[] args) {
        if (player.hasPermission("CARP.use.command.set")) {
            if (args.length == 1) {
                //car set
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 2) {
                //car set <id>
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 3) {
                //car set <id> <type>
                String type = args[2];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaFileManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("lobby") || type.equalsIgnoreCase("LobbyLocation") || type.equalsIgnoreCase("LobbyPosition")) {
                            ArenaDataSetters.setLobbyPosition(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Lobby", id));
                        } else if (type.equalsIgnoreCase("end") || type.equalsIgnoreCase("endLocation") || type.equalsIgnoreCase("EndPosition")) {
                            ArenaDataSetters.setEndPosition(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.End", id));
                        } else if (type.equalsIgnoreCase("quit") || type.equalsIgnoreCase("QuitLocation") || type.equalsIgnoreCase("QuitPosition")) {
                            ArenaDataSetters.setQuitPosition(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Quit", id));
                        } else if (type.equalsIgnoreCase("spectate") || type.equalsIgnoreCase("spectateLocation")
                                || type.equalsIgnoreCase("SpectatePosition")) {
                            ArenaDataSetters.setSpectatePosition(id, player.getLocation());
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Spectate", id));
                        } else if (type.equalsIgnoreCase("regen") || type.equalsIgnoreCase("RegenArea")) {
                            setRegen(player, id);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            } else if (args.length == 4) {
                //car set <id> <type> <value>
                String type = args[2];
                String valueString = args[3];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaFileManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("minPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setMinPlayers(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.MinPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MinPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("maxPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setMaxPlayers(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.MaxPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MaxPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("minCops")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setMinCops(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.MinCops");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MinPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("maxCops")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setMaxCops(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.MaxCops");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.MaxPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("Wait") || type.equalsIgnoreCase("waitDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setWaitDelay(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Time.Wait");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("Start") || type.equalsIgnoreCase("StartDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setStartDelay(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Time.Start");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("GameTime") || type.equalsIgnoreCase("GameLength")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataSetters.setGameLength(id, value);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Time.Game");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("UseKits")) {
                            try {
                                Boolean canUse = Boolean.valueOf(valueString);
                                ArenaDataSetters.setUseKits(id, canUse);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Kit.CanUse");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%canUse%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (Exception e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Bool.Kits"));
                            }
                        } else if (type.equalsIgnoreCase("lateJoin")) {
                            try {
                                Boolean lateJoinAllowed = Boolean.valueOf(valueString);
                                ArenaDataSetters.setAllowLateJoin(id, lateJoinAllowed);
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.LateJoin");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%allowed%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (Exception e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Bool.LateJoin"));
                            }
                        } else if (type.equalsIgnoreCase("copKit") || type.equalsIgnoreCase("copDefaultKit")) {
                            ArenaDataSetters.setCopsDefaultKit(id, valueString);
                            String msgOr = MessageManager.getMessageYml().getString("Command.Set.Kit.Default.Cop");
                            String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%kitName%", valueString);
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("robberKit") || type.equalsIgnoreCase("robberDefaultKit")) {
                            ArenaDataSetters.setRobbersDefaultKit(id, valueString);
                            String msgOr = MessageManager.getMessageYml().getString("Command.Set.Kit.Default.Robber");
                            String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%kitName%", valueString);
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("copSpawn")) {
                            try {
                                Integer spawnNumber = Integer.valueOf(valueString);
                                ArenaDataSetters.setCopSpawn(id, spawnNumber, player.getLocation());
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Spawn.Cop");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%number%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));

                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Spawn"));
                            }
                        } else if (type.equalsIgnoreCase("robberSpawn")) {
                            try {
                                Integer spawnNumber = Integer.valueOf(valueString);
                                ArenaDataSetters.setRobberSpawn(id, spawnNumber, player.getLocation());
                                String msgOr = MessageManager.getMessageYml().getString("Command.Set.Spawn.Robber");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%number%", valueString);
                                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Spawn"));
                            }
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            } else if (args.length > 4) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
    private static void setRegen(Player player, int id) {
        if (FileManager.getPlayerCacheYml().contains("Players." + player.getUniqueId().toString() + ".Pos.Loc2") &&
                FileManager.getPlayerCacheYml().contains("Players." + player.getUniqueId().toString() + ".Pos.Loc1")) {
            UUID uuid = player.getUniqueId();
            String worldName1 = FileManager.getPlayerCacheYml().getString("Players." + uuid + ".Pos.Loc1.world");
            Integer x1 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc1.x");
            Integer y1 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc1.y");
            Integer z1 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc1.z");
            World world1 = Bukkit.getWorld(worldName1);
            String worldName2 = FileManager.getPlayerCacheYml().getString("Players." + uuid + ".Pos.Loc2.world");
            Integer x2 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc2.x");
            Integer y2 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc2.y");
            Integer z2 = FileManager.getPlayerCacheYml().getInt("Players." + uuid + ".Pos.Loc2.z");
            World world2 = Bukkit.getWorld(worldName2);
            Location loc1 = new Location(world1, x1, y1, z1);
            Location loc2 = new Location(world2, x2, y2, z2);
            ArenaDataSetters.setRegenArea(id, loc1, loc2);
            FileManager.getPlayerCacheYml().set("Players." + uuid + ".Pos", null);
            Main.plugin.saveCustomConfig(FileManager.getPlayerCacheYml(), FileManager.getPlayerCacheFile());
            String msgOr = MessageManager.getMessageYml().getString("Command.Set.Regen");
            String msg = msgOr.replaceAll("%id%", String.valueOf(id));
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Loc.Need"));
        }
    }
}
