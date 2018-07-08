package io.katho.utils.cmds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.katho.utils.KathoUtils;
import io.katho.utils.listeners.PreLogin;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.player.PlayerAccountFile;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.Title;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Login implements CommandExecutor {

    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;

    public Login(Plugin plugin, PlayerAccountDAO playerAccountDAO) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem utilizar este comando.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            Player p = (Player) sender;

            if (!KathoUtils.getLoggedPlayers().contains(p)) {
                if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 1) {

                        PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());
                        playerAccount.setLastLogin(System.currentTimeMillis());
                        playerAccount.setLastIP(p.getAddress().getHostName());
                        this.playerAccountDAO.updateAccount(playerAccount);
                        KathoUtils.getLoggedPlayers().add(p);
                        PreLogin.loginTitle.clearTitle(p);
                        Title title = new Title(PluginMessages.get("loginWelcomeTitle"), PluginMessages.get("loginWelcomeSubtitle"), 1, 3, 1);
                        title.send(p);
                        p.sendMessage(PluginMessages.get("loginSuccess"));

                    } else {
                        p.sendMessage(PluginMessages.get("loginUsage"));
                        return true;
                    }

                } else {
                    p.sendMessage(PluginMessages.get("loginNotYet"));
                    return true;
                }
            } else {
                p.sendMessage(PluginMessages.get("alreadyLogged"));
                return true;
            }
        }

        return false;
    }
}
