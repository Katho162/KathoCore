package io.katho.utils.configuration;

import com.google.gson.JsonElement;

public interface PluginMessages {

    public JsonElement get(String path);
    public String getAsString(String path);

}
