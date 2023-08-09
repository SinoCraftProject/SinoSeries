package games.moegirl.sinocraft.sinocore.crafting.block_interact;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.SCRecipes;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.SimpleRecipe;
import games.moegirl.sinocraft.sinocore.crafting.abstracted.block_ingredient.BlockIngredient;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * @author luqin2007
 */
@Deprecated
public class BlockInteractRecipe extends SimpleRecipe<BlockInteractRecipeContainer, BlockInteractRecipe, BlockInteractRecipeSerializer> {

    final Ingredient tool;
    final BlockIngredient<?> source;
    // 消耗的耐久
    final int damage;
    // 消耗个数
    final int count;

    // 使用后的 BlockState
    // null 表示方块不发生变化
    @Nullable
    private final BlockState destination;

    public static BlockInteractRecipeBuilder builder(ResourceLocation id) {
        return new BlockInteractRecipeBuilder(new ResourceLocation(id.getNamespace(), "block_interact/" + id.getPath()));
    }

    public static BlockInteractRecipeBuilder builder(ItemStack output) {
        return new BlockInteractRecipeBuilder(ForgeRegistries.ITEMS.getKey(output.getItem()))
                .output(output);
    }

    public BlockInteractRecipe(ResourceLocation id, ItemStack output, Ingredient tool, BlockIngredient<?> source,
                               @Nullable BlockState destination, int damage, int count) {
        super(SCRecipes.BLOCK_INTERACT_RECIPE, id, 2, output);
        this.tool = tool;
        this.source = source;
        this.destination = destination;
        this.damage = damage;
        this.count = count;
    }

    @Override
    public boolean matches(BlockInteractRecipeContainer container, Level level) {
        var toolStack = container.getTool();
        return source.test(container.getSource())
                && tool.test(toolStack)
                && (getCount() == 0 || toolStack.getCount() >= getCount())
                && (getDamage() == 0 || (toolStack.isDamageableItem() && toolStack.getMaxDamage() - toolStack.getDamageValue() >= getDamage()));
    }

    @Override
    public ItemStack assemble(BlockInteractRecipeContainer container, RegistryAccess access) {
        var level = container.getLevel();
        var pos = container.getPos();
        var tool = container.getTool();

        if (!container.getPlayer().isCreative()) {
            if (getDamage() != 0 && tool.isDamageableItem()) {
                tool.setDamageValue(tool.getDamageValue() + getDamage());
            }

            if (getCount() != 0) {
                tool.shrink(getCount());
            }
        }

        ItemStack result = getResultItem(access);
        if (!result.isEmpty() && !container.getPlayer().addItem(result)) {
            Block.popResource(level, pos, result);
        }

        if (getDestination() != null) {
            var dest = getDestination();
            for (var property : level.getBlockState(pos).getValues().entrySet()) {
                dest = dest.trySetValue((Property) property.getKey(), (Comparable) property.getValue());
            }

            level.setBlock(pos, dest, Block.UPDATE_ALL);
        }

        return ItemStack.EMPTY;
    }

    @Nullable
    public BlockState getDestination() {
        return destination;
    }

    public int getCount() {
        return count;
    }

    public int getDamage() {
        return damage;
    }
}
