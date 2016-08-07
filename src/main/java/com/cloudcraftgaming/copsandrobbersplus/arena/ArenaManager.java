package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import com.cloudcraftgaming.copsandrobbersplus.arena.GameManager.WinType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class ArenaManager {
    private static ArenaManager instance;

    private List<Arena> arenas = new ArrayList<>();

    private ArenaManager() {} //Prevent initialization.
    public static ArenaManager getManager() {
        if (instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }
    //Arena loading and unloading methods.
    public void loadArena(int id) {
        if (!this.arenaExists(id)) {
            if (ArenaFileManager.arenaExists(id)) {
                int minPlayers = ArenaDataGetters.getMinPlayers(id);
                int maxPlayers = ArenaDataGetters.getMaxPlayers(id);

                Arena arena = new Arena(id, minPlayers, maxPlayers);
                arena.setPlayerCount(0);
                arena.setWaitId(0);
                arena.setStartId(0);
                arena.setGameId(0);
                arena.setWinType(WinType.NONE);
                arena.setGameState(GameState.EMPTY);
                arena.setJoinable(true);

                arenas.add(arena);

                if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                    Main.plugin.getLogger().info("Loaded arena Id: " + String.valueOf(id));
                }
            }
        }
    }
    //Arena loading methods
    public void unloadArena(int id) {
        if (this.arenaExists(id)) {
            Arena a = getArena(id);
            arenas.remove(a);

            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Unloaded arena Id: " + String.valueOf(id));
            }
        }
    }
    public void reloadArena(int id) {
        if (this.arenaExists(id)) {
            this.unloadArena(id);
            this.loadArena(id);
        }
    }

    public void safeLoadArena(int id) {
        if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Safe loading Arena Id: " + String.valueOf(id));
        }
        if (ArenaFileManager.canLoadArena(id)) {
            loadArena(id);
            Regenerator.regenArena(id);
        } else {
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Failed to load arena Id: " + String.valueOf(id));
            }
        }
    }
    public void safeUnloadArena(int id) {
        if (this.arenaExists(id)) {
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Safe unloading Arena Id: " + String.valueOf(id));
            }
            Arena arena = this.getArena(id);
            if (arena.getGameState().equals(GameState.INGAME)) {
                GameManager.endGame(id);
            } else if (arena.getPlayerCount() > 0) {
                PlayerHandler.removeAllPlayers(id);
                Regenerator.regenArena(id);
            }
            this.unloadArena(id);
        }
    }
    public void safeReloadArena(int id) {
        if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Safe reloading arena Id: " + String.valueOf(id));
        }
        if (this.arenaExists(id)) {
            safeUnloadArena(id);
            loadArena(id);
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Successfully safe reloaded arena Id: " + String.valueOf(id));
            }
        } else {
            Main.plugin.getLogger().info("Safe reload failed! Arena is not loaded!");
        }
    }

    //Arena related methods
    public Boolean arenaExists(int i) {
        for (Arena a : arenas) {
            if (a.getId() == i) {
                return true;
            }
        }
        return false;
    }
    public Arena getArena(int i) {
        for (Arena a : arenas) {
            if (a.getId() == i) {
                return a;
            }
        }
        return null;
    }
    public Arena getArena(UUID uuid) {
        for (Arena a : arenas) {
            if (a.getPlayers().contains(uuid) || a.getSpectators().contains(uuid)) {
                return a;
            }
        }
        return null;
    }
    public Arena getArena(Player player) {
        return getArena(player.getUniqueId());
    }
    public Arena getSpectatingArena(UUID uuid) {
        for (Arena a : arenas) {
            if (a.getSpectators().contains(uuid)) {
                return a;
            }
        }
        return null;
    }
    public Arena getSpectatingArena(Player player) {
        return getSpectatingArena(player.getUniqueId());
    }


    //Player related methods
    public Boolean isInGame(UUID uuid) {
        for (Arena a : arenas) {
            if (a.getPlayers().contains(uuid)) {
                return true;
            }
        }
        return false;
    }
    public Boolean isInGame(Player p) {
        return isInGame(p.getUniqueId());
    }
    public Boolean isSpectating(UUID uuid) {
        for (Arena a : arenas) {
            if (a.getSpectators().contains(uuid)) {
                return true;
            }
        }
        return false;
    }
    public Boolean isSpectating(Player p) {
        return isSpectating(p.getUniqueId());
    }

    //Other functional methods.
    public void checkPlayerCount(int id) {
        Arena arena = this.getArena(id);

        //Check player count to see what actions to take.
        if (arena.getGameState().equals(GameState.EMPTY)) {
            if (arena.getPlayerCount() > 0) {
                arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                //Call again to check if more should be done.
                this.checkPlayerCount(id);
            }
        } else if (arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS)) {
            if (arena.getPlayerCount() <= 0) {
                //Reset arena.
                Regenerator.regenArena(id);
            } else if (arena.getPlayerCount() < arena.getMinPlayers()) {
                if (arena.getWaitId() != 0) {
                    //Not enough players... Cancel wait delay
                    TimeManager.getManager().cancelWaitDelay(id);
                }
            } else if (arena.getPlayerCount() >= arena.getMinPlayers()) {
                arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                if (arena.getWaitId() == 0) {
                    //Start wait countdown.
                    TimeManager.getManager().startWaitDelay(id);
                }
            }
        } else if (arena.getGameState().equals(GameState.STARTING)) {
            if (arena.getPlayerCount() <= 0) {
                //Reset arena
                Regenerator.regenArena(id);
            } else if (arena.getPlayerCount() < arena.getMinPlayers()) {
                arena.setGameState(GameState.WAITING_FOR_PLAYERS);
                //Not enough players, cancel start.
                TimeManager.getManager().cancelStartDelay(id);
            }
        } else if (arena.getGameState().equals(GameState.INGAME)) {
            if (arena.getPlayerCount() <= 1) {
                //One player left, declare winner, end game.
                GameManager.endGame(id);
            } else if (arena.getPlayerCount() < arena.getMinPlayers()) {
                //Not enough players left, declare winner, end game.
                GameManager.endGame(id);
            }
        }
    }
}