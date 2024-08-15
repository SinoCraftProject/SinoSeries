package games.moegirl.sinocraft.sinobrush.handler;

import games.moegirl.sinocraft.sinobrush.item.SBRItems;
import games.moegirl.sinocraft.sinobrush.item.XuanPaperItem;
import games.moegirl.sinocraft.sinocore.event.game.CraftingEvents;

public class CraftingHandlers {
    public static void register() {
        CraftingEvents.CARTOGRAPHY_CRAFT.register(e -> {
            var input = e.getMapInput();
            if (input.is(SBRItems.XUAN_PAPER.get())
                    && XuanPaperItem.canExpend(input)) {
                var expand = XuanPaperItem.getExpend(input);
                var other = e.getOtherInput();
                if (other.is(SBRItems.XUAN_PAPER.get()) && expand == XuanPaperItem.getExpend(other)) {
                    var output = input.copyWithCount(1);
                    XuanPaperItem.setExpend(output, expand + 1);
                    e.setOutput(output);
                }
            }
        });
    }
}
