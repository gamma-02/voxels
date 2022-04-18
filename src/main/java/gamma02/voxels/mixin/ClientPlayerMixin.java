package gamma02.voxels.mixin;

import gamma02.voxels.client.ClientVoxelTracker;
import gamma02.voxels.common.Network.Networking;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ClientPlayerMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> {

    protected ClientPlayerMixin(Class<ItemStack> baseClass) {
        super(baseClass);
    }

    ItemStack hmmmmm = (ItemStack) ((CapabilityProvider<ItemStack>)this);//*jeremy clarson impression* god im clever!

    @Inject(method = "getTooltipLines", at = @At("TAIL"), cancellable = true)
    public void voxelDisplayMixin(Player p_41652_, TooltipFlag p_41653_, CallbackInfoReturnable<List<Component>> cir) {
        if (p_41653_ != null) {
            long voxelsAmount;
            voxelsAmount = ClientVoxelTracker.getVoxelsForItemStack((hmmmmm));
            if (voxelsAmount != -1) {
                Component text = new TranslatableComponent("tooltips.voxels.voxel_amount").append(new TextComponent(": " + Long.toString(voxelsAmount)));
                cir.getReturnValue().add(text);

            } else {
                Networking.sendRequestPacket(hmmmmm.getItem().getRegistryName(), hmmmmm.getTag());
            }
            cir.setReturnValue(cir.getReturnValue());
        }
    }
}
