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

public class Login implements CommandExecutor {

    private Plugin plugin;
    private PlayerAccountDAO playerAccountDAO;
    private PluginMessages pluginMessages;

    public Login(Plugin plugin, PlayerAccountDAO playerAccountDAO, PluginMessages pluginMessages) {
        this.plugin = plugin;
        this.playerAccountDAO = playerAccountDAO;
        this.pluginMessages = pluginMessages;
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
                        Title title = new Title(this.pluginMessages.getAsString("loginWelcomeTitle"), this.pluginMessages.getAsString("loginWelcomeSubtitle"), 1, 3, 1);
                        title.send(p);
                        p.sendMessage(this.pluginMessages.getAsString("loginSuccess"));

                    } else {
                        p.sendMessage(this.pluginMessages.getAsString("loginUsage"));
                        return true;
                    }

                } else {
                    p.sendMessage(this.pluginMessages.getAsString("loginNotYet"));
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
