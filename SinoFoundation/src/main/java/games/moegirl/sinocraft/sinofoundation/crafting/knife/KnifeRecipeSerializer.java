package games.moegirl.sinocraft.sinofoundation.crafting.knife;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.serializer.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.crafting.serializer.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class KnifeRecipeSerializer extends AbstractRecipeSerializer<KnifeRecipe> {

    public static final KnifeRecipeSerializer INSTANCE = new KnifeRecipeSerializer();

    KnifeRecipeSerializer() {
    }

    @Override
    public void toJson(JsonObject json, KnifeRecipe recipe) {
        json.add("block", BlockIngredient.toJson(recipe.block));
        json.addProperty("count", recipe.count);
        RecipeSerializers.write(json, "result", recipe.blockResult);
        RecipeSerializers.write(json, "drop", ItemStack.CODEC, recipe.getResultItem());
    }

    @Override
    public KnifeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        BlockIngredient<?> block = BlockIngredient.fromJson(json.getAsJsonObject("block"));
        int count = json.get("count").getAsInt();
        Block result = RecipeSerializers.readBlock(json, "result");
        ItemStack drop = RecipeSerializers.read(ItemStack.CODEC, json, "drop").orElseThrow();
        return new KnifeRecipe(recipeId, drop, block, count, result);
    }

    @Override
    public KnifeRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack drop = buffer.readItem();
        BlockIngredient<?> block = BlockIngredient.fromNetwork(buffer);
        int count = buffer.readVarInt();
        Block blockResult = RecipeSerializers.readBlock(buffer);
        return new KnifeRecipe(recipeId, drop, block, count, blockResult);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, KnifeRecipe recipe) {
        buffer.writeItemStack(recipe.getResultItem(), false);
        BlockIngredient.toNetwork(buffer, recipe.block);
        buffer.writeVarInt(recipe.count);
        RecipeSerializers.write(buffer, recipe.blockResult);
    }
}
