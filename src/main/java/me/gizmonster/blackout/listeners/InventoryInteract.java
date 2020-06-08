package me.gizmonster.blackout.listeners;

import me.gizmonster.blackout.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class InventoryInteract implements Listener {

    MainClass main = MainClass.getInstance();

    @EventHandler
    public void inventoryInteract(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equals(ChatColor.DARK_RED + "Revive Selection"))) {
            return;
        }
        HumanEntity player = event.getWhoClicked();
        event.setCancelled(true);
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) {
            player.closeInventory();
            return;
        }
        if (!(item.hasItemMeta())) {
            player.closeInventory();
            return;
        }
        if (!(item.getItemMeta().hasLocalizedName())) {
            player.closeInventory();
            return;
        }
        if (!(item.getItemMeta().getLocalizedName().equals("respawn"))) {
            return;
        }
        ItemStack selectedItem = event.getCurrentItem();
        if (selectedItem == null) {
            return;
        }
        if (!(item.hasItemMeta())) {
            player.closeInventory();
            return;
        }
        if (!(item.getItemMeta().hasLocalizedName())) {
            player.closeInventory();
            return;
        }
        if (selectedItem.getItemMeta().getLocalizedName().equals("player")) {
            String uuid = selectedItem.getItemMeta().getPersistentDataContainer().get(main.banlistUI.uuidkey, PersistentDataType.STRING);
            String targetName = selectedItem.getItemMeta().getPersistentDataContainer().get(main.banlistUI.namekey, PersistentDataType.STRING);
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(main.banlistUI.uuidkey, PersistentDataType.STRING, uuid);
            meta.getPersistentDataContainer().set(main.banlistUI.namekey, PersistentDataType.STRING, targetName);
            meta.setLocalizedName("charged_respawn");
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + targetName);
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, (float) 2);
        }
    }
}
