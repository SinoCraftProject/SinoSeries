package games.moegirl.sinocraft.sinofoundation.crafting.bamboo_plaque;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.AbstractRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class BambooPlaqueRecipeSerializer extends AbstractRecipeSerializer<BambooPlaqueRecipe> {
    public static final BambooPlaqueRecipeSerializer INSTANCE = new BambooPlaqueRecipeSerializer();

    @Override
    public void toJson(JsonObject pJson, BambooPlaqueRecipe pRecipe) {

    }

    @Override
    public BambooPlaqueRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        return null;
    }

    @Override
    public @Nullable BambooPlaqueRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BambooPlaqueRecipe recipe) {

    }
}
