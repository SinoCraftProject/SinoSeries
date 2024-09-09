package games.moegirl.sinocraft.sinobrush.neoforge.listener;

import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.client.FilledXuanPaperItemColor;
import games.moegirl.sinocraft.sinobrush.client.NormalItemColor;
import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = SinoBrush.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterColorHandlersListener {
    @SubscribeEvent
    public static void onRegisterColorHandlersEvent(RegisterColorHandlersEvent.Item event) {
        event.register(new NormalItemColor(), SBRItems.XUAN_PAPER.get(), SBRItems.INK_BOTTLE.get());
        event.register(new FilledXuanPaperItemColor(), SBRItems.FILLED_XUAN_PAPER.get());
    }
}
