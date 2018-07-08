package io.katho.utils.cmds;

import io.katho.utils.KathoUtils;
import io.katho.utils.listeners.PreLogin;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.PluginMessagesImpl;
import io.katho.utils.utils.Title;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Register implements CommandExecutor {

    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;
    private PluginMessages pluginMessages;

    public Register(Plugin plugin, PlayerAccountDAO playerAccountDAO, PluginMessages pluginMessages) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
        this.pluginMessages = pluginMessages;
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
                            KathoUtils.getLoggedPlayers().add(p);
                            PreLogin.loginTitle.clearTitle(p);
                            Title title = new Title(this.pluginMessages.getAsString("loginWelcomeTitle"), this.pluginMessages.getAsString("loginWelcomeSubtitle"), 1, 3, 1);
                            title.send(p);

                            p.sendMessage(this.pluginMessages.getAsString("registerSuccess"));
                            return true;

                        } else {
                            p.sendMessage(this.pluginMessages.getAsString("registerConfirm"));
                            return true;
                        }
                    } else {
                        p.sendMessage(this.pluginMessages.getAsString("registerUsage"));
                        return true;
                    }
                } else {
                    p.sendMessage(this.pluginMessages.getAsString("registerAlready"));
                    return true;
                }
            } else {
                p.sendMessage(this.pluginMessages.getAsString("alreadyLogged"));
                return true;
            }
        }
        return false;
    }
}
