package me.gizmonster.blackout.objects;

import org.bukkit.entity.Player;

import java.util.UUID;

public class BannedUser {

    private UUID uuid;
    private String playerName;
    private long time;

    public BannedUser(Player player, long time) {
        this.playerName = player.getName();
        this.uuid = player.getUniqueId();
        this.time = time;
    }

    public UUID getUuid() { return uuid;}
    public String getPlayerName() { return playerName;}
    public long getTime() { return time;}
}
