package gamma02.voxels;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.HashMap;
import java.util.Optional;

public class EasyRecipieGenerator {

    public static void main(String[] args){

    }



    public class Recipe<T extends net.minecraft.world.item.crafting.Recipe<?>>{
        public ResourceLocation type;
        private String[] patternInput;
        private HashMap<Character, ResourceLocation> keys;
        private ResourceLocation output;
        private int outputAmount;
        private Optional<String> group;

        public Recipe(ResourceLocation type){
            this.type = type;
        }


        public String[] getPatternInput() {
            return patternInput;
        }


        public Recipe<T> addToKeys(char c, ResourceLocation l){
            keys.put(c, l);
            return this;
        }

        public Recipe<T> setPatternInput(String[] patternInput) {
            this.patternInput = patternInput;
            return this;
        }

        public Recipe<T> setOutput(ResourceLocation location){
            this.output = location;
            return this;
        }

        public Recipe<T> setOutputAmount(int amount){
            this.outputAmount = amount;
            return this;
        }


        public Recipe<T> setGroup(String group) {
            this.group = Optional.of(group);
            return this;
        }

        public JsonObject getRecipeJson(){
            JsonObject object = new JsonObject();
            object.addProperty("type", this.type.toString());
            this.group.ifPresent(s -> object.addProperty("group", s));


            //todo finish this :) <- im going to work on items now, good bye little project of ine
            return object;
        }
    }
}
