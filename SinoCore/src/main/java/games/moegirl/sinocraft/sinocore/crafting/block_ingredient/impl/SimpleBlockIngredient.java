package games.moegirl.sinocraft.sinocore.crafting.block_ingredient.impl;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredientType;
import games.moegirl.sinocraft.sinocore.crafting.serializer.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 匹配特定方块
 *
 * @author luqin2007
 */
public class SimpleBlockIngredient extends BlockIngredient<SimpleBlockIngredient> {

    public static final Serializer<SimpleBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, SimpleBlockIngredient value) {
            json.addProperty("block", ForgeRegistries.BLOCKS.getKey(value.block).toString());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SimpleBlockIngredient value) {
            buffer.writeRegistryId(ForgeRegistries.BLOCKS, value.block);
        }

        @Override
        public SimpleBlockIngredient fromJson(JsonObject obj) {
            return new SimpleBlockIngredient(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(obj.getAsJsonPrimitive("block").getAsString())));
        }

        @Override
        public SimpleBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            return new SimpleBlockIngredient(buffer.readRegistryId());
        }
    };

    public static final BlockIngredientType<SimpleBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "block"), SERIALIZER);

    private final Block block;

    public SimpleBlockIngredient(Block block) {
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
