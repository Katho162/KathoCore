package io.katho.utils.utils;

import io.katho.utils.KathoUtils;
import org.bukkit.ChatColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sun.misc.IOUtils;

import java.io.IOException;

public class PluginMessages {

    /**
     * This method is the implementation of the getter of plugin messages configuration.
     * @param message is the message key.
     * @return the message string with colours.
     */
    public static String get(String message) {
        try {
            JSONObject messages = (JSONObject) IOJSONUtils.readFirst(KathoUtils.getInstance().getDataFolder() + "/config.json").get("messages");
            String rawMessage = (String) messages.get(message);
            return ChatColor.translateAlternateColorCodes('&', rawMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
