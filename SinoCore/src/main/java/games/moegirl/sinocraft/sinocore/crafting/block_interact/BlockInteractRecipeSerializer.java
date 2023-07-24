package games.moegirl.sinocraft.sinocore.crafting.block_interact;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.AbstractRecipeSerializer;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient.BlockIngredient;
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
    public void toJson(JsonObject json, BlockInteractRecipe recipe) {
        json.add("source", BlockIngredient.toJson(recipe.source));
        json.add("tool", recipe.tool.toJson());
        if (!recipe.getResultItem().isEmpty()) {
            RecipeSerializers.write(json, "output", ItemStack.CODEC, recipe.getResultItem());
        }
        if (recipe.getDestination() != null) {
            RecipeSerializers.write(json, "destination", BlockState.CODEC, recipe.getDestination());
        }
        if (recipe.damage != 0) {
            json.addProperty("damage", recipe.damage);
        }
        if (recipe.count != 0) {
            json.addProperty("count", recipe.count);
        }
    }

    @Override
    public BlockInteractRecipe fromJson(ResourceLocation id, JsonObject serializedRecipe) {
        ItemStack output = serializedRecipe.has("output")
                ? RecipeSerializers.read(ItemStack.CODEC, serializedRecipe, "output").orElseThrow()
                : ItemStack.EMPTY;
        BlockState destination = serializedRecipe.has("destination")
                ? RecipeSerializers.read(BlockState.CODEC, serializedRecipe, "destination").orElseThrow()
                : null;
        Ingredient tool = Ingredient.fromJson(serializedRecipe.get("tool"));
        BlockIngredient<?> source = BlockIngredient.fromJson(serializedRecipe.getAsJsonObject("source"));
        int damage = serializedRecipe.has("damage") ? serializedRecipe.getAsJsonPrimitive("damage").getAsInt() : 0;
        int count = serializedRecipe.has("count") ? serializedRecipe.getAsJsonPrimitive("count").getAsInt() : 0;
        return new BlockInteractRecipe(id, output, tool, source, destination, damage, count);
    }

    @Override
    @Nullable
    public BlockInteractRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        int damage = buffer.readVarInt();
        int count = buffer.readVarInt();
        ItemStack output = buffer.readItem();
        boolean hasBlock = buffer.readBoolean();
        BlockState destination = null;
        if (hasBlock) {
            Block block = buffer.readRegistryId();
            destination = block.defaultBlockState();
            int propertyCount = buffer.readVarInt();
            for (int i = 0; i < propertyCount; i++) {
                String keyName = buffer.readUtf();
                String valueName = buffer.readUtf();
                StateDefinition<Block, BlockState> definition = block.getStateDefinition();
                Property property = definition.getProperty(keyName);
                assert property != null;
                Optional<Comparable> value = property.getValue(valueName);
                assert value.isPresent();
                destination = destination.setValue(property, value.get());
            }
        }
        Ingredient tool = Ingredient.fromNetwork(buffer);
        BlockIngredient<?> source = BlockIngredient.fromNetwork(buffer);
        return new BlockInteractRecipe(id, output, tool, source, destination, damage, count);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BlockInteractRecipe recipe) {
        buffer.writeVarInt(recipe.damage);
        buffer.writeVarInt(recipe.count);
        buffer.writeItemStack(recipe.getResultItem(), false);
        BlockState convert = recipe.getDestination();
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
        recipe.tool.toNetwork(buffer);
        BlockIngredient.toNetwork(buffer, recipe.source);
    }
}
