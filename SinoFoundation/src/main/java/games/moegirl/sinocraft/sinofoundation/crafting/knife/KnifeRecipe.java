package games.moegirl.sinocraft.sinofoundation.crafting.knife;

import games.moegirl.sinocraft.sinocore.crafting.block_ingredient.BlockIngredient;
import games.moegirl.sinocraft.sinocore.crafting.recipe.SimpleRecipe;
import games.moegirl.sinocraft.sinofoundation.crafting.SFDRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

/**
 * 小刀配方
 *
 * @author luqin2007
 */
public class KnifeRecipe extends SimpleRecipe<KnifeRecipeContainer, KnifeRecipe, KnifeRecipeSerializer> {

    public static KnifeRecipeBuilder builder(ResourceLocation name) {
        return new KnifeRecipeBuilder(name);
    }

    public static KnifeRecipeBuilder builder(Block result) {
        return new KnifeRecipeBuilder(ForgeRegistries.BLOCKS.getKey(result)).setResultBlock(result);
    }

    BlockIngredient<?> block;

    int count;
    Block blockResult;

    KnifeRecipe(ResourceLocation id, ItemStack output, BlockIngredient<?> block, int count, Block blockResult) {
        super(SFDRecipes.KNIFE_RECIPE, id, 0, output);
        this.block = block;
        this.count = count;
        this.blockResult = blockResult;
    }

    @Override
    @Nonnull
    public ItemStack getResultItem() {
        ItemStack resultItem = super.getResultItem();
        resultItem.setCount(count);
        return resultItem;
    }

    @Override
    public boolean matches(KnifeRecipeContainer container, Level level) {
        return block.test(level.getBlockState(container.pos));
    }

    @Override
    @Nonnull
    public ItemStack assemble(KnifeRecipeContainer container, RegistryAccess access) {
        Player player = container.player;
        InteractionHand hand = container.hand;
        Level level = container.level;
        BlockPos pos = container.pos;

        if (!player.isCreative()) {
            player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }

        ItemStack result = getResultItem(access);
        if (!player.addItem(result)) {
            Block.popResource(level, pos, result);
        }

        BlockState rb = blockResult.defaultBlockState();
        BlockState old = level.getBlockState(pos);
        for (Property property : old.getProperties()) {
            if (rb.hasProperty(property)) {
                rb = rb.setValue(property, old.getValue(property));
            }
        }
        level.setBlock(pos, rb, Block.UPDATE_ALL);

        return ItemStack.EMPTY;
    }

    public int getCount() {
        return count;
    }

    public Block getBlockResult() {
        return blockResult;
    }
}
