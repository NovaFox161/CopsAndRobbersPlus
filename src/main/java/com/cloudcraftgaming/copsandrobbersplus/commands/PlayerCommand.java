package com.cloudcraftgaming.copsandrobbersplus.commands;

import com.cloudcraftgaming.copsandrobbersplus.arena.PlayerHandler;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class PlayerCommand {
    public static void playerCommand(Player player, String[] args) {
        if (args.length == 1) {
            //car <type>
            String type = args[0];
            if (type.equalsIgnoreCase("join")) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (type.equalsIgnoreCase("quit")) {
                PlayerHandler.quitArena(player);
            } else if (type.equalsIgnoreCase("Spectate")) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else if (type.equalsIgnoreCase("info")) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
            } else {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
            }
        } else if (args.length == 2) {
            //car <type> <id>
            String type = args[0];
            String idString = args[1];
            try {
                Integer  id = Integer.valueOf(idString);
                if (type.equalsIgnoreCase("join")) {
                    PlayerHandler.joinArena(player, id);
                } else if (type.equalsIgnoreCase("spectate")) {
                    PlayerHandler.spectateArena(player, id);
                } else if (type.equalsIgnoreCase("info")) {
                    PlayerHandler.sendArenaInfo(player, id);
                } else if (type.equalsIgnoreCase("quit")) {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                }
            } catch (NumberFormatException e) {
                player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Int.Arena"));
            }
        } else if (args.length > 2) {
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
        }
    }
}
