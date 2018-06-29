package io.katho.utils.listeners;

import io.katho.utils.KathoUtils;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class PreLogin implements Listener {

    public static Title loginTitle;

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

        if (playerFile.exists()) {
            try {
                JSONObject configObj = IOJSONUtils.readFirst(KathoUtils.getInstance().getDataFolder() + "/config.json");
                JSONObject playerObj = IOJSONUtils.readFirst(playerFile);
                JSONObject authObj = (JSONObject) configObj.get("authentication");
                long loginPeriod = (long) authObj.get("timeInterval");
                long lastLogin = (long) playerObj.get("lastLogin");


                if (timestamp > lastLogin + loginPeriod) {
                    loginTitle = new Title(PluginMessages.get("loginTitle"), PluginMessages.get("loginSubtitle"), 1, 20, 1);
                    loginTitle.send(p);
                } else {
                    loginTitle = new Title(PluginMessages.get("loginWelcomeTitle"), PluginMessages.get("loginWelcomeSubtitle"), 1, 3, 1);
                    loginTitle.send(p);
                    KathoUtils.getLoggedPlayers().add(p);
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
    }



}
