package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.getters.KitGetter;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameBoardManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class GameManager {
    public static void startGame(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);

            arena.setGameState(GameState.INGAME);
            arena.setJoinable(false);
            arena.setWinType(WinType.NONE);

            determinePlayerRoles(id);

            GameBoardManager.createScoreboard(id);
            GameBoardManager.addPlayers(id);

            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                if (arena.getCops().contains(uuid)) {
                    p.teleport(ArenaDataGetters.getRandomCopSpawn(id));
                } else {
                    p.teleport(ArenaDataGetters.getRandomPrisonerSpawn(id));
                }
                p.setGameMode(GameMode.SURVIVAL);
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
                p.getInventory().clear();
                p.setExp(0);
                p.setLevel(0);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setExhaustion(20);
                p.setFireTicks(0);

                if (ArenaDataGetters.useKits(id)) {
                    if (arena.getCops().contains(uuid)) {
                        PlayerHandler.giveKit(p, KitGetter.getKit(ArenaDataGetters.getDefaultCopKitName(id)));
                    } else {
                        PlayerHandler.giveKit(p, KitGetter.getKit(ArenaDataGetters.getDefaultRobberKitName(id)));
                    }
                }
            }

            GameBoardManager.updateBoards(id);
            TimeManager.getManager().startGameTimer(id);
            GameMessages.announceGameStart(id);
            GameMessages.announceTeamToPlayer(id);
        }
    }
    public static void startGame(int id, Player player) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);

            GameBoardManager.addPlayer(player, id, "Player");

            if (arena.getCops().contains(player.getUniqueId())) {
                player.teleport(ArenaDataGetters.getRandomCopSpawn(id));
            } else {
                player.teleport(ArenaDataGetters.getRandomPrisonerSpawn(id));
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            player.getInventory().clear();
            player.setExp(0);
            player.setLevel(0);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setExhaustion(20);
            player.setFireTicks(0);

            if (ArenaDataGetters.useKits(id)) {
                if (arena.getCops().contains(player.getUniqueId())) {
                    PlayerHandler.giveKit(player, KitGetter.getKit(ArenaDataGetters.getDefaultCopKitName(id)));
                } else {
                    PlayerHandler.giveKit(player, KitGetter.getKit(ArenaDataGetters.getDefaultRobberKitName(id)));
                }
            }

            GameBoardManager.updateBoards(id);
            GameMessages.announceTeamToPlayer(id, player);
        }
    }
    public static void endGame(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            if (arena.getWinType().equals(WinType.NONE)) {
                determineWinningTeam(id);
            }

            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                p.setExhaustion(20f);
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
                p.getInventory().clear();
                p.setFoodLevel(20);
                p.setHealth(20);
                p.setFireTicks(0);
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(ArenaDataGetters.getEndPosition(id));
            }
            for (UUID uuid : arena.getSpectators()) {
                Player p = Bukkit.getPlayer(uuid);
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(ArenaDataGetters.getQuitPosition(id));
            }
            GameBoardManager.removePlayers(id);

            GameMessages.announceWinningTeam(id);

            arena.getPlayers().clear();
            arena.setPlayerCount(0);
            Regenerator.regenArena(id);
        }
    }

    private static void determinePlayerRoles(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);

            List<UUID> players = arena.getPlayers();
            Collections.shuffle(players);

            int maxCops = ArenaDataGetters.getMaxCops(id);

            for (UUID uuid : players) {
                if (arena.getCops().size() < maxCops && (arena.getCops().size() + 1) < (arena.getPlayerCount())) {
                    if (arena.getCops().contains(uuid)) {
                        continue;
                    }
                    arena.getCops().add(uuid);
                } else {
                    if (!arena.getCops().contains(uuid)) {
                        arena.getPrisoners().add(uuid);
                    }
                }
            }
        }
    }
    public static void determinePlayerRoles(int id, Player player) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);

            int maxCops = ArenaDataGetters.getMaxCops(id);
            int currentCopAmount = arena.getCops().size();

            if (currentCopAmount < maxCops && (arena.getCops().size() + 1) < (arena.getPlayerCount())) {
                arena.getCops().add(player.getUniqueId());
            } else {
                arena.getPrisoners().add(player.getUniqueId());
            }
        }
    }

    private static void determineWinningTeam(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (arena.getCops().size() > 0) {
            arena.setWinType(WinType.COPS);
        } else {
            arena.setWinType(WinType.ROBBERS);
        }
    }
    public static void determineWinningTeam(int id, Player winner) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (arena.getCops().contains(winner.getUniqueId())) {
            arena.setWinType(WinType.COPS);
        } else {
            arena.setWinType(WinType.ROBBERS);
        }
        endGame(id);
    }


    public enum WinType {
        COPS, ROBBERS, NONE
    }
}