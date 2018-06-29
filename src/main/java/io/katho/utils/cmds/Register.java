package io.katho.utils.cmds;

import io.katho.utils.KathoUtils;
import io.katho.utils.utils.IOJSONUtils;
import io.katho.utils.utils.PluginMessages;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class Register implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem executar este comando.");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("register")) {
            Player p = (Player) sender;
            File playerFile = new File(KathoUtils.getInstance().getDataFolder() + "/accounts/" + p.getUniqueId() + ".account");
            if (!playerFile.exists()) {
                if (args.length == 2) {
                    if (args[0].equals(args[1])) {
                        JSONObject playerObj = new JSONObject();
                        playerObj.put("name", p.getName());
                        playerObj.put("uuid", p.getUniqueId().toString());
                        playerObj.put("registerTimestamp", System.currentTimeMillis());
                        playerObj.put("lastLogin", System.currentTimeMillis());
                        try {
                            IOJSONUtils.write(playerFile, playerObj);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
        }
        return false;
    }
}
