package games.moegirl.sinocraft.sinocore.event.client.args.render;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public record BeforeRenderHudArgs(GuiGraphics guiGraphics, DeltaTracker partialTick) {
}
