package io.katho.utils.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.katho.utils.KathoUtils;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.PluginMessagesImpl;
import io.katho.utils.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class PreLogin implements Listener {

    public static Title loginTitle;
    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;
    private PluginMessages pluginMessages;

    public PreLogin(Plugin plugin, PlayerAccountDAO playerAccountDAO, PluginMessages pluginMessages) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
        this.pluginMessages = pluginMessages;
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedPlayers().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedPlayers().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedPlayers().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedPlayers().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void interactEntityEvent(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedPlayers().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
            PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());
            Gson gson = new GsonBuilder().create();
            try (Reader reader = new FileReader(this.plugin.getDataFolder() + "/config.json")) {
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
            loginTitle = new Title(this.pluginMessages.getAsString("registerTitle"), this.pluginMessages.getAsString("registerSubtitle"), 1, 20, 1);
            loginTitle.send(p);
        }

        Bukkit.getScheduler().runTaskLater(KathoUtils.getInstance(), () -> {
            if (!KathoUtils.getLoggedPlayers().contains(p))
                p.kickPlayer("§cVocê demorou muito para entrar no servidor.");
        }, 20 * 20);

    }


}
