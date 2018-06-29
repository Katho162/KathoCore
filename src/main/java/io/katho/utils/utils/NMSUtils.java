package io.katho.utils.utils;

import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSUtils {

    public NMSUtils() {
    }

    /**
     * It will send a packet object to a connected player.
     * @param player the player of will receive the packet
     * @param packet the packet to send.
     */
    public static void sendPacket(Player player, Packet packet) {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(packet);
    }

}
