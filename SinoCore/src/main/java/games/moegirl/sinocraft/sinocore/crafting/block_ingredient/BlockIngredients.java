package games.moegirl.sinocraft.sinocore.crafting.block_ingredient;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luqin2007
 */
public class BlockIngredients {

    private static final Map<ResourceLocation, BlockIngredientType<?>> TYPES = new HashMap<>();

    public static void register(BlockIngredientType<?> type) {
        TYPES.put(type.name(), type);
    }

    static {
        register(NoneBlockIngredient.TYPE);
        register(AnyBlockIngredient.TYPE);
        register(AndBlockIngredient.TYPE);
        register(OrBlockIngredient.TYPE);

        register(TypeBlockIngredient.TYPE);
        register(BlockStateIngredient.TYPE);
        register(TagBlockIngredient.TYPE);
    }

    @Nullable
    public static BlockIngredientType<?> type(ResourceLocation name) {
        return TYPES.get(name);
    }

    public static AnyBlockIngredient any() {
        return new AnyBlockIngredient();
    }

    public static NoneBlockIngredient none() {
        return new NoneBlockIngredient();
    }

    public static AndBlockIngredient and(BlockIngredient<?> a, BlockIngredient<?> b) {
        return new AndBlockIngredient(a, b);
    }

    public static OrBlockIngredient or(BlockIngredient<?> a, BlockIngredient<?> b) {
        return new OrBlockIngredient(a, b);
    }

    public static TypeBlockIngredient block(Block block) {
        return new TypeBlockIngredient(block);
    }

    public static <T extends Comparable<T>> BlockStateIngredient state(Property<T> property, T value) {
        return new BlockStateIngredient().property(property, value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static AndBlockIngredient block(BlockState bs) {
        BlockStateIngredient ingredient = new BlockStateIngredient();
        for (Property property : bs.getProperties()) {
            ingredient.property(property, bs.getValue(property));
        }
        return block(bs.getBlock()).and(ingredient);
    }

    public static TagBlockIngredient tag(TagKey<Block> tag) {
        return new TagBlockIngredient(tag);
    }

}
