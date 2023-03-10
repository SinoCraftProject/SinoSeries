package games.moegirl.sinocraft.sinocore.old.crafting.recipe.base;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.api.utility.json.JsonUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 **/
public class BaseSerializer<C extends Container, RECIPE extends BaseRecipe<C>>
        extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<RECIPE> {

    Class<RECIPE> recipeClass;

    public BaseSerializer(Class<RECIPE> recipeClass) {
        this.recipeClass = recipeClass;
    }

    @Nullable
    @Override
    public RECIPE fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        return JsonUtils.INSTANCE.getNormal().fromJson(pBuffer.readUtf(), recipeClass)
                .setId(pRecipeId).setSerializer(this);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, RECIPE pRecipe) {
        pBuffer.writeUtf(JsonUtils.INSTANCE.getNormal().toJson(pRecipe));
    }

    @Override
    public RECIPE fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return JsonUtils.INSTANCE.getNormal().fromJson(pSerializedRecipe, recipeClass)
                .setId(pRecipeId).setSerializer(this);
    }

    public JsonObject toJson(RECIPE pRecipe) {
        return JsonUtils.INSTANCE.getNormal().toJsonTree(pRecipe).getAsJsonObject();
    }
}
