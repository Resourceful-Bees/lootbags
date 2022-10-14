package tech.thatgravyboat.lootbags.common.network;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.common.network.messages.ShowToastPacket;

public class NetworkHandlers {
    public static final NetworkChannel CHANNEL = new NetworkChannel(Lootbags.MOD_ID, 0, "main");

    public static void register() {
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ShowToastPacket.ID, ShowToastPacket.HANDLER, ShowToastPacket.class);
    }
}