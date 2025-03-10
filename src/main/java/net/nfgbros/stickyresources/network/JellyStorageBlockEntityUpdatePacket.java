package net.nfgbros.stickyresources.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.NetworkEvent;
import net.nfgbros.stickyresources.block.entity.JellyStorageBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Packet to update the client about the state of the JellyStorageBlockEntity.
 */
public class JellyStorageBlockEntityUpdatePacket extends SimplePacketBase {
    private final BlockPos pos; // Position of the block entity
    private final List<Entity> jellies; // List of jelly entities to update

    /**
     * Constructor to create the packet on the sender side.
     *
     * @param pos     The position of the block entity.
     * @param jellies The list of jelly entities to be sent.
     */
    public JellyStorageBlockEntityUpdatePacket(BlockPos pos, List<Entity> jellies) {
        this.pos = pos;
        this.jellies = jellies;
    }

    /**
     * Constructor to read the packet data on the receiver side.
     *
     * @param buf The buffer containing the packet data.
     */
    public JellyStorageBlockEntityUpdatePacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();

        // Read and deserialize the list of jellies from the buffer
        ListTag jelliesTag = buf.readNbt().getList("jellies", 10);
        this.jellies = new ArrayList<>();
        for (int i = 0; i < jelliesTag.size(); i++) {
            CompoundTag entityTag = jelliesTag.getCompound(i);
            Entity entity = EntityType.loadEntityRecursive(entityTag, Minecraft.getInstance().level, ent -> {
                ent.setPos(pos.getX(), pos.getY(), pos.getZ());
                return ent;
            });
            if (entity != null) {
                jellies.add(entity);
            }
        }
    }

    /**
     * Serializes the packet data to the buffer.
     *
     * @param buf The buffer to write the data to.
     */
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);

        // Serialize and write the jelly list to the buffer
        CompoundTag mainTag = new CompoundTag();
        ListTag jelliesTag = new ListTag();
        for (Entity jelly : jellies) {
            if (jelly instanceof INBTSerializable) {
                CompoundTag entityTag = new CompoundTag();
                entityTag = ((INBTSerializable<CompoundTag>) jelly).serializeNBT();
                jelliesTag.add(entityTag);
            }
        }
        mainTag.put("jellies", jelliesTag);
        buf.writeNbt(mainTag);
    }

    /**
     * Handles the received packet on the client side.
     *
     * @param supplier The network event context supplier.
     * @return True to indicate the packet was handled.
     */
    @Override
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Retrieve the block entity and update it with the received data
            BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
            if (blockEntity instanceof JellyStorageBlockEntity) {
                ((JellyStorageBlockEntity) blockEntity).handleUpdatePacket(jellies);
            }
        });
        return true;
    }
}