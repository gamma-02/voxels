package gamma02.voxels.common.Network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;

public class ItemVoxelRequestC2SPacket {
    public final ResourceLocation requestLocation;

    @Nullable
    public final CompoundTag requestNbt;




    public ItemVoxelRequestC2SPacket(ResourceLocation request, @Nullable CompoundTag nbt){
        this.requestLocation = request;
        if(nbt != null && !nbt.contains("IsTag")) {
            nbt.putBoolean("IsTag", false);
        }
        this.requestNbt = nbt;
    }

    public static ItemVoxelRequestC2SPacket read(FriendlyByteBuf buf){
        ResourceLocation location = buf.readResourceLocation();
        CompoundTag tag = buf.readNbt();
        if(tag != null) {
            return new ItemVoxelRequestC2SPacket(location, tag);
        }else{
            return new ItemVoxelRequestC2SPacket(location, null);
        }
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(requestLocation);
        if(this.requestNbt != null){
            buf.writeNbt(this.requestNbt);
        }

    }

    public void handle(PacketListener p_131342_) {

    }
}
