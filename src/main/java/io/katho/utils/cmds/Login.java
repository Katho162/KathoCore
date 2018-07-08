package io.katho.utils.cmds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.katho.utils.KathoUtils;
import io.katho.utils.listeners.PreLogin;
import io.katho.utils.player.PlayerAccountFile;
import io.katho.utils.utils.PluginMessages;
import io.katho.utils.utils.Title;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

public class Login implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem utilizar este comando.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            Player p = (Player) sender;
            File playerFile = new File(KathoUtils.getInstance().getDataFolder() + "/accounts/" + p.getUniqueId() + ".account");
            Gson gson = new GsonBuilder().create();


            if (!KathoUtils.getLoggedPlayers().contains(p)) {
                if (playerFile.exists()) {
                    if (args.length == 1) {

                        /*try {
                            JSONObject playerObj = IOJSONUtils.readFirst(playerFile);
                            if (playerObj.get("password").equals(DigestUtils.sha256Hex(args[0]))) {
                                playerObj.replace("lastIP", p.getAddress().getHostName());
                                playerObj.replace("lastLogin", System.currentTimeMillis());
                                IOJSONUtils.write(playerFile, playerObj);

                            } else {
                                p.sendMessage(PluginMessages.get("loginIncorrect"));
                                return true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                        try {
                            PlayerAccountFile playerAccountFile = new PlayerAccountFile(playerFile);
                            playerAccountFile.getPlayerAccount().setLastLogin(System.currentTimeMillis());
                            playerAccountFile.getPlayerAccount().setLastIP(p.getAddress().getHostName());
                            playerAccountFile.close();
                            KathoUtils.getLoggedPlayers().add(p);
                            PreLogin.loginTitle.clearTitle(p);
                            Title title = new Title(PluginMessages.get("loginWelcomeTitle"), PluginMessages.get("loginWelcomeSubtitle"), 1, 3, 1);
                            title.send(p);
                            p.sendMessage(PluginMessages.get("loginSuccess"));
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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
