package gamma02.voxels.common.Network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class ItemVoxelResponseS2CPacket  {
    
    public final long amount;
    public final ResourceLocation requestLocation;

    @Nullable
    public final CompoundTag requestData;//todo: might write a custom interpereter for special requests but idk

    public ItemVoxelResponseS2CPacket(long amount, ResourceLocation requestLocation, @Nullable CompoundTag requestData){
        this.amount = amount;
        this.requestData = requestData;
        this.requestLocation = requestLocation;
    }

    public static ItemVoxelResponseS2CPacket read(FriendlyByteBuf buf){
        long amount = buf.readLong();
        ResourceLocation request = buf.readResourceLocation();
        CompoundTag tag = buf.readNbt();
        if(tag != null){
            return new ItemVoxelResponseS2CPacket(amount, request, tag);
        }else{
            return new ItemVoxelResponseS2CPacket(amount, request, null);
        }
    }


    public void write(FriendlyByteBuf buf) {
        buf.writeLong(amount);
        buf.writeResourceLocation(requestLocation);
        buf.writeNbt(requestData);
    }


    public void handle(PacketListener p_131342_) {

    }
}
