package games.moegirl.sinocraft.sinocore.old.api.crafting;

import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class SimpleFinishedRecipe<S extends AbstractRecipeSerializer<R>, R extends Recipe<?>> implements FinishedRecipe {

    private final S serializer;
    private final R recipe;

    public SimpleFinishedRecipe(S serializer, R recipe) {
        this.serializer = serializer;
        this.recipe = recipe;
    }

    @Override
    public void serializeRecipeData(JsonObject pJson) {
        serializer.toJson(pJson, recipe);
    }

    @Override
    public ResourceLocation getId() {
        return recipe.getId();
    }

    @Override
    public RecipeSerializer<?> getType() {
        return serializer;
    }

    @Nullable
    @Override
    public JsonObject serializeAdvancement() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementId() {
        return null;
    }
}
