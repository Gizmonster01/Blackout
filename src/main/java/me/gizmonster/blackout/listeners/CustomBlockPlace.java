package me.gizmonster.blackout.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CustomBlockPlace implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) {
            return;
        }
        if (!(item.hasItemMeta())) {
            return;
        }
        if (event.hasBlock()) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getHand().equals(EquipmentSlot.HAND)) return;
            }
            if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                return;
            }
            World world = player.getWorld();
            Block block = event.getClickedBlock();
            Location originalLoc = block.getLocation();
            Location blockLoc = block.getLocation();
            BlockFace face = event.getBlockFace();
            Location loc;
            switch(face) {
                case EAST:
                    loc = blockLoc.add(1, 0, 0);
                    break;
                case WEST:
                    loc = blockLoc.add(-1, 0, 0);
                    break;
                case NORTH:
                    loc = blockLoc.add(0, 0, -1);
                    break;
                case SOUTH:
                    loc = blockLoc.add(0, 0, 1);
                    break;
                case UP:
                    loc = blockLoc.add(0, 1, 0);
                    break;
                case DOWN:
                    loc = blockLoc.add(0, -1, 0);
                    break;
                default:
                    loc = new Location(null, 69, 69, 69);
            }
            if (block.getType() == Material.GRASS) {
                loc = originalLoc;
            }
            if (!(world.getNearbyEntities(loc.add(0.5, 0, 0.5), 0.5, 0.5, 0.5).isEmpty())) {
                return;
            }
            if (item.getItemMeta().getLocalizedName().equals("block")) {
                world.playSound(loc, Sound.BLOCK_STONE_PLACE, 1, 1);
                Block target = world.getBlockAt(loc);
                int amount = item.getAmount();
                if (player.getGameMode() == GameMode.ADVENTURE) {
                    return;
                }
                if (!(target.getType() == Material.AIR || target.getType() == Material.GRASS)) {
                    return;
                }
                target.setType(Material.PETRIFIED_OAK_SLAB);
                Slab slab = (Slab) world.getBlockAt(loc).getBlockData();
                slab.setType(Slab.Type.DOUBLE);
                target.setBlockData(slab);
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (amount > 0) {
                        item.setAmount(amount - 1);
                    } else {
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
                    }
                }
            }
            if (item.getItemMeta().getLocalizedName().equals("lunaraltar")) {
                world.playSound(loc, Sound.BLOCK_STONE_PLACE, 1, 1);
                Block target = world.getBlockAt(loc);
                int amount = item.getAmount();
                if (player.getGameMode() == GameMode.ADVENTURE) {
                    return;
                }
                if (!(target.getType() == Material.AIR || target.getType() == Material.GRASS)) {
                    return;
                }
                target.setType(Material.PETRIFIED_OAK_SLAB);
                Slab slab = (Slab) world.getBlockAt(loc).getBlockData();
                slab.setType(Slab.Type.TOP);
                target.setBlockData(slab);
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (amount > 0) {
                        item.setAmount(amount - 1);
                    } else {
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
                    }
                }
            }
        }
    }
}
