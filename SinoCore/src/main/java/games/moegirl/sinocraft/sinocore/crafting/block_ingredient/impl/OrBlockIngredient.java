package games.moegirl.sinocraft.sinocore.crafting.block_ingredient.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredientType;
import games.moegirl.sinocraft.sinocore.crafting.serializer.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 多个 BlockIngredient 中任意一个满足即可匹配
 *
 * @author luqin2007
 */
public class OrBlockIngredient extends BlockIngredient<OrBlockIngredient> {

    public static final Serializer<OrBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, OrBlockIngredient value) {
            JsonArray array = new JsonArray(value.ingredients.size());
            for (BlockIngredient<?> ingredient : value.ingredients) {
                array.add(BlockIngredient.toJson(ingredient));
            }
            json.add("blocks", array);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, OrBlockIngredient value) {
            buffer.writeVarInt(value.ingredients.size());
            for (BlockIngredient<?> ingredient : value.ingredients) {
                BlockIngredient.toNetwork(buffer, ingredient);
            }
        }

        @Override
        public OrBlockIngredient fromJson(JsonObject obj) {
            JsonArray array = obj.getAsJsonArray("blocks");
            BlockIngredient<?>[] ingredients = new BlockIngredient[array.size()];
            for (int i = 0; i < array.size(); i++) {
                ingredients[i] = BlockIngredient.fromJson(array.get(i).getAsJsonObject());
            }
            return new OrBlockIngredient(ingredients);
        }

        @Override
        public OrBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            int count = buffer.readVarInt();
            BlockIngredient<?>[] ingredients = new BlockIngredient[count];
            for (int i = 0; i < count; i++) {
                ingredients[i] = BlockIngredient.fromNetwork(buffer);
            }
            return new OrBlockIngredient(ingredients);
        }
    };

    public static final BlockIngredientType<OrBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "or"), SERIALIZER);

    private final List<BlockIngredient<?>> ingredients;

    public OrBlockIngredient(BlockIngredient<?>[] ingredients) {
        super(TYPE);
        this.ingredients = List.of(ingredients);
    }

    @Override
    public boolean test(BlockState blockState) {
        return ingredients.stream().anyMatch(p -> p.test(blockState));
    }
}
