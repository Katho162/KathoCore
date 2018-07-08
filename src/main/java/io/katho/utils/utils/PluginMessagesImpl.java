package io.katho.utils.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.katho.utils.KathoUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class PluginMessagesImpl implements PluginMessages {

    private Plugin plugin;
    private String pluginLanguage;
    private Gson gson;
    private File langFile;
    private final File configFile = new File(KathoUtils.getInstance().getDataFolder() + "/config.json");

    public PluginMessagesImpl(String pluginLanguage) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().create();
        this.pluginLanguage = pluginLanguage;
        this.langFile = new File(KathoUtils.getInstance().getDataFolder() + "/" + this.pluginLanguage + ".json");
    }

    public JsonElement get(String path) {
        try (Reader reader = new FileReader(this.langFile)) {
            return this.gson.fromJson(reader, JsonObject.class).get(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAsString(String path) {
        try (Reader reader = new FileReader(this.langFile)) {
            return ChatColor.translateAlternateColorCodes('&', this.gson.fromJson(reader, JsonObject.class).get(path).getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
