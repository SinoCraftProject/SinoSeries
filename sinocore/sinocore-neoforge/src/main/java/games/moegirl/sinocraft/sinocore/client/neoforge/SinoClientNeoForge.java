package games.moegirl.sinocraft.sinocore.client.neoforge;

import games.moegirl.sinocraft.sinocore.SinoCore;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = SinoCore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SinoClientNeoForge {
    private static ItemColors itemColors;
    private static BlockColors blockColors;

    static void registerItemColor(ItemColor color, ItemLike... items) {
        itemColors.register(color, items);
        System.out.println(items.length);
    }

    static void registerBlockColor(BlockColor color, Block... blocks) {
        blockColors.register(color, blocks);
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        itemColors = event.getItemColors();
    }

    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Block event) {
        blockColors = event.getBlockColors();
    }
}
