package me.gizmonster.blackout;

import me.gizmonster.blackout.commands.DEBUG;
import me.gizmonster.blackout.commands.LN;
import me.gizmonster.blackout.listeners.*;
import me.gizmonster.blackout.resources.Items;
import me.gizmonster.blackout.utils.BanlistUI;
import me.gizmonster.blackout.utils.FileManager;
import me.gizmonster.blackout.utils.StructureLoader;
import me.gizmonster.blackout.utils.TimeUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainClass extends JavaPlugin {
    public static MainClass instance;
    public StructureLoader structureLoader;
    public FileManager fileManager;
    public TimeUtil time;
    public BanlistUI banlistUI;
    public Items items;


    @Override
    public void onEnable() {

        System.out.println("|");
        System.out.println("|");
        System.out.println("|           MainClass has booted up");
        System.out.println("|");
        System.out.println("|     By gizmonster - For Triad - Version 1.0");

        this.instance = this;
        this.structureLoader = new StructureLoader();
        this.fileManager = new FileManager();
        this.banlistUI = new BanlistUI();
        this.items = new Items();

        getServer().getPluginManager().registerEvents(new ChunkPopulate(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerLogin(), this);
        getServer().getPluginManager().registerEvents(new PlayerUse(), this);
        getServer().getPluginManager().registerEvents(new InventoryInteract(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockPlace(), this);
        this.getCommand("debug").setExecutor((CommandExecutor) new DEBUG());
        this.getCommand("ln").setExecutor((CommandExecutor) new LN());
        fileManager.initializeFolders();
        items.initializeItems();
    }

    public int getRandomNum (int min, int max) {
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public static MainClass getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
