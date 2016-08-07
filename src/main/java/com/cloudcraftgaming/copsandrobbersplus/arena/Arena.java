package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import com.cloudcraftgaming.copsandrobbersplus.arena.GameManager.WinType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class Arena {
    private final int id;
    private final int minPlayers;
    private final int maxPlayers;

    private final List<UUID> players = new ArrayList<>();
    private final List<UUID> spectators = new ArrayList<>();

    private GameState gameState;
    private int playerCount;
    private final List<UUID> prisoners = new ArrayList<>();
    private final List<UUID> cops = new ArrayList<>();

    private Scoreboard scoreboard;

    private Boolean joinable;

    private WinType winType;

    private int waitId;
    private int startId;
    private int gameId;


    public Arena(int _id, int _minPlayers, int _maxPlayers) {
        this.id = _id;
        this.minPlayers = _minPlayers;
        this.maxPlayers = _maxPlayers;
    }

    //Setters
    public void setGameState(GameState _gameState) {
        this.gameState = _gameState;
    }
    public void setPlayerCount(int _playerCount) {
        this.playerCount = _playerCount;
    }

    public void setScoreboard(Scoreboard _scoreboard) {
        this.scoreboard = _scoreboard;
    }

    public void setJoinable(Boolean _joinable) {
        this.joinable = _joinable;
    }

    public void setWinType(WinType _winType) {
        this.winType = _winType;
    }

    public void setWaitId(int _waitId) {
        this.waitId = _waitId;
    }
    public void setStartId(int _startId) {
        this.startId = _startId;
    }
    public void setGameId(int _gameId) {
        this.gameId = _gameId;
    }


    //Getters
    public int getId() {
        return this.id;
    }
    public List<UUID> getPlayers() {
        return this.players;
    }
    public List<UUID> getSpectators() {
        return this.spectators;
    }
    public int getMinPlayers() {
        return this.minPlayers;
    }
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    public GameState getGameState() {
        return this.gameState;
    }
    public int getPlayerCount() {
        return this.playerCount;
    }
    public List<UUID> getPrisoners() {
        return this.prisoners;
    }
    public List<UUID> getCops() {
        return this.cops;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Boolean isJoinable() {
        return this.joinable;
    }

    public WinType getWinType() {
        return this.winType;
    }

    public int getWaitId() {
        return this.waitId;
    }
    public int getStartId() {
        return this.startId;
    }
    public int getGameId() {
        return this.gameId;
    }


}
