package io.katho.utils.cmds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Register implements CommandExecutor {

    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;

    public Register(Plugin plugin, PlayerAccountDAO playerAccountDAO) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem executar este comando.");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("register")) {
            Player p = (Player) sender;

            if (!KathoUtils.getLoggedPlayers().contains(p)) {
                if (!this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 2) {
                        if (args[0].equals(args[1])) {

                            PlayerAccount playerAccount = new PlayerAccount(p, args[0], System.currentTimeMillis());
                            this.playerAccountDAO.addAccount(playerAccount);

                            // Add player to logged.
                            KathoUtils.getLoggedPlayers().add(p);
                            // Aesthetics.
                            PreLogin.loginTitle.clearTitle(p);
                            Title title = new Title(PluginMessages.get("loginWelcomeTitle"), PluginMessages.get("loginWelcomeSubtitle"), 1, 3, 1);
                            title.send(p);

                            p.sendMessage(PluginMessages.get("registerSuccess"));
                            return true;

                        } else {
                            p.sendMessage(PluginMessages.get("registerConfirm"));
                            return true;
                        }
                    } else {
                        p.sendMessage(PluginMessages.get("registerUsage"));
                        return true;
                    }
                } else {
                    p.sendMessage(PluginMessages.get("registerAlready"));
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
