package games.moegirl.sinocraft.sinocore.crafting;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.utility.IGenericClass;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * 某类配方的序列化和反序列化工具，增加了对 Json 的序列化方法
 *
 * @author luqin2007
 */
public abstract class AbstractRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T>, IGenericClass {

    protected final Class<T> type = getGenericClass(0);

    public abstract void toJson(JsonObject pJson, T pRecipe);

    public Class<T> recipeClass() {
        return type;
    }
}
