package gamma02.voxels.server.VoxelMapping;

import gamma02.voxels.server.VoxelsUtils.VoxelsSerializers;

import java.util.HashMap;

public class MappingCollector<T extends VoxelsSerializers.JsonItemStack> {
    HashMap<VoxelsSerializers.JsonItemStack, Long> toAdd = new HashMap<>();

    /*package protected*/ MappingCollector(){}//lmao java is weird sometimes

    public void add(VoxelsSerializers.JsonItemStack stack, long l){
        toAdd.put(stack, l);
    }

    public void remove(VoxelsSerializers.JsonItemStack stack){
        toAdd.remove(stack);
    }


}
