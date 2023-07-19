package games.moegirl.sinocraft.sinofoundation.crafting.block_interact;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.utility.RecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author luqin2007
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BlockInteractRecipeSerializer extends AbstractRecipeSerializer<BlockInteractRecipe> {

    public static final BlockInteractRecipeSerializer INSTANCE = new BlockInteractRecipeSerializer();

    private BlockInteractRecipeSerializer() {
    }

    @Override
    public void toJson(JsonObject pJson, BlockInteractRecipe pRecipe) {
        pJson.add("target", BlockIngredient.toJson(pRecipe.inputBlock));
        pJson.add("hold", pRecipe.inputItem.toJson());
        if (!pRecipe.getResultItem().isEmpty()) {
            RecipeSerializers.write(pJson, "output", ItemStack.CODEC, pRecipe.getResultItem());
        }
        if (pRecipe.getOutputBlock() != null) {
            RecipeSerializers.write(pJson, "convert", BlockState.CODEC, pRecipe.getOutputBlock());
        }
        if (pRecipe.damage != 0) {
            pJson.addProperty("damage", pRecipe.damage);
        }
        if (pRecipe.count != 0) {
            pJson.addProperty("count", pRecipe.count);
        }
    }

    @Override
    public BlockInteractRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
        ItemStack output = serializedRecipe.has("output")
                ? RecipeSerializers.read(ItemStack.CODEC, serializedRecipe, "output").orElseThrow()
                : ItemStack.EMPTY;
        BlockState convert = serializedRecipe.has("convert")
                ? RecipeSerializers.read(BlockState.CODEC, serializedRecipe, "convert").orElseThrow()
                : null;
        Ingredient input = Ingredient.fromJson(serializedRecipe.get("hold"));
        BlockIngredient<?> target = BlockIngredient.fromJson(serializedRecipe.getAsJsonObject("target"));
        int damage = serializedRecipe.has("damage") ? serializedRecipe.getAsJsonPrimitive("damage").getAsInt() : 0;
        int count = serializedRecipe.has("count") ? serializedRecipe.getAsJsonPrimitive("count").getAsInt() : 0;
        return new BlockInteractRecipe(recipeId, output, convert, input, target, damage, count);
    }

    @Override
    @Nullable
    public BlockInteractRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        int damage = buffer.readVarInt();
        int count = buffer.readVarInt();
        ItemStack output = buffer.readItem();
        boolean hasBlock = buffer.readBoolean();
        BlockState convert = null;
        if (hasBlock) {
            Block block = buffer.readRegistryId();
            convert = block.defaultBlockState();
            int propertyCount = buffer.readVarInt();
            for (int i = 0; i < propertyCount; i++) {
                String keyName = buffer.readUtf();
                String valueName = buffer.readUtf();
                StateDefinition<Block, BlockState> definition = block.getStateDefinition();
                Property property = definition.getProperty(keyName);
                assert property != null;
                Optional<Comparable> value = property.getValue(valueName);
                assert value.isPresent();
                convert = convert.setValue(property, value.get());
            }
        }
        Ingredient input = Ingredient.fromNetwork(buffer);
        BlockIngredient<?> target = BlockIngredient.fromNetwork(buffer);
        return new BlockInteractRecipe(recipeId, output, convert, input, target, damage, count);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BlockInteractRecipe recipe) {
        buffer.writeVarInt(recipe.damage);
        buffer.writeVarInt(recipe.count);
        buffer.writeItemStack(recipe.getResultItem(), false);
        BlockState convert = recipe.getOutputBlock();
        buffer.writeBoolean(convert != null);
        if (convert != null) {
            buffer.writeRegistryId(ForgeRegistries.BLOCKS, convert.getBlock());
            Block block = convert.getBlock();
            BlockState defState = block.defaultBlockState();
            StateDefinition<Block, BlockState> definition = block.getStateDefinition();
            Map<String, String> pp = new HashMap<>();
            for (Property property : definition.getProperties()) {
                if (!Objects.equals(defState.getValue(property), convert.getValue(property))) {
                    pp.put(property.getName(), property.getName(convert.getValue(property)));
                }
            }
            buffer.writeVarInt(pp.size());
            pp.forEach((k, v) -> {
                buffer.writeUtf(k);
                buffer.writeUtf(v);
            });
        }
        recipe.inputItem.toNetwork(buffer);
        BlockIngredient.toNetwork(buffer, recipe.inputBlock);
    }
}
