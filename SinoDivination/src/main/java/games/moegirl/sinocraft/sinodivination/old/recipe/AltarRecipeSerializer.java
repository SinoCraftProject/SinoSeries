package games.moegirl.sinocraft.sinodivination.old.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.AbstractRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public final class AltarRecipeSerializer extends AbstractRecipeSerializer<AltarRecipe> {

    public static final AltarRecipeSerializer INSTANCE = new AltarRecipeSerializer();

    @Override
    public void toJson(JsonObject jsonObject, AltarRecipe altarRecipe) {
        JsonArray sacrificialVessels = new JsonArray();
        for (Item sacrificialVessel : altarRecipe.sacrificialVessels) {
            if (sacrificialVessel == Items.AIR) {
                continue;
            }
            sacrificialVessels.add(ForgeRegistries.ITEMS.getKey(sacrificialVessel).toString());
        }
        jsonObject.add("sacrificialVessels", sacrificialVessels);
        jsonObject.add("base", altarRecipe.base.toJson());
        jsonObject.addProperty("tick", altarRecipe.tick);
        RecipeSerializers.write(jsonObject, "result", ItemStack.CODEC, altarRecipe.getResultItem());
    }

    @Override
    public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        RecipeSerializers.requireKeys(pSerializedRecipe, SDRecipes.ALTAR.name(), pRecipeId, "base", "result");
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        if (pSerializedRecipe.has("sacrificialVessels")) {
            JsonArray array = pSerializedRecipe.getAsJsonArray("sacrificialVessels");
            int count = Math.min(4, array.size());
            for (int i = 0; i < count; i++) {
                sacrificialVessels[i] = ForgeRegistries.ITEMS.getDelegateOrThrow(new ResourceLocation(array.get(i).getAsString())).get();
            }
        }
        Ingredient base = Ingredient.fromJson(pSerializedRecipe.get("base"));
        int tick = pSerializedRecipe.get("tick").getAsInt();
        ItemStack result = RecipeSerializers.read(ItemStack.CODEC, pSerializedRecipe, "result").orElseThrow();
        return new AltarRecipe(pRecipeId, sacrificialVessels, base, tick, result);
    }

    @Override
    public AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        Item[] sacrificialVessels = new Item[4];
        Arrays.fill(sacrificialVessels, Items.AIR);
        int count = Math.min(4, pBuffer.readByte());
        for (int i = 0; i < count; i++) {
            sacrificialVessels[i] = Item.byId(pBuffer.readVarInt());
        }
        Ingredient base = Ingredient.fromNetwork(pBuffer);
        int tick = pBuffer.readVarInt();
        ItemStack result = pBuffer.readItem();
        return new AltarRecipe(pRecipeId, sacrificialVessels, base, tick, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
        int count = 0;
        for (Item sacrificialVessel : pRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                count++;
            }
        }
        pBuffer.writeByte(count);
        for (Item sacrificialVessel : pRecipe.sacrificialVessels) {
            if (sacrificialVessel != Items.AIR) {
                pBuffer.writeVarInt(Item.getId(sacrificialVessel));
            }
        }
        pRecipe.base.toNetwork(pBuffer);
        pBuffer.writeVarInt(pRecipe.tick);
        pBuffer.writeItemStack(pRecipe.getResultItem(), false);
    }
}
