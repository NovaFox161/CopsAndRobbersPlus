package com.cloudcraftgaming.copsandrobbersplus.arena;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import com.cloudcraftgaming.copsandrobbersplus.getters.ArenaDataGetters;
import com.cloudcraftgaming.copsandrobbersplus.utils.Cuboid;
import com.cloudcraftgaming.copsandrobbersplus.utils.GameState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import java.util.List;

/**
 * Created by Nova Fox on 5/7/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class Regenerator {
    public static void regenArena(int id) {
        if (ArenaManager.getManager().arenaExists(id)) {
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Starting arena regeneration for Arena Id: " + String.valueOf(id));
                Main.plugin.getLogger().info("This may take some time depending on the arena's size.");
            }

            Arena arena = ArenaManager.getManager().getArena(id);
            arena.setJoinable(false);
            arena.setGameState(GameState.REGENERATING);

            Cuboid regenArea = ArenaDataGetters.getRegenArea(id);

            clearGroundItems(id, regenArea);
            resetDoors(id, regenArea);

            ArenaManager.getManager().reloadArena(id);
        }
    }

    private static void clearGroundItems(int id, Cuboid regenArea) {
        if (ArenaManager.getManager().arenaExists(id)) {
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Clearing ground items for arena Id: " + String.valueOf(id));
            }
            List<Entity> entList = regenArea.getWorld().getEntities();

            for (Entity entity : entList) {
                if (entity instanceof Item) {
                    if (regenArea.contains(entity.getLocation())) {
                        entity.remove();
                    }
                }
            }
            if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
                Main.plugin.getLogger().info("Successfully cleared all ground items for arena Id: " + String.valueOf(id));
            }
        }
    }
    private static void resetDoors(int id, Cuboid regenArea) {
        if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Resetting all doors/trapdoors/etc for Arena Id: " + String.valueOf(id));
        }
        for (Block block : regenArea.getBlocks()) {
            if (block == null || block.getType().equals(Material.AIR)) {
                continue;
            }
            Material mat = block.getType();
            if (isOpenable(mat)) {

                BlockState state = block.getState();
                Openable o = (Openable) state.getData();
                o.setOpen(false);
                state.setData((MaterialData) o);
                state.update();
            }
        }
        if (Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Successfully reset all doors/trapdoors/etc for Arena Id: " + String.valueOf(id));
        }
    }
    private static boolean isOpenable(Material mat) {
        return mat.equals(Material.ACACIA_DOOR) || mat.equals(Material.BIRCH_DOOR) || mat.equals(Material.DARK_OAK_DOOR)
                || mat.equals(Material.SPRUCE_DOOR) || mat.equals(Material.JUNGLE_DOOR) || mat.equals(Material.IRON_DOOR)
                || mat.equals(Material.WOODEN_DOOR) || mat.equals(Material.TRAP_DOOR) || mat.equals(Material.FENCE_GATE)
                || mat.equals(Material.ACACIA_FENCE_GATE) || mat.equals(Material.BIRCH_FENCE_GATE) || mat.equals(Material.DARK_OAK_FENCE_GATE)
                || mat.equals(Material.SPRUCE_FENCE_GATE) || mat.equals(Material.JUNGLE_FENCE_GATE);
    }
}