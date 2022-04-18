package gamma02.voxels.server;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.UUID;

/**
 * Used for keeping track of voxels, <i><b>IN THE SERVER</b></i>!!!!!<br>
 *
 * Use {@link gamma02.voxels.client.ClientVoxelTracker} for the client :)
 */
public class VoxelsServer {

    private static final HashMap<UUID, Integer> playerVoxelTracker = new HashMap<>();


    public static int getVoxelCount(UUID uuid){
        return playerVoxelTracker.get(uuid);
    }

    public static void setVoxelCount(UUID uuid, int voxels){
        playerVoxelTracker.put(uuid, voxels);
    }

    public static void addVoxels(UUID uuid, int amount){
        playerVoxelTracker.put(uuid, (playerVoxelTracker.get(uuid) + amount));
    }

    public static CompoundTag save(CompoundTag nbt){
        ListTag hashList = new ListTag();
        for(UUID id : playerVoxelTracker.keySet()){
            CompoundTag tag = new CompoundTag();
            tag.putUUID("UUID", id);
            tag.putInt("amount", playerVoxelTracker.get(id));
            hashList.add(tag);
        }

        nbt.put("VoxelData", hashList);
        return nbt;
    }

    public static void load(Either<Tag, DataResult.PartialResult<Tag>> either){
        if(either.left().isPresent()) {
            ListTag list = ((ListTag) either.left().get());
            for (Tag tag1 : list) {
                if (tag1 instanceof CompoundTag tag) {
                    playerVoxelTracker.put(tag.getUUID("UUID"), tag.getInt("amount"));
                }
            }
        }
    }

    public static boolean isPresent(UUID id){
        return playerVoxelTracker.containsKey(id);
    }

}
