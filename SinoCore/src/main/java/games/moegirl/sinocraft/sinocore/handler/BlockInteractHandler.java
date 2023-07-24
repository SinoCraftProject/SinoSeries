package games.moegirl.sinocraft.sinocore.handler;

import games.moegirl.sinocraft.sinocore.crafting.abstracted.SCRecipes;
import games.moegirl.sinocraft.sinocore.crafting.block_interact.BlockInteractRecipeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
            ItemStack stack = event.getItemStack();
            var container = new BlockInteractRecipeContainer(level, pos, stack, event.getEntity());
            SCRecipes.BLOCK_INTERACT_RECIPE.match(level, container)
                    .ifPresent(recipe -> recipe.assemble(container, level.registryAccess()));
        }
    }
}
