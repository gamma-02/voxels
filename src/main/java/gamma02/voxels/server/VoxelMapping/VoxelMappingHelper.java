package gamma02.voxels.server.VoxelMapping;

import gamma02.voxels.server.VoxelsUtils.VoxelsSerializers;
import it.unimi.dsi.fastutil.Hash;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class VoxelMappingHelper {

    public static HashMap<VoxelsSerializers.JsonItemStack, Long> voxelValuesHashMap = new HashMap<>();

    private static ArrayDeque<VoxelMapper<VoxelsSerializers.JsonItemStack>> mappers;



    public void registerMapper(VoxelMapper<VoxelsSerializers.JsonItemStack> mapper){
        mappers.add(mapper);
    }





}
