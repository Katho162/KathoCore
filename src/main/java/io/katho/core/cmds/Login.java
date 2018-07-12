package io.katho.core.cmds;

import io.katho.core.Core;
import io.katho.core.player.PlayerAccount;
import io.katho.core.player.PlayerAccountDAO;
import io.katho.core.player.PlayerAccountDAOImpl;
import io.katho.core.utils.TitleBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Login implements CommandExecutor {

    private PlayerAccountDAO playerAccountDAO;

    /**
     * Login command is the command to handle all process of already registered players, make login on their acocunt.
     */
    public Login() {
        this.playerAccountDAO = new PlayerAccountDAOImpl();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            Player p = (Player) sender;

            if (!Core.getLoggedIn().contains(p)) {
                if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 1) {

                        PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());
                        playerAccount.setLastLogin(System.currentTimeMillis());
                        playerAccount.setLastIP(p.getAddress().getHostName());
                        this.playerAccountDAO.updateAccount(playerAccount);
                        Core.getLoggedIn().add(p);
                        new TitleBuilder()
                                .setTitle(Core.getPluginMessages().getAsString("loginWelcomeTitle"))
                                .setSubtitle(Core.getPluginMessages().getAsString("loginWelcomeSubtitle"))
                                .build()
                                .send(p);
                        p.sendMessage(Core.getPluginMessages().getAsString("loginSuccess"));

                    } else {
                        p.sendMessage(Core.getPluginMessages().getAsString("loginUsage"));
                        return true;
                    }

                } else {
                    p.sendMessage(Core.getPluginMessages().getAsString("loginNotYet"));
                    return true;
                }
            } else {
                p.sendMessage(Core.getPluginMessages().getAsString("alreadyLogged"));
                return true;
            }
        }

        return false;
    }
}
