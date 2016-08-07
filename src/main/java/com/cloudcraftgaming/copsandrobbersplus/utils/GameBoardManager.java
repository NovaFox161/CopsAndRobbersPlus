package com.cloudcraftgaming.copsandrobbersplus.utils;

import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class GameBoardManager {
    private static org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();

    public static void createScoreboard(int id) {
        Scoreboard board = manager.getNewScoreboard();
        Objective boardObj = board.registerNewObjective("Board", "dummy");
        boardObj.setDisplayName(ArenaDataGetters.getDisplayName(id));
        boardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team cops = board.registerNewTeam("cops");
        cops.setDisplayName(ChatColor.DARK_BLUE + "Cops");
        cops.setAllowFriendlyFire(true);
        Team robbers = board.registerNewTeam("robbers");
        robbers.setDisplayName(ChatColor.DARK_RED + "Robbers");
        robbers.setAllowFriendlyFire(true);
        Team spectators = board.registerNewTeam("spectators");
        spectators.setDisplayName(ChatColor.GOLD + "Spectators");
        spectators.setAllowFriendlyFire(false);
        Score specScoreFake = boardObj.getScore(ChatColor.GOLD + "Spectators");
        specScoreFake.setScore(600);
        Score copScoreFake = boardObj.getScore(ChatColor.BLUE + "Cops");
        copScoreFake.setScore(400);
        Score robScoreFake = boardObj.getScore(ChatColor.RED + "Robbers");
        robScoreFake.setScore(300);
        Arena arena = ArenaManager.getManager().getArena(id);
        arena.setScoreboard(board);
    }
    public static void addPlayers(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        if (!(arena.getScoreboard() == null)) {
            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                Scoreboard gameBoard = arena.getScoreboard();
                Objective object = gameBoard.getObjective("Board");
                if (arena.getCops().contains(uuid)) {
                    Team team = gameBoard.getTeam("cops");
                    team.addPlayer(p);
                    Score pScore = object.getScore(ChatColor.BLUE + p.getName());
                    pScore.setScore(399);
                } else {
                    Team team = gameBoard.getTeam("robbers");
                    team.addPlayer(p);
                    Score pScore = object.getScore(ChatColor.RED + p.getName());
                    pScore.setScore(299);
                }
                arena.setScoreboard(gameBoard);
            }
            for (UUID uuid : arena.getSpectators()) {
                Player p = Bukkit.getPlayer(uuid);
                Scoreboard gameBoard = arena.getScoreboard();
                Objective object = gameBoard.getObjective("Board");
                Team team = gameBoard.getTeam("spectators");
                team.addPlayer(p);
                Score pScore = object.getScore(ChatColor.GOLD + p.getName());
                pScore.setScore(599);
                arena.setScoreboard(gameBoard);
            }
        }
    }
    public static void removePlayers(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            player.setScoreboard(manager.getNewScoreboard());
        }
        for (UUID uuid : arena.getSpectators()) {
            Player player = Bukkit.getPlayer(uuid);
            player.setScoreboard(manager.getNewScoreboard());
        }
    }
    public static void updateBoards(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            if (!(arena.getScoreboard() == null)) {
                Player p = Bukkit.getPlayer(uuid);
                p.setScoreboard(arena.getScoreboard());
            }
        }
        for (UUID uuid : arena.getSpectators()) {
            if (!(arena.getScoreboard() == null)) {
                Player p = Bukkit.getPlayer(uuid);
                p.setScoreboard(arena.getScoreboard());
            }
        }
    }
    public static void addPlayer(Player player, int id, String type) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Scoreboard board = arena.getScoreboard();
        if (!(board == null)) {
            Objective object = board.getObjective("Board");
            if (type.equalsIgnoreCase("Player")) {
                if (arena.getCops().contains(player.getUniqueId())) {
                    Team team = board.getTeam("cops");
                    team.addPlayer(player);
                    Score pScore = object.getScore(ChatColor.BLUE + player.getName());
                    pScore.setScore(399);
                    arena.setScoreboard(board);
                } else {
                    Team team = board.getTeam("robbers");
                    team.addPlayer(player);
                    Score pScore = object.getScore(ChatColor.RED + player.getName());
                    pScore.setScore(299);
                    arena.setScoreboard(board);
                }
            } else if (type.equalsIgnoreCase("Spectator")) {
                Team team = board.getTeam("spectators");
                team.addPlayer(player);
                Score pScore = object.getScore(ChatColor.GOLD + player.getName());
                pScore.setScore(599);
                arena.setScoreboard(board);
            }
            updateBoards(id);
        }
    }
    public static void removePlayer(Player player, int id, String type) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Scoreboard board = arena.getScoreboard();
        if (!(board == null)) {
            if (type.equalsIgnoreCase("Player")) {
                if (arena.getCops().contains(player.getUniqueId())) {
                    board.resetScores(ChatColor.RED + player.getName());
                    Team team = board.getTeam("cops");
                    team.removePlayer(player);
                    arena.setScoreboard(board);
                } else {
                    board.resetScores(ChatColor.BLUE + player.getName());
                    Team team = board.getTeam("robbers");
                    team.removePlayer(player);
                    arena.setScoreboard(board);
                }
            } else if (type.equalsIgnoreCase("Spectator")) {
                board.resetScores(ChatColor.GOLD + player.getName());
                Team team = board.getTeam("spectators");
                team.removePlayer(player);
                arena.setScoreboard(board);
            }
            updateBoards(id);
        }
        player.setScoreboard(manager.getNewScoreboard());
    }
}
