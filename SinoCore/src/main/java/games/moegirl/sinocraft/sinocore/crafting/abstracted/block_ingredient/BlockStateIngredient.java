package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author luqin2007
 */
public class BlockStateIngredient extends BlockIngredient<BlockStateIngredient> {

    public static final Serializer<BlockStateIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, BlockStateIngredient value) {
            JsonObject properties = new JsonObject();
            value.properties.forEach(properties::addProperty);
            json.add("state", properties);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BlockStateIngredient value) {
            buffer.writeVarInt(value.properties.size());
            value.properties.forEach((k, v) -> {
                buffer.writeUtf(k);
                buffer.writeUtf(v);
            });
        }

        @Override
        public BlockStateIngredient fromJson(JsonObject obj) {
            BlockStateIngredient ingredient = new BlockStateIngredient();
            JsonObject state = obj.get("state").getAsJsonObject();
            for (String key : state.keySet()) {
                ingredient.properties.put(key, state.getAsJsonPrimitive(key).getAsString());
            }
            return ingredient;
        }

        @Override
        public BlockStateIngredient fromNetwork(FriendlyByteBuf buffer) {
            BlockStateIngredient ingredient = new BlockStateIngredient();
            int count = buffer.readVarInt();
            for (int i = 0; i < count; i++) {
                String property = buffer.readUtf();
                String value = buffer.readUtf();
                ingredient.properties.put(property, value);
            }
            return ingredient;
        }
    };
    public static final BlockIngredientType<BlockStateIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "blockstate"), SERIALIZER);

    private final Map<String, String> properties = new HashMap<>();

    public BlockStateIngredient() {
        super(TYPE);
    }

    public <T extends Comparable<T>> BlockStateIngredient property(Property<T> property, T value) {
        properties.put(property.getName(), property.getName(value));
        return this;
    }

    public BlockStateIngredient property(String property, String value) {
        properties.put(property, value);
        return this;
    }

    @Override
    public boolean test(BlockState blockState) {
        StateDefinition<Block, BlockState> definition = blockState.getBlock().getStateDefinition();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            Property<?> property = definition.getProperty(entry.getKey());
            assert property != null;
            if (!Objects.equals(property.getValue(entry.getValue()).orElse(null), blockState.getValue(property))) {
                return false;
            }
        }
        return true;
    }
}
