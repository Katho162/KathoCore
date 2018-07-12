package io.katho.core.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.katho.core.Core;

import java.io.*;
import java.util.Collection;
import java.util.UUID;

public class PlayerAccountDAOImpl implements PlayerAccountDAO {

    private Gson gson;
    private final String ACCOUNTS_DIR = Core.getInstance().getDataFolder() + "/accounts/";

    public PlayerAccountDAOImpl() {
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void addAccount(PlayerAccount playerAccount) {
        File dir = new File(ACCOUNTS_DIR);
        dir.mkdirs();
        try (Writer writer = new FileWriter(ACCOUNTS_DIR + playerAccount.getUUID() + ".json", false)) {
            gson.toJson(playerAccount, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerAccount getAccount(UUID uuid) {
        File dir = new File(ACCOUNTS_DIR);
        dir.mkdirs();
        try (Reader reader = new FileReader(ACCOUNTS_DIR + uuid.toString() + ".json")) {
            return gson.fromJson(reader, PlayerAccount.class);
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAccount(UUID uuid) {
        File file = new File(ACCOUNTS_DIR + uuid.toString() + ".json");
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                throw new FileNotFoundException("The file doesn't exits or have no account on there.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (file.delete()) {
            return;
        } else {
            try {
                throw new Exception("The file isn't deleted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateAccount(PlayerAccount playerAccount) {
        File dir = new File(ACCOUNTS_DIR);
        dir.mkdirs();
        try (Writer writer = new FileWriter(ACCOUNTS_DIR + playerAccount.getUUID() + ".json")) {
            gson.toJson(playerAccount, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existAccount(UUID uuid) {
        File file = new File(ACCOUNTS_DIR + uuid.toString() + ".json");
        if ((file == null) || !(file.exists())) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<PlayerAccount> getAllAccounts() {
        return null;
    }
}
