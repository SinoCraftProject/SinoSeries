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
 * 允许任意方块通过
 *
 * @author luqin2007
 */
public class AnyBlockIngredient extends BlockIngredient<AnyBlockIngredient> {

    public static final Serializer<AnyBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, AnyBlockIngredient value) {
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AnyBlockIngredient value) {
        }

        @Override
        public AnyBlockIngredient fromJson(JsonObject obj) {
            return new AnyBlockIngredient();
        }

        @Override
        public AnyBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            return new AnyBlockIngredient();
        }
    };

    public static final BlockIngredientType<AnyBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "any"), SERIALIZER);

    public AnyBlockIngredient() {
        super(TYPE);
    }

    @Override
    public boolean test(BlockState blockState) {
        return true;
    }

}
