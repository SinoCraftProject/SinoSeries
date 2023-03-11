package games.moegirl.sinocraft.sinocore.old.crafting;

import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {

    protected final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public abstract void toJson(JsonObject pJson, T pRecipe);

    public Class<T> recipeClass() {
        return type;
    }
}
