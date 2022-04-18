package gamma02.voxels.common.Network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.packs.repository.Pack;
import net.minecraftforge.network.ICustomPacket;

import java.util.UUID;

public class VoxelPacket implements Packet {
    public final long VoxelAmount ;
    public final UUID target;

    public VoxelPacket(long voxelAmount, UUID target){
        this.VoxelAmount = voxelAmount;
        this.target = target;
    }

    public static VoxelPacket read(FriendlyByteBuf buffer){
        return new VoxelPacket(buffer.readLong(), buffer.readUUID());
    }



    public void writeData(FriendlyByteBuf buffer) {
        buffer.writeLong(VoxelAmount);
        buffer.writeUUID(target);

    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(VoxelAmount);
        buffer.writeUUID(target);
    }

    @Override
    public void handle(PacketListener listener) {

    }
}
