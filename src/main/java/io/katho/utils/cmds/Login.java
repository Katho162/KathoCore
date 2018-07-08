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

            if (!KathoUtils.getLoggedIn().contains(p)) {
                if (this.playerAccountDAO.existAccount(p.getUniqueId())) {
                    if (args.length == 1) {

                        PlayerAccount playerAccount = this.playerAccountDAO.getAccount(p.getUniqueId());
                        playerAccount.setLastLogin(System.currentTimeMillis());
                        playerAccount.setLastIP(p.getAddress().getHostName());
                        this.playerAccountDAO.updateAccount(playerAccount);
                        KathoUtils.getLoggedIn().add(p);
                        new TitleBuilder()
                                .setTitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeTitle"))
                                .setSubtitle(KathoUtils.getPluginMessages().getAsString("loginWelcomeSubtitle"))
                                .build()
                                .send(p);
                        p.sendMessage(KathoUtils.getPluginMessages().getAsString("loginSuccess"));

                    } else {
                        p.sendMessage(KathoUtils.getPluginMessages().getAsString("loginUsage"));
                        return true;
                    }

                } else {
                    p.sendMessage(KathoUtils.getPluginMessages().getAsString("loginNotYet"));
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
