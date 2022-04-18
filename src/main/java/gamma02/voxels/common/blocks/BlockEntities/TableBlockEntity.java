package gamma02.voxels.common.blocks.BlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class TableBlockEntity extends BlockEntity {




    long voxels;

    UUID Owner;

    public TableBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public TableBlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.TABLE_BLOCK_ENTITY.get(), pos, state);
    }


    public UUID getOwner(){
        return Owner;
    }

    public void setOwner(UUID owner){
        this.Owner = owner;
    }

    public long getVoxels() {
        return voxels;
    }

    public void setVoxels(long voxels) {
        this.voxels = voxels;
    }

    public long getVoxelAmount(ItemStack stack){

        return 0;
    }






}
