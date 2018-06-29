package io.katho.utils;

import io.katho.utils.cmds.Register;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class KathoUtils extends JavaPlugin {

    private static List<Player> loggedPlayers;
    private static KathoUtils instance;

    public void onEnable() {
        instance = this;
        loggedPlayers = new ArrayList<Player>();
        registerCommands();
        registerListeners();
        buildFiles();
        Bukkit.getLogger().info("Plugin bootstrap done!");
    }

    private void registerCommands() {
        getCommand("register").setExecutor(new Register());
    }

    private void registerListeners() {
        // TODO
    }

    private void buildFiles() {
        saveResource("config.json", false);
    }

    public static KathoUtils getInstance() {
        return instance;
    }

    public static List<Player> getLoggedPlayers() { return loggedPlayers; }

}
