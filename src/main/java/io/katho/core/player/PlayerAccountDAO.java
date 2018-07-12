package io.katho.core.player;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.UUID;

public interface PlayerAccountDAO {

    public void addAccount(PlayerAccount playerAccount);
    public PlayerAccount getAccount(UUID uuid);
    public void removeAccount(UUID uuid) throws FileNotFoundException;
    public void updateAccount(PlayerAccount playerAccount);
    public boolean existAccount(UUID uuid);
    public Collection<PlayerAccount> getAllAccounts();

}
