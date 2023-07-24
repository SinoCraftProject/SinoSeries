package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import games.moegirl.sinocraft.sinocore.utility.Self;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 * 方块测试
 * @author luqin2007
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BlockIngredient<T extends BlockIngredient<T>> implements Predicate<BlockState>, Self<T> {

    private final BlockIngredientType<T> type;

    public BlockIngredient(BlockIngredientType<T> type) {
        this.type = type;
    }

    public AndBlockIngredient and(BlockIngredient<?> other) {
        return BlockIngredients.and(this, other);
    }

    public OrBlockIngredient or(BlockIngredient<?> other) {
        return BlockIngredients.or(this, other);
    }

    public static JsonObject toJson(BlockIngredient<?> value) {
        JsonObject object = new JsonObject();
        object.addProperty("id", value.type.name().toString());
        Serializer serializer = value.type.serializer();
        serializer.toJson(object, value);
        return object;
    }

    public static void toNetwork(FriendlyByteBuf buffer, BlockIngredient<?> value) {
        buffer.writeResourceLocation(value.type.name());
        Serializer serializer = value.type.serializer();
        serializer.toNetwork(buffer, value);
    }

    public static BlockIngredient<?> fromJson(JsonObject jsonObject) {
        ResourceLocation id = new ResourceLocation(jsonObject.getAsJsonPrimitive("id").getAsString());
        BlockIngredientType<?> type = BlockIngredients.type(id);
        if (type == null) {
            throw new NullPointerException("Unknown block ingredient type " + id);
        }
        return type.serializer().fromJson(jsonObject);
    }

    public static BlockIngredient<?> fromNetwork(FriendlyByteBuf buffer) {
        ResourceLocation id = buffer.readResourceLocation();
        BlockIngredientType<?> type = BlockIngredients.type(id);
        if (type == null) {
            throw new NullPointerException("Unknown block ingredient type " + id);
        }
        return type.serializer().fromNetwork(buffer);
    }
}
