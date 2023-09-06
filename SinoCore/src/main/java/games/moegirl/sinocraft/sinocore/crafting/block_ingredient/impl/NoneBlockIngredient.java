package games.moegirl.sinocraft.sinocore.crafting.block_ingredient.impl;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredientType;
import games.moegirl.sinocraft.sinocore.crafting.serializer.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class NoneBlockIngredient extends BlockIngredient<NoneBlockIngredient> {

    public static final Serializer<NoneBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, NoneBlockIngredient value) {
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, NoneBlockIngredient value) {
        }

        @Override
        public NoneBlockIngredient fromJson(JsonObject obj) {
            return new NoneBlockIngredient();
        }

        @Override
        public NoneBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            return new NoneBlockIngredient();
        }
    };

    public static final BlockIngredientType<NoneBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "none"), SERIALIZER);

    public NoneBlockIngredient() {
        super(TYPE);
    }

    @Override
    public boolean test(BlockState blockState) {
        return false;
    }
}
