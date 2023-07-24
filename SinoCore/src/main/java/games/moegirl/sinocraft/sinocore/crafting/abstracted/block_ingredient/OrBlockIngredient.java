package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class OrBlockIngredient extends BlockIngredient<OrBlockIngredient> {

    public static final Serializer<OrBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, OrBlockIngredient value) {
            json.add("a", BlockIngredient.toJson(value.a));
            json.add("b", BlockIngredient.toJson(value.b));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, OrBlockIngredient value) {
            BlockIngredient.toNetwork(buffer, value.a);
            BlockIngredient.toNetwork(buffer, value.b);
        }

        @Override
        public OrBlockIngredient fromJson(JsonObject obj) {
            BlockIngredient<?> a = BlockIngredient.fromJson(obj.getAsJsonObject("a"));
            BlockIngredient<?> b = BlockIngredient.fromJson(obj.getAsJsonObject("b"));
            return new OrBlockIngredient(a, b);
        }

        @Override
        public OrBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            BlockIngredient<?> a = BlockIngredient.fromNetwork(buffer);
            BlockIngredient<?> b = BlockIngredient.fromNetwork(buffer);
            return new OrBlockIngredient(a, b);
        }
    };

    public static final BlockIngredientType<OrBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "or"), SERIALIZER);

    private final BlockIngredient<?> a, b;

    public OrBlockIngredient(BlockIngredient<?> a, BlockIngredient<?> b) {
        super(TYPE);
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean test(BlockState blockState) {
        return a.test(blockState) || b.test(blockState);
    }
}
