package io.katho.utils.cmds;

import io.katho.utils.KathoUtils;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Login implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem utilizar este comando.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("login")) {
            Player p = (Player) sender;
            File playerFile = new File(KathoUtils.getInstance().getDataFolder() + "/accounts/" + p.getUniqueId() + ".account");

            if (!KathoUtils.getLoggedPlayers().contains(p)) {
                if (playerFile.exists()) {
                    if (args.length == 1) {
                        try {
                            JSONObject playerObj = IOJSONUtils.readFirst(playerFile);
                            if (playerObj.get("password").equals(DigestUtils.sha256Hex(args[0]))) {
                                playerObj.replace("lastIP", p.getAddress().getHostName());
                                playerObj.replace("lastLogin", System.currentTimeMillis());
                                IOJSONUtils.write(playerFile, playerObj);
                                KathoUtils.getLoggedPlayers().add(p);
                                p.sendMessage(PluginMessages.get("loginSuccess"));
                                return true;
                            } else {
                                p.sendMessage(PluginMessages.get("loginIncorrect"));
                                return true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        return true;
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
