package io.katho.utils.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.katho.utils.KathoUtils;
import org.bukkit.Bukkit;
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
            Bukkit.getLogger().warning("It was not possible to get " + path + " in plugin messages file.");
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("It was not possible to get " + path + " in plugin messages file.");
        }
        return null;
    }

    public String getAsString(String path) {
        try (Reader reader = new FileReader(this.langFile)) {
            return ChatColor.translateAlternateColorCodes('&', this.gson.fromJson(reader, JsonObject.class).get(path).getAsString());
        } catch (IOException e) {
            Bukkit.getLogger().warning("It was not possible to get " + path + " in plugin messages file.");
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning("It was not possible to get " + path + " in plugin messages file.");
        }
        return null;
    }

}
