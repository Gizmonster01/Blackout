package me.gizmonster.blackout.listeners;

import me.gizmonster.blackout.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;

import static org.bukkit.inventory.EquipmentSlot.OFF_HAND;

public class PlayerUse implements Listener {
    MainClass main = MainClass.getInstance();
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getHand() == OFF_HAND) {
            return;
        }
        if (player.getInventory().getItemInMainHand() == null) {
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(item.hasItemMeta())) {
            return;
        }
        if (!(item.getItemMeta().hasLocalizedName())) {
            return;
        }
        if (item.getItemMeta().getLocalizedName().equals("respawn")) {
            main.banlistUI.showBans(player);
            return;
        }
    }

    @EventHandler
    public void onRightClickBlock(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (!(action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (player.getInventory().getItemInMainHand() == null) {
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!(item.hasItemMeta())) {
            return;
        }
        if (!(item.getItemMeta().hasLocalizedName())) {
            return;
        }
        if (!(event.getClickedBlock().getType().toString().toLowerCase().contains("command"))) {
            return;
        }
        if (item.getItemMeta().getLocalizedName().equals("charged_respawn")) {
            event.setCancelled(true);
            ItemMeta meta = item.getItemMeta();
            String uuid = meta.getPersistentDataContainer().get(main.banlistUI.uuidkey, PersistentDataType.STRING);
            String targetName = meta.getPersistentDataContainer().get(main.banlistUI.namekey, PersistentDataType.STRING);
            File data = new File(main.fileManager.bandataPath, uuid + ".json");
            Bukkit.broadcastMessage(uuid);
            if (!(data.exists())) {
                return;
            }
            if (data.exists()) {
                data.delete();
            }
            int slot = player.getInventory().getHeldItemSlot();
            int amount = item.getAmount();
            if (amount > 0) {
                item.setAmount(amount - 1);
            } else {
                player.getInventory().setItem(slot, null);
            }
        }
    }
}
