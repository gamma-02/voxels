package gamma02.voxels.server.Config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gamma02.voxels.server.VoxelMapping.VoxelMappingHelper;
import gamma02.voxels.server.VoxelsUtils.VoxelsSerializers;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ServerConfig {

    public static JsonObject savedJsonValues;

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(VoxelsSerializers.JsonItemStack.class, VoxelsSerializers.INSTANCE)
            .enableComplexMapKeySerialization().setPrettyPrinting().create();

    public static long maxVoxelValue;

    ServerConfig(ForgeConfigSpec.Builder builder){
        builder.comment("maximum voxel value per player").define("max_voxel_value", maxVoxelValue);
    }

    static {
        Pair<ServerConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        String savedJson = "";
        Path of = Path.of("config", "voxels", "saved_voxel_values.json");
        Path of1 = Path.of("config", "voxels");
        File configDir = of1.toFile();
        File jsonFile = of.toFile();
        if(!Files.exists(of1)){
            System.out.println("Making Dir: " + configDir.getAbsolutePath() + " RESULT: " + configDir.mkdir());
        }

        if(!Files.exists(of)){
            try {
                if(jsonFile.createNewFile()) {
                    try (FileOutputStream str = new FileOutputStream(of.toString())) {
                        byte[] values = DefaultVoxelValues.values.getBytes(StandardCharsets.UTF_8);
                        str.write(values);
                        str.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            savedJson = new String(Files.readAllBytes(of));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!savedJson.equals("")){
            JsonObject object = JsonParser.parseString(savedJson).getAsJsonObject();
            Type type = new TypeToken<Map<VoxelsSerializers.JsonItemStack, Long>>() {}.getType();
            Map<VoxelsSerializers.JsonItemStack, Long> map = gson.fromJson(object, type);
            map.remove(null);

            VoxelMappingHelper.voxelValuesHashMap.putAll(map);
        }
    }

    public static void save(){
        Path of = Path.of("config", "voxels", "saved_voxel_values.json");
        File jsonFile = of.toFile();
        Type type = new TypeToken<Map<VoxelsSerializers.JsonItemStack, Long>>() {}.getType();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
            gson.toJson(VoxelMappingHelper.voxelValuesHashMap, type, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
