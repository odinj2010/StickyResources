package net.nfgbros.stickyresources.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// Base class for network packets
public abstract class SimplePacketBase {

    /**
     * Serializes the packet data into the byte buffer.
     *
     * @param buf The buffer to write the data to.
     */
    public abstract void toBytes(FriendlyByteBuf buf);

    /**
     * Handles the packet on the receiving side.
     *
     * @param supplier The context supplier for the packet.
     * @return A boolean indicating whether the packet was handled successfully.
     */
    public abstract boolean handle(Supplier<NetworkEvent.Context> supplier);

}