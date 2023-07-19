package games.moegirl.sinocraft.sinofoundation.event;

import games.moegirl.sinocraft.sinofoundation.crafting.block_interact.BlockInteractRecipeContainer;
import games.moegirl.sinocraft.sinofoundation.crafting.SFDRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class BlockInteractHandler {

    @SubscribeEvent
    public static void onEntityUseItem(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = event.getHitVec().getBlockPos();
            BlockState blockState = level.getBlockState(pos);
            ItemStack stack = event.getItemStack();
            SFDRecipes.BLOCK_INTERACT_RECIPE.match(level, new BlockInteractRecipeContainer(stack, blockState))
                    .ifPresent(recipe -> {
                        Player player = event.getEntity();
                        // 消耗物品与耐久
                        if (recipe.getCount() != 0) {
                            stack.shrink(recipe.getCount());
                        }
                        if (recipe.getDamage() != 0) {
                            stack.setDamageValue(stack.getDamageValue() + recipe.getDamage());
                        }
                        player.setItemInHand(event.getHand(), stack);
                        // 输出物品
                        ItemStack result = recipe.getResultItem(level.registryAccess());
                        if (!result.isEmpty() && !player.addItem(result)) {
                            Block.popResource(level, pos, result);
                        }
                        // 变化方块
                        BlockState block = recipe.getOutputBlock();
                        if (block != null) {
                            level.setBlock(pos, block, Block.UPDATE_ALL);
                        }
                    });
        }
    }
}
