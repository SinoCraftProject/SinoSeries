package games.moegirl.sinocraft.sinofoundation.crafting.block_interact;

import games.moegirl.sinocraft.sinocore.crafting.SimpleRecipe;
import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinofoundation.crafting.SFDRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * @author luqin2007
 */
public class BlockInteractRecipe extends SimpleRecipe<BlockInteractRecipeContainer, BlockInteractRecipe, BlockInteractRecipeSerializer> {

    final Ingredient inputItem;
    final BlockIngredient<?> inputBlock;
    // 消耗的耐久
    final int damage;
    // 消耗个数
    final int count;

    // 使用后的 BlockState
    // null 表示方块不发生变化
    @Nullable
    private final BlockState outputBlock;

    public static BlockInteractRecipeBuilder builder(ResourceLocation id) {
        return new BlockInteractRecipeBuilder(id);
    }

    public static BlockInteractRecipeBuilder builder(ItemStack output) {
        return new BlockInteractRecipeBuilder(ForgeRegistries.ITEMS.getKey(output.getItem()))
                .output(output);
    }

    public BlockInteractRecipe(ResourceLocation id, ItemStack output, @Nullable BlockState convert,
                               Ingredient input, BlockIngredient<?> target, int damage, int count) {
        super(SFDRecipes.BLOCK_INTERACT_RECIPE, id, 2, output);
        this.inputItem = input;
        this.inputBlock = target;
        this.damage = damage;
        this.count = count;
        this.outputBlock = convert;
    }

    @Override
    public boolean matches(BlockInteractRecipeContainer container, Level level) {
        ItemStack item = container.itemStack;
        return inputBlock.test(container.blockState)
                && inputItem.test(item)
                && (count == 0 || item.getCount() >= count)
                && (damage == 0 || (item.isDamageableItem() && item.getMaxDamage() - item.getDamageValue() >= damage));
    }

    @Nullable
    public BlockState getOutputBlock() {
        return outputBlock;
    }

    public int getCount() {
        return count;
    }

    public int getDamage() {
        return damage;
    }
}
