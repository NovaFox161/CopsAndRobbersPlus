package com.cloudcraftgaming.copsandrobbersplus.commands;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaFileManager;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.FileManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class AdminCommand {
    public static void adminCommand(Player player, String[] args) {
        if (player.hasPermission("CARP.use.command.admin") || player.hasPermission("CARP.use.command.set")) {
            if (args.length == 1) {
                //car <type>
                String type = args[0];
                if (type.equalsIgnoreCase("set")) {
                    SetCommand.setCommand(player, args);
                } else if (type.equalsIgnoreCase("create") || type.equalsIgnoreCase("createArena")) {
                    ArenaFileManager.createArena(player);
                } else if (type.equalsIgnoreCase("tool") || type.equalsIgnoreCase("arenaTool")) {
                    arenaTool(player);
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                }
            } else if (args.length == 2) {
                //car <type> <id>
                String type = args[0];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (type.equalsIgnoreCase("enable") || type.equalsIgnoreCase("enableArena")) {
                        if (ArenaFileManager.canLoadArena(id)) {
                            ArenaFileManager.enableArena(id);
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Enable", id));
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Enable.Cannot"));
                        }
                    } else if (type.equalsIgnoreCase("disable") || type.equalsIgnoreCase("disableArena")) {
                        ArenaFileManager.disableArena(id);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Set.Disable", id));
                    } else if (type.equalsIgnoreCase("reload") || type.equalsIgnoreCase("reloadArena")) {
                        ArenaManager.getManager().safeReloadArena(id);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.ReloadArena", id));
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
                }
            }
        } else {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
    private static void arenaTool(Player player) {
        YamlConfiguration playerCache = FileManager.getPlayerCacheYml();
        UUID uuid = player.getUniqueId();
        if (playerCache.getString("Players." + uuid + ".ArenaTool.Enabled").equalsIgnoreCase("True")) {
            playerCache.set("Players." + uuid + ".ArenaTool.Enabled", false);
            Main.plugin.saveCustomConfig(playerCache, FileManager.getPlayerCacheFile());
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Tool.Disable"));
        } else {
            playerCache.set("Players." + uuid + ".ArenaTool.Enabled", true);
            Main.plugin.saveCustomConfig(playerCache, FileManager.getPlayerCacheFile());
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Tool.Enable"));
        }
    }
}
