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
 * 多个 BlockIngredient 同时满足时匹配
 *
 * @author luqin2007
 */
public class AndBlockIngredient extends BlockIngredient<AndBlockIngredient> {

    public static final Serializer<AndBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, AndBlockIngredient value) {
            JsonArray array = new JsonArray(value.ingredients.size());
            for (BlockIngredient<?> ingredient : value.ingredients) {
                array.add(BlockIngredient.toJson(ingredient));
            }
            json.add("blocks", array);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AndBlockIngredient value) {
            buffer.writeVarInt(value.ingredients.size());
            for (BlockIngredient<?> ingredient : value.ingredients) {
                BlockIngredient.toNetwork(buffer, ingredient);
            }
        }

        @Override
        public AndBlockIngredient fromJson(JsonObject obj) {
            JsonArray array = obj.getAsJsonArray("blocks");
            BlockIngredient<?>[] ingredients = new BlockIngredient[array.size()];
            for (int i = 0; i < array.size(); i++) {
                ingredients[i] = BlockIngredient.fromJson(array.get(i).getAsJsonObject());
            }
            return new AndBlockIngredient(ingredients);
        }

        @Override
        public AndBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            int count = buffer.readVarInt();
            BlockIngredient<?>[] ingredients = new BlockIngredient[count];
            for (int i = 0; i < count; i++) {
                ingredients[i] = BlockIngredient.fromNetwork(buffer);
            }
            return new AndBlockIngredient(ingredients);
        }
    };
    public static final BlockIngredientType<AndBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "and"), SERIALIZER);

    private final List<BlockIngredient<?>> ingredients;

    public AndBlockIngredient(BlockIngredient<?>[] ingredients) {
        super(TYPE);
        this.ingredients = List.of(ingredients);
    }

    @Override
    public boolean test(BlockState blockState) {
        return ingredients.stream().allMatch(p -> p.test(blockState));
    }
}
