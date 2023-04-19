package games.moegirl.sinocraft.sinocore.mixin.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.mixin.interfaces.IScreen;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author luqin2007
 */
@Mixin(Screen.class)
public abstract class ScreenMixin implements IScreen {

    @Shadow
    protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);

    @Shadow public abstract void renderTooltip(PoseStack poseStack, Component text, int mouseX, int mouseY);

    @Override
    public <T extends GuiEventListener & Renderable& NarratableEntry> T sinocore$addRenderableWidget(T widget) {
        return addRenderableWidget(widget);
    }

    @Override
    public void sinocore$renderTooltip(PoseStack poseStack, Component text, int mouseX, int mouseY) {
        renderTooltip(poseStack, text, mouseX, mouseY);
    }
}
