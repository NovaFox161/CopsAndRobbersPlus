package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameBoardManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Nova Fox on 5/7/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerHandler {
    public static void joinArena(Player player, int id) {
        if (!ArenaManager.getManager().isInGame(player)) {
            if (ArenaManager.getManager().arenaExists(id)) {
                Arena arena = ArenaManager.getManager().getArena(id);
                if (arena.getPlayerCount() < arena.getMaxPlayers()) {
                    if (arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS) || arena.getGameState().equals(GameState.EMPTY)) {
                        if (inventoryEmpty(player)) {
                            arena.setPlayerCount(arena.getPlayerCount() + 1);
                            arena.getPlayers().add(player.getUniqueId());
                            player.teleport(ArenaDataGetters.getLobbyPosition(id));
                            GameMessages.announcePlayerJoin(id, player);
                            ArenaManager.getManager().checkPlayerCount(id);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Player.InvHasItem"));
                        }
                    } else if (arena.getGameState().equals(GameState.STARTING)) {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.State.Starting"));
                    } else if (arena.getGameState().equals(GameState.INGAME)) {
                        if (ArenaDataGetters.allowLateJoin(id)) {
                            LateJoinArena(player, id);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.State.InGame"));
                        }
                    } else if (arena.getGameState().equals(GameState.REGENERATING)) {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.State.Regenerating"));
                    } else {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Error"));
                    }
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NotJoinable"));
                }
            } else {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Player.InGame"));
        }
    }
    public static void LateJoinArena(Player player, int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (arena.getPlayerCount() < arena.getMaxPlayers()) {
            if (inventoryEmpty(player)) {
                arena.setPlayerCount(arena.getPlayerCount() + 1);
                arena.getPlayers().add(player.getUniqueId());
                GameManager.determinePlayerRoles(id, player);

                GameMessages.announcePlayerJoin(id, player);

                GameManager.startGame(id, player);
            } else {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Player.InvHasItem"));
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NotJoinable"));
        }
    }
    public static void quitArena(Player player) {
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            player.setExhaustion(20f);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setFireTicks(0);
            player.teleport(ArenaDataGetters.getQuitPosition(arena.getId()));
            GameBoardManager.removePlayer(player, arena.getId(), "Player");
            arena.getPlayers().remove(player.getUniqueId());
            arena.setPlayerCount(arena.getPlayerCount() - 1);
            GameMessages.announcePlayerQuit(arena.getId(), player);
            ArenaManager.getManager().checkPlayerCount(arena.getId());
        } else if (ArenaManager.getManager().isSpectating(player)) {
            Arena arena = ArenaManager.getManager().getSpectatingArena(player);
            arena.getSpectators().remove(player.getUniqueId());
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(ArenaDataGetters.getQuitPosition(arena.getId()));
            GameBoardManager.removePlayer(player, arena.getId(), "Spectator");
            String msgOr = MessageManager.getMessageYml().getString("Player.Spectate.Stop");
            String msg = msgOr.replaceAll("%id%", String.valueOf(arena.getId()));
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Player.NotInGame"));
        }
    }
    public static void spectateArena(Player player, int id) {
        if (!(ArenaManager.getManager().isInGame(player))) {
            if (!(ArenaManager.getManager().isSpectating(player))) {
                if (ArenaManager.getManager().arenaExists(id)) {
                    Arena arena = ArenaManager.getManager().getArena(id);
                    if (arena.getGameState().equals(GameState.INGAME)) {
                        player.teleport(ArenaDataGetters.getSpectatePosition(id));
                        arena.getSpectators().add(player.getUniqueId());
                        player.setGameMode(GameMode.SPECTATOR);
                        GameBoardManager.addPlayer(player, id, "Spectator");
                        String msgOr = MessageManager.getMessageYml().getString("Player.Spectate.Spectate");
                        String msg = msgOr.replaceAll("%id%", String.valueOf(id));
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                        GameMessages.announcePlayerSpectating(id, player);
                    } else {
                        String msgOr = MessageManager.getMessageYml().getString("Player.Spectate.Cannot");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    }
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                }
            } else {
                String msgOr = MessageManager.getMessageYml().getString("Player.Spectate.Already");
                player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
            }
        } else {
            String msgOr = MessageManager.getMessageYml().getString("Player.Spectate.InGame");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }

    public static void removeAllPlayers(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.teleport(ArenaDataGetters.getQuitPosition(id));
        }
    }

    public static void giveKit(Player player, ArrayList<ItemStack> kit) {
        Integer itemSlot = 0;
        for (ItemStack item : kit) {
            player.getInventory().setItem(itemSlot, item);
            itemSlot++;
        }
    }

    public static boolean inventoryEmpty(Player player) {
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (!(itemStack == null)) {
                if (!(itemStack.getType().equals(Material.AIR))) {
                    return false;
                }
            }
        }
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (!(itemStack == null)) {
                if (!(itemStack.getType().equals(Material.AIR))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void sendArenaInfo(Player player, int id) {
        if (ArenaFileManager.arenaExists(id)) {
            GameState state = GameState.EMPTY;
            String players = "";
            Integer playerCount = 0;
            Boolean enabled = false;
            Boolean lateJoinAllowed = ArenaDataGetters.allowLateJoin(id);
            Integer max = ArenaDataGetters.getMaxPlayers(id);
            if (ArenaManager.getManager().arenaExists(id)) {
                Arena arena = ArenaManager.getManager().getArena(id);
                playerCount = arena.getPlayerCount();
                if (arena.getPlayerCount() > 0) {
                    for (UUID uuid : arena.getPlayers()) {
                        Player p = Bukkit.getPlayer(uuid);
                        players = players + p.getDisplayName() + ", " + ChatColor.RESET;
                    }
                }
                state = arena.getGameState();
                enabled = true;
            }
            String headingOr = MessageManager.getMessageYml().getString("Sign.Info.Heading");
            String heading = headingOr.replaceAll("%id%", String.valueOf(id));
            String statusString = String.valueOf(state).replaceAll("_", " ").toLowerCase();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', heading));
            player.sendMessage(ChatColor.GREEN + "Enabled: " + ChatColor.RED + String.valueOf(enabled));
            player.sendMessage(ChatColor.GREEN + "Status: " + ChatColor.RED + statusString);
            player.sendMessage(ChatColor.GREEN + "LateJoin Allowed: " + ChatColor.RED + lateJoinAllowed);
            player.sendMessage(ChatColor.GREEN + "Player Count: " + ChatColor.RED + String.valueOf(playerCount) + "/" + String.valueOf(max));
            player.sendMessage(ChatColor.GREEN + "Players: " + ChatColor.RED + players);
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
        }
    }
}
