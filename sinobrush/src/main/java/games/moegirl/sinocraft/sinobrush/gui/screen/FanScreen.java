package games.moegirl.sinocraft.sinobrush.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinobrush.network.Common2FanLines;
import games.moegirl.sinocraft.sinocore.gui.widgets.WidgetLoader;
import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.TextureEntry;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class FanScreen extends Screen {
    public static final int MAX_DISPLAY_LINES = 21;

    public static final Widgets TEXTURE = WidgetLoader.loadWidgets(ResourceLocation.parse("sinobrush:textures/gui/fan"));
    public static final float[] LINE_X = {231, 223, 213, 203, 191, 181, 165, 152, 138, 117, 102, 89, 77, 67, 56, 46, 37, 28, 20, 14, 9,};
    public static final float[] LINE_Y = {64, 54, 43, 35, 27, 22, 17, 13, 10, 11, 13, 18, 22, 28, 34, 43, 52, 62, 72, 83, 95,};
    public static final float[] LINE_R = {45, 41, 35, 30, 25, 22, 14, 9, 4, -6, -13, -19, -23, -26, -30, -35, -39, -45, -50, -55, -58,};

    private int leftPos, topPos;
    private final int imageWidth, imageHeight;
    private final List<Component> lines;
    private int currentLine = -1;
    private long focusedTime = 0;
    private boolean isChanged = false;

    public FanScreen(List<Component> lines) {
        super(Component.literal("fan"));
        TextureEntry background = (TextureEntry) TEXTURE.getWidget("background");
        imageWidth = background.getWidth();
        imageHeight = background.getHeight();
        this.lines = new ArrayList<>(lines);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void renderMenuBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        TextureEntry background = (TextureEntry) TEXTURE.getWidget("background");
        guiGraphics.blit(TEXTURE.getTexture(), leftPos + x, topPos + y, imageWidth, imageHeight,
                background.getTextureX(), background.getTextureY(),
                background.getTextureWidth(), background.getTextureHeight(),
                TEXTURE.getWidth(), TEXTURE.getHeight());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        PoseStack pose = guiGraphics.pose();
        for (int i = 0; i < MAX_DISPLAY_LINES; i++) {
            if (i >= lines.size() && currentLine != i)
                continue;

            float x = leftPos + LINE_X[i];
            float y = topPos + LINE_Y[i];
            float r = (float) Math.toRadians(LINE_R[i]);
            pose.pushPose();
            pose.translate(x, y, 0);
            pose.scale(0.6f, 0.6f, 1);
            pose.mulPose(new Matrix4f().rotate(r, 0, 0, 1));
            int ny = 0;
            // 文本内容
            if (i < lines.size()) {
                ny = drawCharacters(guiGraphics, lines.get(i).getString());
            }
            // 编辑标记
            if (currentLine == i) {
                drawAppendChar(guiGraphics, ny);
            }
            pose.translate(-x, -y, 0);
            pose.popPose();
        }
    }

    private int drawCharacters(GuiGraphics guiGraphics, String line) {
        int y = 0;
        for (char c : line.toCharArray()) {
            String s = String.valueOf(c);
            int w = font.width(s);
            int h = font.wordWrapHeight(s, w);
            guiGraphics.drawString(font, s, 0, y, 0x414141, false);
            y += h;
        }
        return y;
    }

    private void drawAppendChar(GuiGraphics guiGraphics, int y) {
        boolean show = (Util.getMillis() - focusedTime) / 300L % 2L == 0L;
        if (show) {
            guiGraphics.drawString(this.font, "_", 0, y, 0x414141);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= leftPos && mouseX < leftPos + imageWidth && mouseY >= topPos && mouseY < topPos + imageHeight) {
            if (currentLine == -1) {
                focusedTime = Util.getMillis();
                currentLine = 0;
            }
        } else {
            currentLine = -1;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            currentLine = (currentLine + 1) % MAX_DISPLAY_LINES;
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
            currentLine = (MAX_DISPLAY_LINES + currentLine - 1) % MAX_DISPLAY_LINES;
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_BACKSPACE && currentLine != -1) {
            Component component = getComponentAt(currentLine);
            String content = component.getString();
            if (!content.isEmpty()) {
                lines.set(currentLine, Component.literal(content.substring(0, content.length() - 1)));
                isChanged = true;
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (currentLine >= 0) {
            if (codePoint == '\n') {
                currentLine = (currentLine + 1) % MAX_DISPLAY_LINES;
            } else {
                Component component = getComponentAt(currentLine);
                lines.set(currentLine, Component.literal(component.getString() + codePoint));
                isChanged = true;
            }
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    private Component getComponentAt(int i) {
        for (int j = lines.size(); j <= i; j++) {
            lines.add(Component.empty());
        }
        return lines.get(i);
    }

    @Override
    public void onClose() {
        if (isChanged) {
            NetworkManager.sendToServer(new Common2FanLines(lines));
        }
        super.onClose();
    }
}
