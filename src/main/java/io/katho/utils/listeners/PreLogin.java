package io.katho.utils.listeners;

import io.katho.utils.KathoUtils;
import io.katho.utils.utils.IOJSONUtils;
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

        try {
            JSONObject configObj = IOJSONUtils.readFirst(KathoUtils.getInstance().getDataFolder() + "/config.json");
            JSONObject playerObj = IOJSONUtils.readFirst(playerFile);
            JSONObject authObj = (JSONObject) configObj.get("authentication");
            long loginPeriod = (long) authObj.get("timeInterval");
            long lastLogin = (long) playerObj.get("lastLogin");

            if (timestamp > lastLogin + loginPeriod) {
                p.sendMessage("Você precisa se logar!");
            } else {
                p.sendMessage("Você foi logado com sucesso");
                KathoUtils.getLoggedPlayers().add(p);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }



}
