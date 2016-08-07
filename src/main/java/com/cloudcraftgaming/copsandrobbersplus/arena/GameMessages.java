package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Nova Fox on 5/7/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class GameMessages {
    public static void announcePlayerJoin(int id, Player player) {
        String msgOr = MessageManager.getMessageYml().getString("Arena.PlayerJoin");
        String msgRep = msgOr.replaceAll("%player%", player.getDisplayName());
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgRep));
        }
    }
    public static void announcePlayerQuit(int id, Player player) {
        String msgOr = MessageManager.getMessageYml().getString("Arena.PlayerQuit");
        String msgRep = msgOr.replaceAll("%player%", player.getDisplayName());
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgRep));
        }
        for (UUID uuid : arena.getSpectators()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgRep));
        }
    }
    public static void announcePlayerSpectating(int id, Player player) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessageYml().getString("Arena.Spectating");
        String msg = msgOr.replaceAll("%player%", player.getDisplayName());
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
        for (UUID uuid : arena.getSpectators()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void announceWaitDelay(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Integer waitDelay = ArenaDataGetters.getWaitDelay(id);
        String msgOr = MessageManager.getMessageYml().getString("Arena.Waiting");
        String msgRep = msgOr.replace("%time%", String.valueOf(waitDelay));
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgRep));
        }
    }
    public static void announceWaitCancel(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.WaitCancelled"));
        }
    }
    public static void announceStartDelay(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        Integer startDelay = ArenaDataGetters.getStartDelay(id);
        String msgOr = MessageManager.getMessageYml().getString("Arena.Starting");
        String msgRep = msgOr.replaceAll("%time%", String.valueOf(startDelay));
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgRep));
        }
    }
    public static void announceStartCancel(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Arena.StartCancelled"));
        }
    }

    public static void announceGameStart(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msgOr = MessageManager.getMessageYml().getString("Arena.GameStart");
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
        for (UUID uuid : arena.getSpectators()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
        }
    }
    public static void announceTeamToPlayer(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String copMsg = MessageManager.getMessage("Arena.Start.Team.Cop");
        String robberMsg = MessageManager.getMessage("Arena.Start.Team.Robber");

        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            if (arena.getCops().contains(uuid)) {
                p.sendMessage(MessageManager.getPrefix() + copMsg);
            } else {
                p.sendMessage(MessageManager.getPrefix() + robberMsg);
            }
        }
    }
    public static void announceTeamToPlayer(int id, Player player) {
        Arena arena = ArenaManager.getManager().getArena(id);

        if (arena.getCops().contains(player.getUniqueId())) {
            String copMsg = MessageManager.getMessage("Arena.Start.Team.Cop");
            player.sendMessage(MessageManager.getPrefix() + copMsg);

        } else {
            String robberMsg = MessageManager.getMessage("Arena.Start.Team.Robber");
            player.sendMessage(MessageManager.getPrefix() + robberMsg);
        }
    }

    public static void announceWinningTeam(int id) {
        Arena arena = ArenaManager.getManager().getArena(id);
        String msg = "";
        if (arena.getWinType().equals(GameManager.WinType.COPS)) {
            String msgOr = MessageManager.getMessageYml().getString("Arena.Win.Cops");
            msg = ChatColor.translateAlternateColorCodes('&', msgOr);
        } else if (arena.getWinType().equals(GameManager.WinType.ROBBERS)) {
            String msgOr = MessageManager.getMessageYml().getString("Arena.Win.Robbers");
            msg = ChatColor.translateAlternateColorCodes('&', msgOr);
        } else if (arena.getWinType().equals(GameManager.WinType.NONE)) {
            String msgOr = MessageManager.getMessageYml().getString("Arena.Win.None");
            msg = ChatColor.translateAlternateColorCodes('&', msgOr);
        }
        for (UUID uuid : arena.getPlayers()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + msg);
        }
        for (UUID uuid : arena.getSpectators()) {
            Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(MessageManager.getPrefix() + msg);
        }
        if (Main.plugin.getConfig().getString("Game.Announce.End").equalsIgnoreCase("True")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(MessageManager.getPrefix() + msg);
            }
        }
    }
}