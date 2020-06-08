package me.gizmonster.blackout.commands;

import me.gizmonster.blackout.MainClass;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class DEBUG implements CommandExecutor {
    MainClass main = MainClass.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String directory = main.getDataFolder().getPath() + "\\schematics";
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage("Arguments Needed");
            return true;
        }
        if (args[0].equals("size")) {
            Bukkit.broadcastMessage(main.fileManager.structures.size() + "");
            return true;
        }
        if (args[0].equals("ban")) {
            Set<BanEntry> type = Bukkit.getBanList(BanList.Type.NAME).getBanEntries();
            for (BanEntry banEntry : type) {
                Bukkit.broadcastMessage(banEntry.getTarget());
                Bukkit.broadcastMessage(banEntry.getSource());
            }
            return true;
        }
        if (args[0].equals("list")) {
            main.banlistUI.showBans(player);
            return true;
        }
        return false;
    }
}
