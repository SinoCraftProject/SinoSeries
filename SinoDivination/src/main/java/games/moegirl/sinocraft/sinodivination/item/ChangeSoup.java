package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 变荑汤
 */
public class ChangeSoup extends Item {

    public ChangeSoup() {
        super(new Item.Properties().stacksTo(1).craftRemainder(Items.BOWL));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            BlockPos pos = context.getClickedPos();
            Level level = context.getLevel();
            BlockState bs = level.getBlockState(pos);
            SimpleContainer container = new SimpleContainer(new ItemStack(bs.getBlock()));
            return level.getRecipeManager()
                    .getRecipeFor(SDRecipes.CHANGE_SOUP.recipeType(), container, level)
                    .map(recipe -> {
                        // 更换方块
                        level.setBlock(pos, recipe.getResult(), 2);
                        // 移除物品
                        Player player = context.getPlayer();
                        if (player != null && !player.isCreative()) {
                            player.setItemInHand(context.getHand(), new ItemStack(Items.BOWL));
                        }
                        return InteractionResult.SUCCESS;
                    })
                    .orElseGet(() -> super.useOn(context));
        }
        return super.useOn(context);
    }
}
