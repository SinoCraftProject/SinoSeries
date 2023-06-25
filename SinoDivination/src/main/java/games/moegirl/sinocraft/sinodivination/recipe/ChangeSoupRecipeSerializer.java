package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.utility.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public final class ChangeSoupRecipeSerializer extends AbstractRecipeSerializer<ChangeSoupRecipe> {

    public static final ChangeSoupRecipeSerializer INSTANCE = new ChangeSoupRecipeSerializer();

    @Override
    public ChangeSoupRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        RecipeSerializers.requireKeys(pSerializedRecipe, SDRecipes.CHANGE_SOUP.name(), pRecipeId, "input", "result");
        Block input = RecipeSerializers.readBlock(pSerializedRecipe, "input");
        Block result = RecipeSerializers.readBlock(pSerializedRecipe, "result");
        return new ChangeSoupRecipe(pRecipeId, input, result);
    }

    @Override
    public ChangeSoupRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        Block input = RecipeSerializers.readBlock(pBuffer);
        Block result = RecipeSerializers.readBlock(pBuffer);
        return new ChangeSoupRecipe(pRecipeId, input, result);
    }

    @Override
    public void toJson(JsonObject pJson, ChangeSoupRecipe pRecipe) {
        RecipeSerializers.write(pJson, "input", pRecipe.input);
        RecipeSerializers.write(pJson, "result", pRecipe.result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, ChangeSoupRecipe pRecipe) {
        RecipeSerializers.write(pBuffer, pRecipe.input);
        RecipeSerializers.write(pBuffer, pRecipe.result);
    }
}
