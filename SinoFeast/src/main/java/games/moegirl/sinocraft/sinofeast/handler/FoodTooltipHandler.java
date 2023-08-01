package games.moegirl.sinocraft.sinofeast.handler;

import games.moegirl.sinocraft.sinofeast.SFConstants;
import games.moegirl.sinocraft.sinofeast.SinoFeast;
import games.moegirl.sinocraft.sinofeast.utility.FoodTasteHelper;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoFeast.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FoodTooltipHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        var player = event.getEntity();
        var item = event.getItemStack();
        var tooltip = event.getToolTip();

        if (player == null) {
            return;
        }

        // Todo
//        if (FoodTasteHelper.isPrefer(player, item)) {
//            tooltip.add(Component.translatable(SFConstants.TRANSLATE_PREFER_TOOLTIP));
//        } else if (FoodTasteHelper.isLike(player, item)) {
//            tooltip.add(Component.translatable(SFConstants.TRANSLATE_LIKE_TOOLTIP));
//        } else if (FoodTasteHelper.isDislike(player, item)) {
//            tooltip.add(Component.translatable(SFConstants.TRANSLATE_DISLIKE_TOOLTIP));
//        }
    }
}
