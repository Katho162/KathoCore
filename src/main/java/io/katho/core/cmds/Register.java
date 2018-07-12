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

public class Register implements CommandExecutor {

    private PlayerAccountDAO playerAccountDAO;

    /**
     * Register command is the main command of Register/Login system, is there where the players will make their account files.
     */
    public Register() {
        this.playerAccountDAO = new PlayerAccountDAOImpl();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("register")) {
            Player p = (Player) sender;

            if (!Core.getLoggedIn().contains(p)) {
                if (!this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 2) {
                        if (args[0].equals(args[1])) {

                            PlayerAccount playerAccount = new PlayerAccount(p, args[0], System.currentTimeMillis());
                            this.playerAccountDAO.addAccount(playerAccount);
                            Core.getLoggedIn().add(p);
                            new TitleBuilder()
                                    .setTitle(Core.getPluginMessages().getAsString("loginWelcomeTitle"))
                                    .setSubtitle(Core.getPluginMessages().getAsString("loginWelcomeSubtitle"))
                                    .build()
                                    .send(p);
                            p.sendMessage(Core.getPluginMessages().getAsString("registerSuccess"));
                            return true;

                        } else {
                            p.sendMessage(Core.getPluginMessages().getAsString("registerConfirm"));
                            return true;
                        }
                    } else {
                        p.sendMessage(Core.getPluginMessages().getAsString("registerUsage"));
                        return true;
                    }
                } else {
                    p.sendMessage(Core.getPluginMessages().getAsString("registerAlready"));
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
