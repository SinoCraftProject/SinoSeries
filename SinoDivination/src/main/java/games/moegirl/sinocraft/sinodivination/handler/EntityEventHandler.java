package games.moegirl.sinocraft.sinodivination.handler;

import games.moegirl.sinocraft.sinodivination.data.gen.tag.SDTags;
import games.moegirl.sinocraft.sinodivination.item.SDItems;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityEventHandler {

    @SubscribeEvent
    public static void onEntityUseItem(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) {
            BlockPos pos = event.getHitVec().getBlockPos();
            ItemStack stack = event.getItemStack();
            Player player = event.getEntity();

            if (SFDItems.WORMWOOD_LEAF.get() == stack.getItem() && level.getBlockState(pos).is(SDTags.FIRE_SOURCE)) {
                stack.shrink(1);
                ItemStack result = new ItemStack(SDItems.MOXIBUSTION.get());
                if (!player.addItem(result)) {
                    Block.popResource(level, pos, result);
                }
            }
        }
    }
}
