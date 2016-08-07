package com.cloudcraftgaming.copsandrobbersplus.utils;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Nova Fox on 5/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class MessageManager {
    protected static Double messageVersion = 1.1;

    public static void createMessageFiles() {
        createEnglishMessageFile();
    }
    private static void createEnglishMessageFile() {
        File enFile = new File(Main.plugin.getDataFolder() + "/Messages/English.yml");
        if (!enFile.exists()) {
            Main.plugin.getLogger().info("Generating English message file...");
            YamlConfiguration en = YamlConfiguration.loadConfiguration(enFile);

            en.addDefault("DO NOT DELETE", "CopsAndRobbersPlus is developed and managed by Shades161");
            en.addDefault("Messages Version", messageVersion);

            en.addDefault("Prefix", "&5[C'nR]");

            en.addDefault("Player.InGame", "&4You can only be in one game at a time!");
            en.addDefault("Player.NotInGame", "&4You cannot quit a minigame if you aren't in one!");
            en.addDefault("Player.InvHasItem", "&4Your inventory must be empty to join a game!");
            en.addDefault("Player.Spectate.Spectate", "&2You are now spectating Arena %id%! &4 Use /car quit to quit");
            en.addDefault("Player.Spectate.Cannot", "&4You cannot spectate that arena right now!");
            en.addDefault("Player.Spectate.Already", "&4You are already spectating a minigame!");
            en.addDefault("Player.Spectate.InGame", "&4You cannot spectate a minigame while you are playing one!");
            en.addDefault("Player.Rules.Block.Break", "&4You cannot break blocks while in a minigame!");
            en.addDefault("Player.Rules.Block.Place", "&4You cannot place blocks while in a minigame!");

            en.addDefault("Arena.PlayerJoin", "&2%player% &6has joined the minigame!");
            en.addDefault("Arena.PlayerQuit", "&2%player% &4has quit the minigame!");
            en.addDefault("Arena.Spectating", "&4%player% &6is now spectating the minigame!");
            en.addDefault("Arena.Waiting", "&3Waiting for more players! &6Time: %time% seconds&3!");
            en.addDefault("Arena.WaitCancelled", "&4Not enough players in lobby, wait time cancelled!");
            en.addDefault("Arena.Starting", "&3Starting minigame in: &6%time% seconds&3!");
            en.addDefault("Arena.StartCancelled", "&4Not enough players in lobby! Minigame start cancelled!");
            en.addDefault("Arena.GameStart", "&2Game Started! &4Robbers: Escape! &6Cops: Catch the robbers!");
            en.addDefault("Arena.State.Starting", "&4A game in that arena is already starting! Try joining a different arena!");
            en.addDefault("Arena.State.InGame", "&4A game in that arena has already started!");
            en.addDefault("Arena.State.Regenerating", "&4That arena is currently regenerating! Try joining again in a few moments!");
            en.addDefault("Arena.Start.Team.Cop", "&1You are a &6COP &1Do not let the &4ROBBERS escape!");
            en.addDefault("Arena.Start.Team.Robber", "&1You are a &6ROBBER &1Escape the prison and do not let the &4COPS &1catch you!");
            en.addDefault("Arena.Robber.Caught", "&4You got caught! You are now in a jail cell!");
            en.addDefault("Arena.Cop.Caught", "&2You caught a robber! They have been sent to a jail cell!");
            en.addDefault("Arena.Cop.HitCop", "&4You cannot hurt another cop!");
            en.addDefault("Arena.Win.Cops", "&2The cops caught the robbers and won the minigame!");
            en.addDefault("Arena.Win.Robbers", "&4The Robbers have escaped and won the minigame!");
            en.addDefault("Arena.Win.None", "&4No one won the game! We dunno how that happened! -Shrug-");

            en.addDefault("Command.Create", "&5Created new arena with Id: &6%id%");
            en.addDefault("Command.Tool.Enable", "&3Enabled arena tool!");
            en.addDefault("Command.Tool.Disable", "&3Disabled arena tool!");
            en.addDefault("Command.ReloadArena", "&3Reloaded arena &6%id%&3!");
            en.addDefault("Command.Set.Enable", "&3Enabled arena &6%id%&3!");
            en.addDefault("Command.Set.Disable", "&3Disabled arena &6%id%&3!");
            en.addDefault("Command.Set.End", "&3End position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Quit", "&3Quit position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Lobby", "&3Lobby position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Spectate", "&3Spectating position for arena &6%id% &3set!");
            en.addDefault("Command.Set.Regen", "&3Regen area for arena &6%id% &3set!");
            en.addDefault("Command.Set.Name", "&3Display name for arena &6%id% &3is now &6%name%&3!");
            en.addDefault("Command.Set.MinPlayers", "&3Minimum players for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.MaxPlayers", "&3Maximum players for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.MinCops", "&3Minimum number of cops for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.MaxCops", "&3Maximum number of cops for arena &6%id% &3set to &6%count%&3!");
            en.addDefault("Command.Set.Spawn.Cop", "&3Cop Spawn Number &6%number% for arena &6%id% &3set!");
            en.addDefault("Command.Set.Spawn.Robber", "&3Robber Spawn Number &6%number% for arena &6%id% &3set!");
            en.addDefault("Command.Set.Time.Wait", "&3Wait delay for arena &6%id% &3set to &6%time% seconds&3!");
            en.addDefault("Command.Set.Time.Start", "&3Start delay for arena &6%id% &3set to &6%time% seconds&3!");
            en.addDefault("Command.Set.Time.Game", "&3Game length for arena &6%id% &3set to &6%time% minutes&3!");
            en.addDefault("Command.Set.LateJoin", "&3Late join for arena &6%id% &3set to &6%allowed%&3!");
            en.addDefault("Command.Set.Kit.CanUse", "&3Can use kits for arena &6%id% &3set to &6%canUse%&3!");
            en.addDefault("Command.Set.Kit.Default.Cop", "&3Default kit for cops for arena &6%id% &3set to &6%kitName%&3!");
            en.addDefault("Command.Set.Kit.Default.Robber", "&3Default kit for robbers for arena &6%id% &3set to &6%kitName%&3!");
            en.addDefault("Command.Set.Loc.Loc1", "&3Location 1 set!");
            en.addDefault("Command.Set.Loc.Loc1Only", "&3Set location 2 (right click( and then finalize your selection!");
            en.addDefault("Command.Set.Loc.Loc2", "&3Location 2 set!");
            en.addDefault("Command.Set.Loc.Loc2Only", "&3Set location 1 (left click) and then finalize your selection!");
            en.addDefault("Command.Set.Loc.Both", "&3Locations 1 and 2 set! &6Use the correct command to finalize your selection!");
            en.addDefault("Command.Set.Loc.Need", "&4You need to set both positions 1 and 2 in order to set this!");
            en.addDefault("Command.Kit.Create", "&5Kit &6%kitName% &5created!");
            en.addDefault("Command.Kit.Delete", "&6Kit deleted!");
            en.addDefault("Command.Kit.AlreadyExists", "&4A kit with that name already exists! Delete it with &6/car kit delete <kitName>");
            en.addDefault("Command.Kit.DoesNotExist", "&4A kit with that name does not exist!");

            en.addDefault("Sign.Place.Join", "&2Join sign placed! &3Right click to join, left click to view info!");
            en.addDefault("Sign.Place.Spectate", "&2Spectate sign placed! &3Right click to spectate the arena!");
            en.addDefault("Sign.Place.Quit", "&2Quit sign placed! &3Right click to quit the game you are in!");
            en.addDefault("Sign.Info.Heading", "&1-~- &6Arena %id% Info &1-~-");

            en.addDefault("Notifications.BlockedCommand", "&4You cannot use that command while in game! &2Use /car quit &4to leave the game");
            en.addDefault("Notifications.ArenaDoesNotExist", "&4The specified arena does not exist or is not enabled!");
            en.addDefault("Notifications.AlreadyEnabled", "&4That arena is already enabled!");
            en.addDefault("Notifications.AlreadyDisabled", "&4That arena is already disabled!");
            en.addDefault("Notifications.NotJoinable", "&4The specified arena is currently not joinable!");
            en.addDefault("Notifications.Error", "&4There was some sort of error! Sorry!");
            en.addDefault("Notifications.Enable.Cannot", "&4Cannot enable the specified arena because a setting is missing!");
            en.addDefault("Notifications.PlayerOnly", "&4Only players can do this! (Support for non players may come soon)");
            en.addDefault("Notifications.Args.Few", "&4Too few arguments for this command!");
            en.addDefault("Notifications.Args.Many", "&4Too many arguments for this command!");
            en.addDefault("Notifications.Args.Invalid", "&4Invalid arguments for this command!");
            en.addDefault("Notifications.NoPerm", "&4You do not have permission to do that!");
            en.addDefault("Notifications.Int.Arena", "&4Arena Id must be a number!");
            en.addDefault("Notifications.Int.MinPlayers", "&4Minimum players must be a number!");
            en.addDefault("Notifications.Int.MaxPlayers", "&4Maximum players musts be a number!");
            en.addDefault("Notifications.Int.Time", "&4Time must be a number!");
            en.addDefault("Notifications.Int.Spawn", "&4Spawn value must be a number!");
            en.addDefault("Notifications.Bool.Kits", "&4Can use kits must be 'True' or 'False'");
            en.addDefault("Notifications.Bool.LateJoin", "&4Late join allowed must be 'True' or 'False'");


            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);

            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);
        }
    }
    public static File getMessageFile() {
        String msgFileName = Main.plugin.getConfig().getString("Language");
        return new File(Main.plugin.getDataFolder() + "/Messages/" + msgFileName + ".yml");
    }
    public static YamlConfiguration getMessageYml() {
        return YamlConfiguration.loadConfiguration(getMessageFile());
    }

    public static String getPrefix() {
        String prefixOr = getMessageYml().getString("Prefix");
        return ChatColor.translateAlternateColorCodes('&', prefixOr) + " " + ChatColor.RESET;
    }
    public static String getMessage(String msgString) {
        String msgOr = getMessageYml().getString(msgString);
        return ChatColor.translateAlternateColorCodes('&', msgOr);
    }
    public static String getMessage(String msgString, int id) {
        String msgOr = getMessageYml().getString(msgString);
        String msg = msgOr.replaceAll("%id%", String.valueOf(id));
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
