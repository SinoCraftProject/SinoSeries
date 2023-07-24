package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 合并两个 BlockIngredient，使用与关系
 *
 * @author luqin2007
 */
public class AndBlockIngredient extends BlockIngredient<AndBlockIngredient> {

    public static final Serializer<AndBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, AndBlockIngredient value) {
            json.add("a", BlockIngredient.toJson(value.a));
            json.add("b", BlockIngredient.toJson(value.b));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AndBlockIngredient value) {
            BlockIngredient.toNetwork(buffer, value.a);
            BlockIngredient.toNetwork(buffer, value.b);
        }

        @Override
        public AndBlockIngredient fromJson(JsonObject obj) {
            BlockIngredient<?> a = BlockIngredient.fromJson(obj.getAsJsonObject("a"));
            BlockIngredient<?> b = BlockIngredient.fromJson(obj.getAsJsonObject("b"));
            return new AndBlockIngredient(a, b);
        }

        @Override
        public AndBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            BlockIngredient<?> a = BlockIngredient.fromNetwork(buffer);
            BlockIngredient<?> b = BlockIngredient.fromNetwork(buffer);
            return new AndBlockIngredient(a, b);
        }
    };
    public static final BlockIngredientType<AndBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "and"), SERIALIZER);

    private final BlockIngredient<?> a, b;

    public AndBlockIngredient(BlockIngredient<?> a, BlockIngredient<?> b) {
        super(TYPE);
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean test(BlockState blockState) {
        return a.test(blockState) && b.test(blockState);
    }
}
