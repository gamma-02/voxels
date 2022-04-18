package gamma02.voxels.common.Network;

import gamma02.voxels.client.ClientVoxelTracker;

/*@OnlyIn(Dist.CLIENT)*/
public class StaticClientHelper {
    public static long getVoxels(){
        return ClientVoxelTracker.CurrentVoxelCount;
    }




}
