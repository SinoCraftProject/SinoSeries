package games.moegirl.sinocraft.sinodivination.old.item;

import games.moegirl.sinocraft.sinodivination.old.recipe.ChangeSoupRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class ChangeSoup extends Item {

    public ChangeSoup() {
        super(new Properties().stacksTo(1).craftRemainder(Items.BOWL));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            BlockPos pos = pContext.getClickedPos();
            Level level = pContext.getLevel();
            BlockState bs = level.getBlockState(pos);
            Optional<BlockState> result = ChangeSoupRecipe.findRecipeResult(level, bs);
            if (result.isPresent()) {
                level.setBlock(pos, result.get(), 2);
                Player player = pContext.getPlayer();
                if (player != null && !player.isCreative()) {
                    player.setItemInHand(pContext.getHand(), new ItemStack(Items.BOWL));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(pContext);
    }
}
