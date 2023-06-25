package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.crafting.fluid_ingredient.FluidIngredient;
import games.moegirl.sinocraft.sinocore.utility.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class KettlePotRecipeSerializer extends AbstractRecipeSerializer<KettlePotRecipe> {

    public static final KettlePotRecipeSerializer INSTANCE = new KettlePotRecipeSerializer();

    @Override
    public KettlePotRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        RecipeSerializers.requireKeys(pSerializedRecipe, SDRecipes.CHANGE_SOUP.name(), pRecipeId, "inputs", "result");
        JsonArray array = pSerializedRecipe.getAsJsonArray("inputs");
        KettlePotRecipe.IngredientEntry[] inputs = new KettlePotRecipe.IngredientEntry[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            Ingredient ingredient = Ingredient.fromJson(object);
            int count;
            if (object.has("count")) {
                count = object.get("count").getAsInt();
            } else {
                count = 1;
            }
            inputs[i] = new KettlePotRecipe.IngredientEntry(count, ingredient);
        }

        Ingredient container = Ingredient.fromJson(pSerializedRecipe.get("container"));
        FluidIngredient fluid = FluidIngredient.fromJson(pSerializedRecipe.getAsJsonObject("fluid"));
        ItemStack result = RecipeSerializers.read(ItemStack.CODEC, pSerializedRecipe, "result").orElseThrow();
        return new KettlePotRecipe(pRecipeId, inputs, container, fluid, result);
    }

    @Override
    public KettlePotRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        KettlePotRecipe.IngredientEntry[] inputs = new KettlePotRecipe.IngredientEntry[pBuffer.readVarInt()];
        for (int i = 0; i < inputs.length; i++) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int count = pBuffer.readVarInt();
            inputs[i] = new KettlePotRecipe.IngredientEntry(count, ingredient);
        }
        Ingredient container = Ingredient.fromNetwork(pBuffer);
        FluidIngredient fluid = FluidIngredient.fromNetwork(pBuffer);
        ItemStack result = pBuffer.readItem();
        return new KettlePotRecipe(pRecipeId, inputs, container, fluid, result);
    }

    @Override
    public void toJson(JsonObject pJson, KettlePotRecipe pRecipe) {
        JsonArray array = new JsonArray();
        for (KettlePotRecipe.IngredientEntry ingredient : pRecipe.inputs) {
            JsonObject json = ingredient.ingredient().toJson().getAsJsonObject();
            json.addProperty("count", ingredient.count());
            array.add(json);
        }
        pJson.add("inputs", array);
        pJson.add("container", pRecipe.container.toJson());
        pJson.add("fluid", FluidIngredient.write(new JsonObject(), pRecipe.fluid));
        RecipeSerializers.write(pJson, "result", ItemStack.CODEC, pRecipe.getResultItem());
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, KettlePotRecipe pRecipe) {
        pBuffer.writeVarInt(pRecipe.inputs.length);
        for (KettlePotRecipe.IngredientEntry input : pRecipe.inputs) {
            input.ingredient().toNetwork(pBuffer);
            pBuffer.writeVarInt(input.count());
        }
        pRecipe.container.toNetwork(pBuffer);
        FluidIngredient.write(pBuffer, pRecipe.fluid);
        pBuffer.writeItemStack(pRecipe.getResultItem(), false);
    }
}
