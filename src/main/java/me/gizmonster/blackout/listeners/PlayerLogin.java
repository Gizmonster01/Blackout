package me.gizmonster.blackout.listeners;

import me.gizmonster.blackout.MainClass;
import me.gizmonster.blackout.objects.BannedUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.time.Instant;
import java.util.UUID;

import static me.gizmonster.blackout.utils.TimeUtil.toHMS;

public class PlayerLogin implements Listener {

    MainClass main = MainClass.getInstance();

    String bumper = org.apache.commons.lang.StringUtils.repeat("\n", 35);
    private String message = ChatColor.RED + "" + ChatColor.BOLD + "You are currently Deathbanned" + "\n" + "\n" + ChatColor.YELLOW + "Have a friend revive you at a Monolith or wait:" + ChatColor.RESET;

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UUID id = player.getUniqueId();
        long now = Instant.now().toEpochMilli();
        File data = new File(main.fileManager.bandataPath, id.toString() + ".json");
        if (data.exists()) {
            BannedUser banned = main.fileManager.loadBan(player);
            if (now > banned.getTime()) {
                data.delete();
                return;
            }
            long remaining = banned.getTime() - now;
            String remainingString = toHMS(remaining);
            if (remainingString == null) {
                remainingString = "ooga ugh";
            }
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message + "\n" + remainingString);
        }
    }
}
