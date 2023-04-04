package games.moegirl.sinocraft.sinocore.client.component;

import com.google.common.base.Verify;
import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

import java.time.Duration;

public class AnimatedText extends AbstractWidget {

    private long beginTime;
    private double duration;
    private int repeat;
    private int r, g, b;

    private Font font;

    public AnimatedText(int pX, int pY, int pWidth, int pHeight, Font font) {
        super(pX, pY, pWidth, pHeight, CommonComponents.EMPTY);
        this.font = font;
    }

    public AnimatedText(int pWidth, int pHeight) {
        this(0, 0, pWidth, pHeight, Minecraft.getInstance().font);
    }

    public void begin(Duration duration, int repeat, int r, int g, int b, Component message) {
        Verify.verify(duration.toNanos() > 0, "duration must not 0, your is " + duration);
        Verify.verify(!CommonComponents.EMPTY.equals(message), "message must not empty");

        setMessage(message);
        this.beginTime = System.nanoTime();
        this.duration = duration.toNanos();
        this.repeat = repeat;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void begin(Duration duration, int repeat, int r, int g, int b, String message) {
        begin(duration, repeat, r, g, b, Component.translatable(message));
    }

    public void begin(Duration duration, int repeat, int r, int g, int b, String message, Object pArgs) {
        begin(duration, repeat, r, g, b, Component.translatable(message, pArgs));
    }

    public void stop() {
        setMessage(CommonComponents.EMPTY);
        beginTime = 0;
        duration = 0;
        repeat = 0;
    }

    public void stopSmooth() {
        repeat = 0;
    }

    public AnimatedText resize(int x, int y, Font font) {
        setX(x);
        setY(y);
        this.font = font;
        return this;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Component message = getMessage();
        if (!CommonComponents.EMPTY.equals(message) && duration > 0) {
            long delta = System.nanoTime() - beginTime;
            if (delta >= duration) {
                if (repeat != 0) {
                    if (repeat > 0) {
                        repeat--;
                    }
                    beginTime = System.nanoTime();
                } else {
                    stop();
                }
            } else {
                double p = delta / duration;
                if (p > 0.5) {
                    p = (1 - p) * 2;
                } else {
                    p *= 2;
                }
                GLSwitcher switcher = GLSwitcher.blend().enable();
                renderMessage(pPoseStack, message, p);
                switcher.resume();
            }
        }
    }

    private void renderMessage(PoseStack poseStack, Component message, double alpha) {
        int textWidth = font.width(message);
        font.draw(poseStack, message, getX() - textWidth / 2f, getY(), FastColor.ARGB32.color((int) (alpha * 255), r, g, b));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, "Animated text");
    }
}
