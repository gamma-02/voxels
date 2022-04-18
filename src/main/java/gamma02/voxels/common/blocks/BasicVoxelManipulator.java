package gamma02.voxels.common.blocks;

import gamma02.voxels.common.blocks.BlockEntities.TableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BasicVoxelManipulator extends BaseEntityBlock {

    protected BasicVoxelManipulator(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        TableBlockEntity entity = new TableBlockEntity(pos, state);

        return entity;
    }
}
