package io.katho.utils.listeners;

import io.katho.utils.KathoUtils;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class PreLogin implements Listener {

    public static Title loginTitle;
    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;

    public PreLogin(Plugin plugin, PlayerAccountDAO playerAccountDAO) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
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
        File playerFile = new File(KathoUtils.getInstance().getDataFolder() + "/accounts/" + p.getUniqueId() + ".account");
        long timestamp = System.currentTimeMillis();

        if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
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
            loginTitle = new Title(PluginMessages.get("registerTitle"), PluginMessages.get("registerSubtitle"), 1, 20, 1);
            loginTitle.send(p);
        }

        Bukkit.getScheduler().runTaskLater(KathoUtils.getInstance(), () -> {
            if (!KathoUtils.getLoggedPlayers().contains(p))
                p.kickPlayer("§cVocê demorou muito para entrar no servidor.");
        }, 20 * 20);

    }


}
