package games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient;

import com.google.gson.JsonObject;
import games.moegirl.sinocraft.sinocore.SinoCore;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.Serializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author luqin2007
 */
public class TagBlockIngredient extends BlockIngredient<TagBlockIngredient> {

    public static final Serializer<TagBlockIngredient> SERIALIZER = new Serializer<>() {
        @Override
        public void toJson(JsonObject json, TagBlockIngredient value) {
            json.addProperty("tag", value.tag.location().toString());
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TagBlockIngredient value) {
            buffer.writeResourceLocation(value.tag.location());
        }

        @Override
        public TagBlockIngredient fromJson(JsonObject obj) {
            ResourceLocation name = new ResourceLocation(obj.getAsJsonPrimitive("tag").getAsString());
            return new TagBlockIngredient(BlockTags.create(name));
        }

        @Override
        public TagBlockIngredient fromNetwork(FriendlyByteBuf buffer) {
            return new TagBlockIngredient(BlockTags.create(buffer.readResourceLocation()));
        }
    };
    public static final BlockIngredientType<TagBlockIngredient> TYPE = new BlockIngredientType<>(new ResourceLocation(SinoCore.MODID, "tag"), SERIALIZER);

    private final TagKey<Block> tag;

    public TagBlockIngredient(TagKey<Block> tag) {
        super(TYPE);
        this.tag = tag;
    }

    @Override
    public boolean test(BlockState blockState) {
        return blockState.is(tag);
    }
}
