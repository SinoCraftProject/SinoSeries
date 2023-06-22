package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.data.SDTags;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author luqin2007
 */
@Mod.EventBusSubscriber
public class EntityEventHandler {

    @SubscribeEvent
    public static void onEntityUseItem(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = event.getHitVec().getBlockPos();
            if (level.getBlockState(pos).is(SDTags.FIRE_SOURCE)) {
                Player player = event.getEntity();
                ItemStack stack = event.getItemStack();
                stack.shrink(1);
                player.setItemInHand(event.getHand(), stack);
                ItemStack result = new ItemStack(SDItems.MOXIBUSTION.get());
                if (!player.addItem(result)) {
                    Block.popResource(level, pos, result);
                }
            }
        }
    }
}
