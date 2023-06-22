package games.moegirl.sinocraft.sinodivination.item;

import games.moegirl.sinocraft.sinodivination.recipe.SDRecipes;
import games.moegirl.sinocraft.sinodivination.util.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

/**
 * 变荑汤
 */
public class ChangeSoup extends Item {

    public ChangeSoup(RegistryObject<CreativeModeTab> tab) {
        super(new ItemProperties().stacksTo(1).craftRemainder(Items.BOWL).tab(tab).properties());
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
