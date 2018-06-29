package io.katho.utils;

import io.katho.utils.cmds.Register;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class KathoUtils extends JavaPlugin {

    private static KathoUtils instance;

    public void onEnable() {
        instance = this;
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
        Bukkit.getLogger().info("Build plugin files...");
    }

    public static KathoUtils getInstance() {
        return instance;
    }

}