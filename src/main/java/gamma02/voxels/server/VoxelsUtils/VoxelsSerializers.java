package gamma02.voxels.server.VoxelsUtils;

import com.google.gson.*;
import com.ibm.icu.impl.Pair;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ResourceLocationException;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.json.Json;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class VoxelsSerializers implements JsonDeserializer<VoxelsSerializers.JsonItemStack>, JsonSerializer<VoxelsSerializers.JsonItemStack> {

    public static HashMap<String, Integer> TagString = new HashMap<>();

    public static VoxelsSerializers INSTANCE = new VoxelsSerializers();

    /**
     * This creates a itemstack from a json string.
     */
    public static Function<String, JsonItemStack> itemCreator = s -> {
        int nbtStart = s.indexOf('{');
        int nbtEnd = s.lastIndexOf('}');
        ResourceLocation resourceLocation = getResourceLocation(nbtStart == -1 ? s.substring(s.indexOf('"')+1, s.lastIndexOf('"')) : s.substring(s.indexOf('"')+1, nbtStart), "item");

        if(s.startsWith("#")){
            return new JsonItemStack(resourceLocation, true, null);
        }

        if(nbtStart == -1){
            return new JsonItemStack(resourceLocation, false, null);
        }
        String nbtAsString = s.substring(nbtStart, nbtEnd+1);
        ArrayList<Character> nbt = new ArrayList<>();
        for(char c : nbtAsString.toCharArray()){
            if(c != '\\'){
                nbt.add(c);
            }
        }
        char[] temp = new char[nbt.size()];
        int i = 0;
        for(char c : nbt) {
            temp[i] = c;
            i++;
        }
        nbtAsString = String.copyValueOf(temp);



        System.out.println("NBT: " + nbtAsString);
        try{

            return new JsonItemStack(resourceLocation, false, TagParser.parseTag(nbtAsString));
        }catch(CommandSyntaxException e){
//            throw new JsonParseException("your nbt was not very poggers", e);
            e.printStackTrace();

        }
        return null;
    };

    public VoxelsSerializers(){

    }

    private static ResourceLocation getResourceLocation(String s, String type) throws JsonParseException {
        try {


            while(s.contains("\\\"")){
                s = s.substring(s.indexOf("\\\""), s.lastIndexOf("\\\""));
            }

            System.out.println("MAKING RESOURCE LOCATION: " + s );

            return new ResourceLocation(s);
        } catch (ResourceLocationException e) {
            throw new JsonParseException("Malformed " + type + " ID", e);
        }
    }

    @Override
    public JsonItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return itemCreator.apply(json.toString());
    }

    @Override
    public JsonElement serialize(JsonItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.json());
    }

    public static class JsonItemStack implements JsonStackJsonType{
        @Nullable
        private final CompoundTag nbt;
        private final ResourceLocation location;
        private final boolean isTag;
        public JsonItemStack(ResourceLocation location, boolean tag, @Nullable CompoundTag nbt){
            this.isTag = tag;
            this.location = location;
            this.nbt = nbt;

        }

        @Nullable
        public CompoundTag getNBT() {
            return nbt;
        }

        public String json() {

            if (hasNBT()) {
                return this.location.toString() + this.nbt.getAsString();
            }
            return this.location.toString();
        }

        @Override
        public String toString() {
            String string = super.toString();
            if (hasNBT()) {
                return string + nbt;
            }
            return string;
        }

        @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }else if(o instanceof JsonItemStack) {
                return Objects.equals(nbt, ((JsonItemStack) o).nbt) && Objects.equals(location, ((JsonItemStack) o).location);
            }else{
                return o.equals(this);
            }
        }

        @Override
        public int hashCode() {
            int code = (this.isTag ? 31 + this.location.hashCode() : this.location.hashCode());
            if (this.nbt != null) {
                code = 31 * code + getNBT().hashCode();
            }
            return code;
        }

        public boolean hasNBT(){
            return this.nbt != null;
        }
    }

    public interface JsonStackJsonType{

        String json();

        @Override
        boolean equals(Object o);

        @Override
        int hashCode();

        @Override
        String toString();

    }


}
