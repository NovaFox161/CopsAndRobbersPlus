package com.cloudcraftgaming.copsandrobbersplus.commands;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class BaseCommand implements CommandExecutor {
    Main plugin;
    public BaseCommand(Main instance) {
        plugin = instance;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("CopsAndRobbers") || cmd.getName().equalsIgnoreCase("car") || cmd.getName().equalsIgnoreCase("carp")) {
            if (sender.hasPermission("CARP.use.command")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length < 1) {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Few"));
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("createArena")
                                || args[0].equalsIgnoreCase("tool") || args[0].equalsIgnoreCase("ArenaTool")) {
                            //send to admin command.
                            AdminCommand.adminCommand(player, args);
                        } else if (args[0].equalsIgnoreCase("kit")) {
                            //Send to kit command
                            KitCommand.kitCommand(player, args);
                        } else {
                            PlayerCommand.playerCommand(player, args);
                        }
                    } else if (args.length == 2) {
                        String type = args[0];
                        if (type.equalsIgnoreCase("set")) {
                            //Send to set command
                            SetCommand.setCommand(player, args);
                        } else if (type.equalsIgnoreCase("enable") || type.equalsIgnoreCase("enableArena")
                                || type.equalsIgnoreCase("disable") || type.equalsIgnoreCase("disableArena")
                                || type.equalsIgnoreCase("reload") || type.equalsIgnoreCase("reloadArena")) {
                            AdminCommand.adminCommand(player, args);
                        } else if (type.equalsIgnoreCase("kit")) {
                            KitCommand.kitCommand(player, args);
                        } else {
                            PlayerCommand.playerCommand(player, args);
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("set")) {
                            SetCommand.setCommand(player, args);
                        } else if (args[0].equalsIgnoreCase("kit")) {
                            KitCommand.kitCommand(player, args);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Invalid"));
                        }
                    } else if (args.length == 4) {
                        if (args[0].equalsIgnoreCase("set")) {
                            SetCommand.setCommand(player, args);
                        } else {
                            player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
                        }
                    } else if (args.length > 4) {
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Args.Many"));
                    }
                } else {
                    sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.PlayerOnly"));
                }
            } else {
                sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.NoPerm"));
            }
        }
        return false;
    }
}
