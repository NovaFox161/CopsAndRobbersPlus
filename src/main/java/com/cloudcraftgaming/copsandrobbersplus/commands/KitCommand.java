package com.cloudcraftgaming.copsandrobbersplus.commands;

import com.cloudcraftgaming.copsandrobbersplus.setters.KitSetter;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 5/9/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class KitCommand {
    public static void kitCommand(Player player, String[] args) {
        if (args.length == 1) {
            //car kit
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
        } else if (args.length == 2) {
            //car kit <function>
            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
        } else if (args.length == 3) {
            //car kit <function> <KitName>
            String function = args[1];
            String kitName = args[2];
            if (function.equalsIgnoreCase("create")) {
                if (player.hasPermission("CARP.use.command.set")) {
                    KitSetter.setKit(player, kitName);
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
                }
            } else if (function.equalsIgnoreCase("delete")) {
                if (player.hasPermission("CARP.use.command.set")) {
                    KitSetter.removeKit(player, kitName);
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
                }
            }
        }
    }
}
