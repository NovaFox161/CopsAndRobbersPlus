package com.cloudcraftgaming.copsandrobbersplus.listeners;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.arena.ArenaManager;
import com.cloudcraftgaming.copsandrobbersplus.utils.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class BlockBreakListener implements Listener {
    public BlockBreakListener(Main instance) {
        plugin = instance;
    }
    Main plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Player.Rules.Block.Break"));
        }
    }
}
