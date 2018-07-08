package io.katho.utils.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class PlayerAccountFile {

    private File file;
    private PlayerAccount playerAccount;
    private Gson gson;

    public PlayerAccountFile(File file, PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
        this.file = file;
        this.gson = new GsonBuilder().create();
    }

    public PlayerAccountFile(File file) throws IOException {
        this.file = file;
        this.gson = new GsonBuilder().create();
        load(new FileReader(this.file));
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setPlayerAccount(PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
    }

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }

    public void close() {
        try (Writer writer = new FileWriter(this.file)) {
            this.gson.toJson(playerAccount, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(Reader reader) throws IOException {
        this.playerAccount = this.gson.fromJson(reader, PlayerAccount.class);
        reader.close();
    }


}
