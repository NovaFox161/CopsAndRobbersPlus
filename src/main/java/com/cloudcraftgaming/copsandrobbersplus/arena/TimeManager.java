package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.Bukkit;

import java.util.Random;

/**
 * Created by Nova Fox on 5/7/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class TimeManager {
    private static TimeManager instance;

    private TimeManager() {} //Prevent initialization

    public static TimeManager getManager() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }

    public void startWaitDelay(final int id) {
        if (ArenaManager.getManager().arenaExists(id))  {
            Arena arena = ArenaManager.getManager().getArena(id);
            Random rn = new Random();
            final Integer waitId = rn.nextInt(99999998) + 1;
            arena.setWaitId(waitId);
            arena.setJoinable(true);
            arena.setGameState(GameState.WAITING_FOR_PLAYERS);
            Integer waitDelay = ArenaDataGetters.getWaitDelay(id);
            GameMessages.announceWaitDelay(id);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (ArenaManager.getManager().arenaExists(id)) {
                        Arena arena = ArenaManager.getManager().getArena(id);
                        if (arena.getWaitId() == waitId) {
                            startStartDelay(id);
                        }
                    }
                }
            }, 20L * waitDelay);
        }
    }
    public void cancelWaitDelay(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.setWaitId(0);
            arena.setJoinable(true);
            arena.setGameState(GameState.WAITING_FOR_PLAYERS);
            GameMessages.announceWaitCancel(id);
        }
    }

    public void startStartDelay(final int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            Random rn = new Random();
            final Integer startId = rn.nextInt(99999998) + 1;
            arena.setStartId(startId);
            arena.setJoinable(false);
            arena.setGameState(GameState.STARTING);
            Integer startDelay = ArenaDataGetters.getStartDelay(id);
            GameMessages.announceStartDelay(id);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (ArenaManager.getManager().arenaExists(id)) {
                        Arena arena = ArenaManager.getManager().getArena(id);
                        if (arena.getStartId() == startId) {
                            GameManager.startGame(id);
                        }
                    }
                }
            }, 20L * startDelay);
        }
    }
    public void cancelStartDelay(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.setStartId(0);
            arena.setJoinable(true);
            arena.setGameState(GameState.WAITING_FOR_PLAYERS);
            GameMessages.announceStartCancel(id);
        }
    }

    public void startGameTimer(final int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            Random rn = new Random();
            final Integer gameId = rn.nextInt(99999998) + 1;
            arena.setGameId(gameId);
            Integer gameLength = ArenaDataGetters.getGameLength(id);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (ArenaManager.getManager().arenaExists(id)) {
                        Arena arena = ArenaManager.getManager().getArena(id);
                        if (arena.getGameId() == gameId) {
                            GameManager.endGame(id);
                        }
                    }
                }
            }, 20L * 60 * gameLength);
        }
    }
}