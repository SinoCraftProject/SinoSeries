package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author luqin2007
 */
public class TypeBlockIngredient extends BlockIngredient<TypeBlockIngredient> {

    public static final Serializer<TypeBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, TypeBlockIngredient value) {
            json.addProperty("block", ForgeRegistries.BLOCKS.getKey(value.block).toString());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TypeBlockIngredient value) {
            buffer.writeRegistryId(ForgeRegistries.BLOCKS, value.block);
        }

        @Override
        public TypeBlockIngredient fromJson(JsonObject obj) {
            return new TypeBlockIngredient(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(obj.getAsJsonPrimitive("block").getAsString())));
        }

        @Override
        public TypeBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            return new TypeBlockIngredient(buffer.readRegistryId());
        }
    };

    public static final BlockIngredientType<TypeBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "block"), SERIALIZER);

    private final Block block;

    public TypeBlockIngredient(Block block) {
        super(TYPE);
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean test(BlockState blockState) {
        return blockState.is(block);
    }
}
