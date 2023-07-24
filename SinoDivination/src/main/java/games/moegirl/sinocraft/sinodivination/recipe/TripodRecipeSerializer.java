package games.moegirl.sinocraft.sinodivination.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.utility.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public final class TripodRecipeSerializer extends AbstractRecipeSerializer<TripodRecipe> {

    public static final TripodRecipeSerializer INSTANCE = new TripodRecipeSerializer();

    @Override
    public void toJson(JsonObject jsonObject, TripodRecipe tripodRecipe) {
        JsonArray sacrificialVessels = new JsonArray();
        for (Item sacrificialVessel : tripodRecipe.sacrificialVessels) {
            if (sacrificialVessel == Items.AIR) {
                continue;
            }
            sacrificialVessels.add(ForgeRegistries.ITEMS.getKey(sacrificialVessel).toString());
        }
        jsonObject.add("sacrificialVessels", sacrificialVessels);
        jsonObject.add("base", tripodRecipe.base.toJson());
        jsonObject.addProperty("tick", tripodRecipe.tick);
        RecipeSerializers.write(jsonObject, "result", ItemStack.CODEC, tripodRecipe.getResultItem());
    }

    @Override
    public TripodRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
        RecipeSerializers.requireKeys(jsonObject, SDRecipes.TRIPOD.name(), recipeId, "base", "result");
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        if (jsonObject.has("sacrificialVessels")) {
            JsonArray array = jsonObject.getAsJsonArray("sacrificialVessels");
            int count = Math.min(4, array.size());
            for (int i = 0; i < count; i++) {
                sacrificialVessels[i] = ForgeRegistries.ITEMS.getDelegateOrThrow(new ResourceLocation(array.get(i).getAsString())).get();
            }
        }
        Ingredient base = Ingredient.fromJson(jsonObject.get("base"));
        int tick = jsonObject.get("tick").getAsInt();
        ItemStack result = RecipeSerializers.read(ItemStack.CODEC, jsonObject, "result").orElseThrow();
        return new TripodRecipe(recipeId, sacrificialVessels, base, tick, result);
    }

    @Override
    public TripodRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        int count = Math.min(4, buffer.readByte());
        for (int i = 0; i < count; i++) {
            sacrificialVessels[i] = Item.byId(buffer.readVarInt());
        }
        Ingredient base = Ingredient.fromNetwork(buffer);
        int tick = buffer.readVarInt();
        ItemStack result = buffer.readItem();
        return new TripodRecipe(recipeId, sacrificialVessels, base, tick, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, TripodRecipe tripodRecipe) {
        int count = 0;
        for (Item sacrificialVessel : tripodRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                count++;
            }
        }
        buffer.writeByte(count);
        for (Item sacrificialVessel : tripodRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                buffer.writeVarInt(Item.getId(sacrificialVessel));
            }
        }
        tripodRecipe.base.toNetwork(buffer);
        buffer.writeVarInt(tripodRecipe.tick);
        buffer.writeItemStack(tripodRecipe.getResultItem(), false);
    }
}
