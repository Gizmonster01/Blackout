package me.gizmonster.blackout.resources;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
    public ItemStack uishell = new ItemStack(Material.CLAY_BALL);

    public void initializeItems() {
        ItemMeta meta = uishell.getItemMeta();
        meta.setCustomModelData(500);
        meta.setDisplayName(ChatColor.GRAY + "");
        uishell.setItemMeta(meta);
    }
}
