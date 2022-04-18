package gamma02.voxels.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ClientVoxelTracker {
    public static long CurrentVoxelCount = 0;//yeah that's literally it... just keeps track of the voxels the client has :)

    /** mayyyybbbbeeee make this a different thing?? ***mayyybbbbbeeee?????*** actuallly might as well do that now; wait no... :thocc: hmm nvm yeah no ima not do that just use it as a refrence for nbt, seeing as the serializer is in the server... yeah ok seems right this was useful
     * <br>
     * <br>
     * shut up its 12:30 am im tired
     *
     * todo:go thru and javadoc all of this
     * */
    public static Map<ItemStack, Long> receivedVoxelValues = new HashMap<>();

    public static Map<ResourceLocation, Long> receivedTagValues = new HashMap<>();


    public static long getVoxelsForItemStack(ItemStack query){

        for(ItemStack location : receivedVoxelValues.keySet()){
            if(location.getItem().getRegistryName() == query.getItem().getRegistryName()){
                if (location.getTag() != null && query.getTag() != null && location.getTag().getAsString().equals(query.getTag().getAsString())){
                    return receivedVoxelValues.get(location);
                }else if(location.getTag() == null && query.getTag() == null){
                    return receivedVoxelValues.get(location);
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
        return -1;

    }


}
