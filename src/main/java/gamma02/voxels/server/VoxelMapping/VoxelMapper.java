package gamma02.voxels.server.VoxelMapping;

import gamma02.voxels.server.VoxelsUtils.VoxelsSerializers;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;

public interface VoxelMapper<T> {

    String getName();

    void addMappings(MappingCollector<VoxelsSerializers.JsonItemStack> mappingCollector, ReloadableServerResources resources, ResourceManager manager);


}
