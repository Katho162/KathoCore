package io.katho.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.katho.utils.cmds.Login;
import io.katho.utils.cmds.Register;
import io.katho.utils.listeners.PreLogin;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.player.PlayerAccountDAOImpl;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.PluginMessagesImpl;
import io.katho.utils.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KathoUtils extends JavaPlugin {

    private static List<Player> loggedPlayers;
    private static Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;
    private PluginMessages pluginMessages;
    private Gson gson;

    public void onEnable() {
        plugin = this;
        this.playerAccountDAO = new PlayerAccountDAOImpl();
        this.gson = new GsonBuilder().create();
        buildFiles();
        try (Reader reader = new FileReader(getDataFolder() + "/config.json")) {
            this.pluginMessages = new PluginMessagesImpl(gson.fromJson(reader, JsonObject.class).get("pluginLanguage").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        loggedPlayers = new ArrayList<Player>();
        registerCommands();
        registerListeners();
        Bukkit.getLogger().info("Plugin bootstrap done!");
        prepareLogin();
    }

    private void registerCommands() {
        getCommand("register").setExecutor(new Register(this, this.playerAccountDAO, this.pluginMessages));
        getCommand("login").setExecutor(new Login(this, this.playerAccountDAO, this.pluginMessages));
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PreLogin(this, this.playerAccountDAO, this.pluginMessages), this);
    }

    private void buildFiles() {
        saveResource("config.json", false);
        saveResource("pt_BR.json", false);
        saveResource("en_US.json", false);
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static List<Player> getLoggedPlayers() {
        return loggedPlayers;
    }

    private void prepareLogin() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());
            if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
                Gson gson = new GsonBuilder().create();
                try (Reader reader = new FileReader(plugin.getDataFolder() + "/config.json")) {
                    JsonObject config = gson.fromJson(reader, JsonObject.class);
                    JsonObject auth = config.getAsJsonObject("authentication");
                    if (p.getAddress().getHostName().equals(playerAccount.getLastIP())) {
                        if (System.currentTimeMillis() > playerAccount.getLastLogin() + auth.get("timeInterval").getAsLong()) {
                            PreLogin.loginTitle = new Title(this.pluginMessages.getAsString("loginTitle"), this.pluginMessages.getAsString("loginSubtitle"), 1, 20, 1);
                            PreLogin.loginTitle.send(p);
                        } else {
                            PreLogin.loginTitle = new Title(this.pluginMessages.getAsString("loginWelcomeTitle"), this.pluginMessages.getAsString("loginWelcomeSubtitle"), 1, 3, 1);
                            PreLogin.loginTitle.send(p);
                            KathoUtils.getLoggedPlayers().add(p);
                        }
                    } else {
                        PreLogin.loginTitle = new Title(this.pluginMessages.getAsString("loginTitle"), this.pluginMessages.getAsString("loginSubtitle"), 1, 20, 1);
                        PreLogin.loginTitle.send(p);
                    }
                } catch (IOException o) {
                    o.printStackTrace();
                }

            } else {
                PreLogin.loginTitle = new Title(this.pluginMessages.getAsString("registerTitle"), this.pluginMessages.getAsString("registerSubtitle"), 1, 20, 1);
                PreLogin.loginTitle.send(p);
            }
        });

    }

}
