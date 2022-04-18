package gamma02.voxels.common.blocks.BlockEntities;

import gamma02.voxels.common.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> ModBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "voxels");

    public static final RegistryObject<BlockEntityType<TableBlockEntity>> TABLE_BLOCK_ENTITY = ModBlockEntities.register("table_block_entity", () -> BlockEntityType.Builder.of(TableBlockEntity::new, ModBlocks.BASIC_VOXEL_MANIPULATOR/*For now*/).build(null));



    public static void register(IEventBus bus){
        ModBlockEntities.register(bus);
    }
}
