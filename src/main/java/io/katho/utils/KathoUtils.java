package io.katho.utils;

import io.katho.utils.cmds.Login;
import io.katho.utils.cmds.Register;
import io.katho.utils.listeners.PreLogin;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
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
        prepareLogin();
    }

    private void registerCommands() {
        getCommand("register").setExecutor(new Register());
        getCommand("login").setExecutor(new Login());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PreLogin(), this);
    }

    private void buildFiles() {
        saveResource("config.json", false);
    }

    public static KathoUtils getInstance() {
        return instance;
    }

    public static List<Player> getLoggedPlayers() { return loggedPlayers; }

    private void prepareLogin() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            File playerFile = new File(KathoUtils.getInstance().getDataFolder() + "/accounts/" + p.getUniqueId() + ".account");
            long timestamp = System.currentTimeMillis();
            if (playerFile.exists()) {
                try {
                    JSONObject configObj = IOJSONUtils.readFirst(KathoUtils.getInstance().getDataFolder() + "/config.json");
                    JSONObject playerObj = IOJSONUtils.readFirst(playerFile);
                    JSONObject authObj = (JSONObject) configObj.get("authentication");
                    long loginPeriod = (long) authObj.get("timeInterval");
                    long lastLogin = (long) playerObj.get("lastLogin");

                    if (p.getAddress().getHostName().equals(playerObj.get("lastIP"))) {
                        if (timestamp > lastLogin + loginPeriod) {
                            PreLogin.loginTitle = new Title(PluginMessages.get("loginTitle"), PluginMessages.get("loginSubtitle"), 1, 20, 1);
                            PreLogin.loginTitle.send(p);
                        } else {
                            PreLogin.loginTitle = new Title(PluginMessages.get("loginWelcomeTitle"), PluginMessages.get("loginWelcomeSubtitle"), 1, 3, 1);
                            PreLogin.loginTitle.send(p);
                            KathoUtils.getLoggedPlayers().add(p);
                        }
                    } else {
                        PreLogin.loginTitle = new Title(PluginMessages.get("loginTitle"), PluginMessages.get("loginSubtitle"), 1, 20, 1);
                        PreLogin.loginTitle.send(p);
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                PreLogin.loginTitle = new Title(PluginMessages.get("registerTitle"), PluginMessages.get("registerSubtitle"), 1, 20, 1);
                PreLogin.loginTitle.send(p);
            }
        });

    }

}
