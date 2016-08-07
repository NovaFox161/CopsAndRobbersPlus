package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.Arena;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.perworldchatplus.chat.ChatMessage;
import com.cloudcraftgaming.perworldchatplus.chat.ChatRecipients;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class ChatListener implements Listener {
    Main plugin;
    public ChatListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            if (ArenaManager.getManager().isInGame(event.getPlayer())
                    || ArenaManager.getManager().isSpectating(event.getPlayer())) {
                Player player = event.getPlayer();
                Arena arena = ArenaManager.getManager().getArena(player);
                if (Main.plugin.perWorldChatPlus != null) {
                    if (ChatMessage.shouldBeGlobal(event.getMessage(), player)) {
                        event.setFormat(ArenaDataGetters.getChatPrefix(arena.getId()) + event.getFormat());
                        event.setMessage(event.getMessage());
                    } else {
                        if (Main.plugin.getConfig().getString("Chat.PerGame").equalsIgnoreCase("True")) {
                            event.getRecipients().clear();
                            event.getRecipients().add(player);
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (arena.getPlayers().contains(p.getUniqueId()) || arena.getSpectators().contains(p.getUniqueId())) {
                                    event.getRecipients().add(p);
                                }
                            }
                            Set<Player> spies = ChatRecipients.getAllSpyReceivers(event.getRecipients(), player);
                            for (Player p1 : spies) {
                                event.getRecipients().add(p1);
                            }
                            event.setFormat(ArenaDataGetters.getChatPrefix(arena.getId()) + event.getFormat());
                            event.setMessage(event.getMessage());
                        } else {
                            event.setFormat(ArenaDataGetters.getChatPrefix(arena.getId()) + event.getFormat());
                            event.setMessage(event.getMessage());
                        }
                    }
                } else {
                    if (Main.plugin.getConfig().getString("Chat.PerGame").equalsIgnoreCase("True")) {
                        event.getRecipients().clear();
                        event.getRecipients().add(player);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (arena.getPlayers().contains(p.getUniqueId()) || arena.getSpectators().contains(p.getUniqueId())) {
                                event.getRecipients().add(p);
                            }
                        }
                        event.setFormat(ArenaDataGetters.getChatPrefix(arena.getId()) + event.getFormat());
                        event.setMessage(event.getMessage());
                    } else {
                        event.setFormat(ArenaDataGetters.getChatPrefix(arena.getId()) + event.getFormat());
                        event.setMessage(event.getMessage());
                    }
                }
            }
        }
    }
}
