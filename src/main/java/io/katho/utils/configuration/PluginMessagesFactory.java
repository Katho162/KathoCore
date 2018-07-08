package io.katho.utils.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.katho.utils.KathoUtils;
import org.bukkit.Bukkit;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class PluginMessagesFactory {

    /**
     * This is a simple factory for the PluginMessages Object.
     * @return the object of the PluginMessages.
     */
    public static PluginMessages makePluginMessages() {
        Gson gson = new GsonBuilder().create();
        try (Reader reader = new FileReader(KathoUtils.getInstance().getDataFolder() + "/config.json")) {
           return new PluginMessagesImpl(gson.fromJson(reader, JsonObject.class).get("pluginLanguage").getAsString());
        } catch (IOException e) {
            Bukkit.getLogger().warning("Impossible to get plugin messages");
        }
        return null;
    }

}
