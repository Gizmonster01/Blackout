package me.gizmonster.blackout.listeners;

import me.gizmonster.blackout.MainClass;
import me.gizmonster.blackout.objects.BannedUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public class PlayerDeath implements Listener {
    private String message = ChatColor.RED + "" + ChatColor.BOLD + "You are now Deathbanned" + "\n" + "\n" + ChatColor.YELLOW + "Have a friend revive you at a Monolith or wait 24 hours.";
    MainClass main = MainClass.getInstance();
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        long now = Instant.now().toEpochMilli();
        long time = now + 86400000;
        BannedUser ban = new BannedUser(player, time); // LINE 29
        main.fileManager.saveBanData(ban);
        new BukkitRunnable(){ public void run(){
            if (!(player.isOnline())) {
                return;
            }
            player.kickPlayer(message);
        }}.runTaskLater(main, 5);
    }
}
