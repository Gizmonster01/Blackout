package me.gizmonster.blackout.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.gizmonster.blackout.MainClass;
import me.gizmonster.blackout.objects.BannedUser;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class FileManager {
    Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    MainClass main = MainClass.getInstance();
    public File schematicsPath;
    public File bandataPath;
    String directory = main.getDataFolder().getPath() + "\\schematics";
    String banDirectory = main.getDataFolder().getPath() + "\\bandata";
    public List<File> structures = new ArrayList<>();

    public void initializeFolders() {
        schematicsPath = new File(main.getDataFolder(), "schematics");
        bandataPath = new File(main.getDataFolder(), "bandata");
        if (!schematicsPath.exists()) {
            schematicsPath.mkdirs();
        }
        if (!bandataPath.exists()) {
            bandataPath.mkdirs();
        }
        List<String> pathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.forEach(path -> {
                pathList.add(path.toString());
                System.out.println(path);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String path : pathList) {
            File file = new File(path);
            if (file.isFile()) {
                structures.add(file);
                continue;
            }
        }
    }

    public void saveBanData(BannedUser player) {
        File data = new File(bandataPath, player.getUuid() + ".json");
        if(!data.exists()) {
            data.getParentFile().mkdirs();
        }
        if(!data.exists()) {
            try {
                data.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Writer writer = null;
        try {
            writer = new FileWriter(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gson.toJson(player, writer);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BannedUser> allBanned() {
        List<BannedUser> bannedUsers = new ArrayList<>();
        List<File> files = new ArrayList<>();
        Reader reader = null;
        try (Stream<Path> paths = Files.walk(Paths.get(banDirectory))) {
            paths.forEach(path -> {
                File file = new File(path.toString());
                if (file.isFile()) {
                    files.add(file);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File file : files) {
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BannedUser bannedUser = gson.fromJson(reader, BannedUser.class);
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bannedUsers.add(bannedUser);
        }
        return bannedUsers;
    }

    public BannedUser loadBan(Player player) {
        UUID id = player.getUniqueId();
        File data = new File(bandataPath, id.toString() + ".json");
        Reader reader = null;
        try {
            reader = new FileReader(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BannedUser bannedUser = gson.fromJson(reader, BannedUser.class);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bannedUser;
    }
}
