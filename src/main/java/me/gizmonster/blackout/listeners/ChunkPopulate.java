package me.gizmonster.blackout.listeners;

import me.gizmonster.blackout.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.io.File;

public class ChunkPopulate implements Listener {

    MainClass main = MainClass.getInstance();

    @EventHandler
    public void chunkGenerate(ChunkPopulateEvent event) {
        int rngesus = main.getRandomNum(1, 500);
        if (rngesus == 100) {
        if (!(event.getChunk().getWorld().getEnvironment() == World.Environment.NORMAL)) {
            return;
        }
        File file = main.fileManager.structures.get(0);
            int x = event.getChunk().getBlock(15, 100, 15).getLocation().getBlockX();
            int y= 64;
            int z = event.getChunk().getBlock(15, 100, 15).getLocation().getBlockZ();
            Bukkit.broadcastMessage("Generating Monolith at " + " " + x + " " + z);
            Location loc = new Location(event.getWorld(), x, y, z);
            main.structureLoader.buildStructure(file, loc, true);
        }
    }
}
