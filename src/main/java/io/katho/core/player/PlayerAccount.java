package io.katho.core.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

public class PlayerAccount {

    private UUID uuid;
    private String name;
    private String password;
    private String lastIP;
    private long lastLogin;
    private long registerTimestamp;
    private transient File accountFile;

    /**
     * The register version. becouse the registerTimestamp is the same as lastLogin timestamp.
     * @param player the player instance.
     * @param password is the player account password.
     * @param registerTimestamp the timestamp of register.
     */
    public PlayerAccount(Player player, String password, long registerTimestamp) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.lastIP = player.getAddress().getHostName();
        this.password = DigestUtils.sha256Hex(password);
        this.lastLogin = registerTimestamp;
        this.registerTimestamp = registerTimestamp;
    }

    /**
     * The register version. becouse the registerTimestamp isn't the same as lastLogin timestamp.
     * @param player the player instance.
     * @param password is the player account password.
     * @param registerTimestamp the timestamp of register.
     */
    public PlayerAccount(Player player, String password, long registerTimestamp, long lastLogin) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.lastIP = player.getAddress().getHostName();
        this.password = DigestUtils.sha256Hex(password);
        this.lastLogin = lastLogin;
        this.registerTimestamp = registerTimestamp;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(long registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
    }

    public File getAccountFile() {
        return accountFile;
    }

    public String toString() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

}
