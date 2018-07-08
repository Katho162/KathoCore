package io.katho.utils.listeners;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.katho.utils.KathoUtils;
import io.katho.utils.configuration.PluginMessagesFactory;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.configuration.PluginMessages;
import io.katho.utils.player.PlayerAccountDAOImpl;
import io.katho.utils.utils.Title;
import io.katho.utils.utils.TitleBuilder;
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
    private PlayerAccountDAO playerAccountDAO;
    private PluginMessages pluginMessages;
    private Gson gson;

    /**
     * Is the handle of player prelogin state, if player isn't logged it will be freeze until it get log in.
     */
    public PreLogin() {
        this.playerAccountDAO = new PlayerAccountDAOImpl();
        this.pluginMessages = PluginMessagesFactory.makePluginMessages();
        this.gson = new GsonBuilder().create();
    }

    /**
     * It will verify player and if all it's ok, the player will logged on.
     */
    @EventHandler
    public void joinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
            PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());

            try (Reader reader = new FileReader(KathoUtils.getInstance().getDataFolder() + "/config.json")) {
                JsonObject config = gson.fromJson(reader, JsonObject.class);
                JsonObject auth = config.getAsJsonObject("authentication");
                if (p.getAddress().getHostName().equals(playerAccount.getLastIP())) {
                    if (System.currentTimeMillis() > playerAccount.getLastLogin() + auth.get("timeInterval").getAsLong()) {
                        new TitleBuilder()
                                .setTitle(KathoUtils.getPluginMessages().getAsString("loginTitle"))
                                .setSubtitle(KathoUtils.getPluginMessages().getAsString("loginSubtitle"))
                                .setStayTime(20).build()
                                .send(p);
                    } else {
                        new TitleBuilder()
                                .setTitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeTitle"))
                                .setSubtitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeSubtitle"))
                                .setStayTime(3)
                                .build()
                                .send(p);
                        KathoUtils.getLoggedIn().add(p);
                    }
                } else {
                    new TitleBuilder()
                            .setTitle(KathoUtils.getPluginMessages().getAsString("loginTitle"))
                            .setSubtitle(KathoUtils.getPluginMessages().getAsString("loginSubtitle"))
                            .setStayTime(20)
                            .build()
                            .send(p);
                }
            } catch (IOException o) {
                Bukkit.getLogger().warning("It was not possible to load the configuration file.");
            } catch (NullPointerException o) {
                Bukkit.getLogger().warning("It was not possible to load the configuration file.");
            }

        } else {
            new TitleBuilder()
                    .setTitle(KathoUtils.getPluginMessages().getAsString("registerTitle"))
                    .setSubtitle(KathoUtils.getPluginMessages().getAsString("registerSubtitle"))
                    .setStayTime(20).build()
                    .send(p);
        }

        Bukkit.getScheduler().runTaskLater(KathoUtils.getInstance(), () -> {
            if (!KathoUtils.getLoggedIn().contains(p))
                p.kickPlayer(KathoUtils.getPluginMessages().getAsString("timeoutKick"));
        }, 20 * 20);

    }

    /**
     * Player freeze above.
     */
    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedIn().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedIn().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedIn().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedIn().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void interactEntityEvent(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!KathoUtils.getLoggedIn().contains(p)) {
            e.setCancelled(true);
        } else {
            e.setCancelled(false);
        }
    }

}
