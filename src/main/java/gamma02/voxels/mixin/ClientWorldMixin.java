package gamma02.voxels.mixin;

import gamma02.voxels.client.ClientVoxelTracker;
import gamma02.voxels.common.Network.Networking;
import gamma02.voxels.common.Network.StaticClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ClientLevel.class)
public class ClientWorldMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void clientTickMixin(BooleanSupplier p_104727_, CallbackInfo ci){
//        System.out.println("CLIENT VOXELS: " + StaticClientHelper.getVoxels());
        Networking.clientTick();
    }

    @Inject(method = "addPlayer", at = @At("HEAD"))
    public void addPlayerMixin(int id, AbstractClientPlayer player, CallbackInfo ci){
        if(player != null && Minecraft.getInstance().getConnection() != null) {
            for (ItemStack i : player.getInventory().items) {
                if (i.getItem() != Items.AIR) {
                    Networking.sendRequestPacket(i);
                }
            }
            for (ItemStack i : player.getInventory().armor) {
                if (i.getItem() != Items.AIR) {
                    Networking.sendRequestPacket(i);
                }
            }
            for (ItemStack i : player.getInventory().offhand) {
                if (i.getItem() != Items.AIR) {
                    Networking.sendRequestPacket(i);
                }
            }
        }
    }




}
