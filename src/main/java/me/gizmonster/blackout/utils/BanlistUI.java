package me.gizmonster.blackout.utils;

import me.gizmonster.blackout.MainClass;
import me.gizmonster.blackout.objects.BannedUser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BanlistUI {

    MainClass main = MainClass.getInstance();
    public NamespacedKey uuidkey = new NamespacedKey(main, "banned_uuid");
    public NamespacedKey namekey = new NamespacedKey(main, "name_key");
    public void showBans(Player player) {
        List<BannedUser> bannedUsers = main.fileManager.allBanned();
        int amtBanned = bannedUsers.size();
        int rows = 4;
        if (bannedUsers == null || amtBanned == 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "There is no one to be revived."));
            return;
        }
        if (amtBanned > 14) {
            rows = 5;
        }
        if (amtBanned > 28) {
            rows = 6;
        }
        Inventory inv = Bukkit.createInventory(null, rows * 9, ChatColor.DARK_RED + "Revive Selection");
        inv.setItem(0, main.items.uishell);
        for (BannedUser user : bannedUsers) {
            int index = 9;
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.getPersistentDataContainer().set(uuidkey, PersistentDataType.STRING, user.getUuid().toString());
            meta.getPersistentDataContainer().set(namekey, PersistentDataType.STRING, user.getPlayerName());
            meta.setLocalizedName("player");
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getUuid());
            meta.setOwningPlayer(offlinePlayer);
            meta.setDisplayName(ChatColor.RED + user.getPlayerName());
            head.setItemMeta(meta);
            inv.setItem(index, head);
            index =+ 1;
        }
        player.openInventory(inv);
    }
}
