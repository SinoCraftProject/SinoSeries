package games.moegirl.sinocraft.sinobrush.forge.listener;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.client.FilledXuanPaperItemColor;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoBrush.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterColorHandlersListener {
    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 0 && stack.getItem() instanceof DyeableLeatherItem dyeable) {
                return dyeable.getColor(stack);
            }
            return -1;
        }, SBRItems.XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());

        event.register(new FilledXuanPaperItemColor(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
