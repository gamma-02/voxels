package gamma02.voxels.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import gamma02.voxels.Voxels;
import gamma02.voxels.server.Config.ServerConfig;
import gamma02.voxels.server.VoxelsServer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelVersion;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class ServerLoadMixin {

    @Inject(method="parse", at = @At("RETURN"))
    private static void loadVoxelData(Dynamic<Tag> p_78531_, DataFixer p_78532_, int p_78533_, CompoundTag p_78534_, LevelSettings p_78535_, LevelVersion p_78536_, WorldGenSettings p_78537_, Lifecycle p_78538_, CallbackInfoReturnable<PrimaryLevelData> cir){
        if(p_78531_.get("VoxelData").result().isPresent()) {
            VoxelsServer.load(p_78531_.getElement("VoxelData").get());
        }
    }

    @Inject(method="setTagData", at = @At("RETURN"))
    private void saveVoxelData(RegistryAccess p_78546_, CompoundTag p_78547_, CompoundTag p_78548_, CallbackInfo ci){
        VoxelsServer.save(p_78547_);

        ServerConfig.save();
    }


}
