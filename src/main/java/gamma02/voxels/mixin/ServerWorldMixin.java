package gamma02.voxels.mixin;

import gamma02.voxels.common.Network.Networking;
import gamma02.voxels.server.VoxelsServer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {

    @Shadow public abstract List<ServerPlayer> getPlayers(Predicate<? super ServerPlayer> p_8796_);

    @Shadow public abstract <T extends ParticleOptions> int sendParticles(T p_8768_, double p_8769_, double p_8770_, double p_8771_, int p_8772_, double p_8773_, double p_8774_, double p_8775_, double p_8776_);

    int tickCount = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(BooleanSupplier k, CallbackInfo ci){
        this.getPlayers((serverPlayer) -> true).forEach((serverPlayer) ->{
            if(tickCount >= 20) {
                Networking.sendVoxelPacket(serverPlayer, VoxelsServer.getVoxelCount(serverPlayer.getUUID()));
                tickCount = 0;
            }
        });
        this.getPlayers((serverPlayer -> !VoxelsServer.isPresent(serverPlayer.getUUID()))).forEach((player) -> VoxelsServer.setVoxelCount(player.getUUID(), 0));
        tickCount++;
    }

}
