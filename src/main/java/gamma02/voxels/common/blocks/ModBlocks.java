package gamma02.voxels.common.blocks;

import gamma02.voxels.common.registry.Registrar;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

@Registrar(element = Block.class, modid = "voxels")
public class ModBlocks {

    public static final Block BASIC_VOXEL_MANIPULATOR = new BasicVoxelManipulator(BlockBehaviour.Properties.of(Material.METAL).explosionResistance(100).destroyTime(30));


}
