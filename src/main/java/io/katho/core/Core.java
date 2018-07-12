package io.katho.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.katho.core.cmds.Login;
import io.katho.core.cmds.Register;
import io.katho.core.configuration.PluginMessagesFactory;
import io.katho.core.listeners.PreLogin;
import io.katho.core.configuration.PluginMessages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * That's the Main class of the plugin. it work like a Singleton cause the instance is
 */
public class Core extends JavaPlugin {

    // Instance of this class for future references.
    private static Plugin INSTANCE;
    // List of logged players.
    private static Collection<Player> LOGGED_PLAYERS;
    private static PluginMessages PLUGIN_MESSAGES;
    // Local Instance Stuff;
    private Gson gson;

    /**
     * is the plugin bootsrap method.
     */
    @Override
    public void onEnable() {
        INSTANCE = this;
        LOGGED_PLAYERS = new ArrayList<Player>();
        this.gson = new GsonBuilder().create();
        this.buildFiles();
        PLUGIN_MESSAGES = PluginMessagesFactory.makePluginMessages();
        this.registerCommands();
        this.registerListeners();
        Bukkit.getLogger().info("Plugin bootstrap done!");
        Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer(getPluginMessages().getAsString("reloadKick")));
    }

    /**
     * @return Plugin instance of a singleton, to prevent it from being new obejcts.
     */
    public synchronized static Plugin getInstance() {
        if (INSTANCE == null) {
            return new Core();
        }
        return INSTANCE;
    }

    /**
     * Register the plugin commands to the Plugin.
     */
    private void registerCommands() {
        getCommand("register").setExecutor(new Register());
        getCommand("login").setExecutor(new Login());
    }

    /**
     * Register the plugin listeners to the Plugin.
     */
    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PreLogin(), this);
    }

    /**
     * Build main plugin configuration files.
     */
    private void buildFiles() {
        saveResource("config.json", false);
        saveResource("pt_BR.json", false);
        saveResource("en_US.json", false);
    }

    /**
     * @return The list of currently logged players.
     */
    public synchronized static Collection<Player> getLoggedIn() {
        return LOGGED_PLAYERS;
    }

    /**
     * @return The plugin messages objects. Where you can sarch for the plugin messages.
     */
    public synchronized static PluginMessages getPluginMessages() {
        return PLUGIN_MESSAGES;
    }


}
