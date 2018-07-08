package io.katho.utils.cmds;

import io.katho.utils.KathoUtils;
import io.katho.utils.player.PlayerAccount;
import io.katho.utils.player.PlayerAccountDAO;
import io.katho.utils.player.PlayerAccountDAOImpl;
import io.katho.utils.utils.TitleBuilder;
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

            if (!KathoUtils.getLoggedIn().contains(p)) {
                if (!this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 2) {
                        if (args[0].equals(args[1])) {

                            PlayerAccount playerAccount = new PlayerAccount(p, args[0], System.currentTimeMillis());
                            this.playerAccountDAO.addAccount(playerAccount);
                            KathoUtils.getLoggedIn().add(p);
                            new TitleBuilder()
                                    .setTitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeTitle"))
                                    .setSubtitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeSubtitle"))
                                    .build()
                                    .send(p);
                            p.sendMessage(KathoUtils.getPluginMessages().getAsString("registerSuccess"));
                            return true;

                        } else {
                            p.sendMessage(KathoUtils.getPluginMessages().getAsString("registerConfirm"));
                            return true;
                        }
                    } else {
                        p.sendMessage(KathoUtils.getPluginMessages().getAsString("registerUsage"));
                        return true;
                    }
                } else {
                    p.sendMessage(KathoUtils.getPluginMessages().getAsString("registerAlready"));
                    return true;
                }
            } else {
                p.sendMessage(KathoUtils.getPluginMessages().getAsString("alreadyLogged"));
                return true;
            }
        }
        return false;
    }
}
