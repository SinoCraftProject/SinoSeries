package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.utility.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class CarvingTableRecipeSerializer extends AbstractRecipeSerializer<CarvingTableRecipe> {

    public static final CarvingTableRecipeSerializer INSTANCE = new CarvingTableRecipeSerializer();

    private CarvingTableRecipeSerializer() {
    }

    @Override
    public void toJson(JsonObject pJson, CarvingTableRecipe pRecipe) {
        if (pRecipe.originData == null) {
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredients.add(ingredient.toJson());
            }
            pJson.add("ingredients", ingredients);
            JsonArray keys = new JsonArray();
            for (int[] input : pRecipe.inputs) {
                for (int i : input) {
                    keys.add(i);
                }
            }
            pJson.add("keys", keys);
        } else {
            CarvingTableRecipe.OriginData originData = pRecipe.originData;
            JsonArray patterns = new JsonArray();
            for (String pattern : originData.patterns()) {
                patterns.add(pattern);
            }
            pJson.add("pattern", patterns);
            JsonObject keys = new JsonObject();
            char[] ks = originData.keys();
            Ingredient[] is = originData.ingredients();
            for (int i = 0; i < ks.length; i++) {
                String key = Character.toString(ks[i]);
                JsonElement ingredient = is[i].toJson();
                keys.add(key, ingredient);
            }
            pJson.add("key", keys);
        }
        if (!pRecipe.dye.isEmpty()) {
            pJson.add("dye", pRecipe.dye.toJson());
        }
        RecipeSerializers.write(pJson, "result", ItemStack.CODEC, pRecipe.getResultItem());
    }

    @Override
    public CarvingTableRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        ItemStack result = RecipeSerializers.read(ItemStack.CODEC, serializedRecipe, "result").orElseThrow();
        Ingredient dye;
        if (serializedRecipe.has("dye")) {
            dye = Ingredient.fromJson(serializedRecipe.get("dye"));
        } else {
            dye = Ingredient.EMPTY;
        }
        if (serializedRecipe.has("pattern") && serializedRecipe.has("key")) {
            CarvingTableRecipeBuilder builder = new CarvingTableRecipeBuilder(recipeId, result).dye(dye);
            JsonArray patterns = serializedRecipe.getAsJsonArray("pattern");
            JsonObject keys = serializedRecipe.getAsJsonObject("key");
            for (JsonElement pattern : patterns) {
                builder.pattern(pattern.getAsString());
            }
            for (String s : keys.keySet()) {
                builder.define(s.charAt(0), Ingredient.fromJson(keys.getAsJsonObject(s)));
            }
            return builder.build();
        } else {
            Ingredient[] ingredients;
            int[][] inputs = new int[][]{
                    new int[]{16, 16, 16, 16},
                    new int[]{16, 16, 16, 16},
                    new int[]{16, 16, 16, 16},
                    new int[]{16, 16, 16, 16}};
            if (serializedRecipe.has("ingredients")) {
                JsonArray is = serializedRecipe.getAsJsonArray("ingredients");
                JsonArray ks = serializedRecipe.getAsJsonArray("keys");
                ingredients = new Ingredient[is.size()];
                for (int i = 0; i < is.size(); i++) {
                    ingredients[i] = Ingredient.fromJson(is.get(i));
                }
                for (int i = 0; i < ks.size(); i++) {
                    int r = i / 4;
                    int c = i % 4;
                    inputs[r][c] = ks.get(i).getAsInt();
                }
            } else {
                ingredients = new Ingredient[0];
            }
            return new CarvingTableRecipe(recipeId, ingredients, inputs, dye, result, null);
        }
    }

    @Nullable
    @Override
    public CarvingTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Ingredient[] ingredients = new Ingredient[buffer.readByte()];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = Ingredient.fromNetwork(buffer);
        }
        int[][] inputs = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                inputs[i][j] = buffer.readByte();
            }
        }
        ItemStack result = buffer.readItem();
        Ingredient dye = buffer.readBoolean() ? Ingredient.fromNetwork(buffer) : Ingredient.EMPTY;
        return new CarvingTableRecipe(recipeId, ingredients, inputs, dye, result, null);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, CarvingTableRecipe recipe) {
        buffer.writeByte(recipe.ingredients.length);
        for (Ingredient ingredient : recipe.ingredients) {
            ingredient.toNetwork(buffer);
        }
        for (int[] input : recipe.inputs) {
            for (int i : input) {
                buffer.writeByte(i);
            }
        }
        buffer.writeItem(recipe.getResultItem());
        boolean hasDye = !recipe.dye.isEmpty();
        buffer.writeBoolean(hasDye);
        if (hasDye) {
            recipe.dye.toNetwork(buffer);
        }
    }
}
