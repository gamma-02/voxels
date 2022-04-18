package gamma02.voxels.common.Network;

import gamma02.voxels.client.ClientVoxelTracker;
import gamma02.voxels.server.VoxelMapping.VoxelMappingHelper;
import gamma02.voxels.server.VoxelsUtils.VoxelsSerializers;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Networking {
    private static final String version = "1";
    private static final SimpleChannel channel = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation("voxels", "network_main"))
            .serverAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .clientAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    public static void init(){

    }

    static{
        channel.registerMessage(
                0, VoxelPacket.class,
                VoxelPacket::writeData, VoxelPacket::read,
                (packet, context ) ->{
                    if(context.get().getDirection().getReceptionSide().isClient()){
                        ClientVoxelTracker.CurrentVoxelCount = packet.VoxelAmount;
                    }else if(context.get().getDirection().getReceptionSide().isServer()){
                        System.out.println("wierd reception of packet, ignoring");
                    }
                    context.get().setPacketHandled(true);
        }, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        channel.registerMessage(1, ItemVoxelRequestC2SPacket.class,
                ItemVoxelRequestC2SPacket::write, ItemVoxelRequestC2SPacket::read,
                (packet, context) ->{
                    if(context.get().getDirection().getReceptionSide().isServer()){
                        ItemVoxelResponseS2CPacket response = new ItemVoxelResponseS2CPacket(
                                searchMap(packet),
                                packet.requestLocation,
                                packet.requestNbt
                                );
                        channel.sendTo(response, context.get().getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);

                    }
                }, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        channel.registerMessage(2,
                ItemVoxelResponseS2CPacket.class,
                ItemVoxelResponseS2CPacket::write, ItemVoxelResponseS2CPacket::read,
                (packet, context) ->{
                    if(context.get().getDirection().getReceptionSide().isClient()) {

                        if(packet.requestData != null && packet.requestData.contains("IsTag") && packet.requestData.getBoolean("IsTag")){
                            ClientVoxelTracker.receivedTagValues.put(packet.requestLocation, packet.amount);
                        }else {
                            ItemStack stack = new ItemStack(ForgeRegistries.ITEMS.getValue(packet.requestLocation));
                            stack.setTag(packet.requestData);

                            ClientVoxelTracker.receivedVoxelValues.put(stack, packet.amount);
                            System.out.println("FINISHED HANDELING REQUEST PACKET FOR " + packet.requestLocation);
                        }
                    }
                }, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendVoxelPacket(ServerPlayer recipitent, long amount){
        if (recipitent != null) {
            VoxelPacket packet = new VoxelPacket(amount, recipitent.getUUID());
            channel.sendTo(packet, recipitent.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    private static long timeSinceLastRequest = 0;
    private static ArrayDeque<ItemVoxelRequestC2SPacket> packetQueue = new ArrayDeque<>();

    public static void sendRequestPacket(ResourceLocation requestLocation, @Nullable CompoundTag requestNbt){
        System.out.println("Sending Request Packet for: " + requestLocation);
        if (requestNbt == null) {
            requestNbt = new CompoundTag();
        }
        if (!requestNbt.contains("IsTag")) {
            requestNbt.putBoolean("IsTag", false);
        }
        var packet = new ItemVoxelRequestC2SPacket(requestLocation, requestNbt);


        if(timeSinceLastRequest <= 0 && Minecraft.getInstance().getConnection() != null ) {
            System.out.println("Sending Request Packet for: " + requestLocation);
            channel.sendToServer(packet);
        }else{
            if(!packetQueue.contains(packet)){
                packetQueue.add(packet);
            }
        }
    }

    public static void sendRequestPacket(ResourceLocation requestLocation){
        sendRequestPacket(requestLocation, null);
    }

    public static void sendRequestPacket(ItemStack i){
        sendRequestPacket(i.getItem().getRegistryName(), i.getTag());
    }

    //used to limit a flood of rather large packets being sent to the client, will todo: make the time configurable
    public static void clientTick(){
        if(timeSinceLastRequest <= 0){
            if(!packetQueue.isEmpty()){
                channel.sendToServer(packetQueue.removeFirst());
                timeSinceLastRequest = 3;
            }
        }else{
            timeSinceLastRequest--;
        }
    }

    static long searchMap(ItemVoxelRequestC2SPacket packet){
        final long[] returnVal = {-1};
        VoxelMappingHelper.voxelValuesHashMap.keySet().forEach( (jsonItemStack -> {
            if (jsonItemStack.equals(new VoxelsSerializers.JsonItemStack(
                    packet.requestLocation,
                    (packet.requestNbt != null && packet.requestNbt.getBoolean("IsTag")),
                    packet.requestNbt
            ))) {
                returnVal[0] = VoxelMappingHelper.voxelValuesHashMap.get(jsonItemStack);
            }
        }) );
        return -1;
    }


}
