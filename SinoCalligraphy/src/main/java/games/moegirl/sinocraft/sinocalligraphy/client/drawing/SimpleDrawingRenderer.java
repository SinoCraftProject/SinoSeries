package games.moegirl.sinocraft.sinocalligraphy.client.drawing;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.SimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.utility.DrawingHelper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;

import static games.moegirl.sinocraft.sinocalligraphy.client.drawing.RenderTypes.COLOR_LIGHT_256;

public class SimpleDrawingRenderer implements IDrawingRenderer {
    protected SimpleDrawing drawing;

    public SimpleDrawingRenderer(SimpleDrawing drawing) {
        this.drawing = drawing;
    }

    @Override
    public void draw(PoseStack poseStack, int x, int y, int width, int height) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        GuiComponent.fill(poseStack, x, y, x + width, y + height, getBackgroundColor());

        int unitX = width / drawing.getSize();
        int unitY = height / drawing.getSize();
        int x1 = x;
        int x2 = x1 + unitX;
        byte[] draw = drawing.getPixels();
        int index = 0;
        for (int i = 0; i < drawing.getSize(); i++) {
            int y1 = y;
            int y2 = y1 + unitY;
            for (int j = 0; j < drawing.getSize(); j++) {
                int foregroundColor = getForegroundColor();
                int fr = FastColor.ARGB32.red(foregroundColor);
                int fg = FastColor.ARGB32.green(foregroundColor);
                int fb = FastColor.ARGB32.blue(foregroundColor);
                int scale = 16 * draw[index++]; // qyl27: For calculating grayscale.
                if (scale != 0) {
                    int scaledColor = FastColor.ARGB32.color(scale, fr, fg, fb);
                    GuiComponent.fill(poseStack, x1, y1, x2, y2, scaledColor);
                }
                y1 = y2;
                y2 += unitY;
            }
            x1 = x2;
            x2 += unitX;
        }
    }

    @Override
    public void draw(PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer vertexConsumer = buffer.getBuffer(COLOR_LIGHT_256);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        if (drawing.isEmpty()) {
            vertexRect(poseStack, vertexConsumer, 0, 0, drawing.getSize(), drawing.getSize(), getBackgroundColor(), packedLight);
        } else {
            drawPixels(poseStack, vertexConsumer, packedLight);
        }

        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
    }

    protected void drawPixels(PoseStack poseStack, VertexConsumer consumer, int light) {
        byte[] pixels = drawing.getPixels();
        for (int x1 = 0; x1 < drawing.getSize(); x1++) {
            int x2 = x1 + 1;
            for (int y1 = 0; y1 < drawing.getSize(); y1++) {
                int greyScale = 16 * pixels[x1 * drawing.getSize() + y1];
                int y2 = y1 + 1;

                if (greyScale == 0) {
                    vertexRectScaleColor(poseStack, consumer, x1, y1, x2, y2, getBackgroundColor(), light);
                } else {
                    var mixed = DrawingHelper.mix(getForegroundColor(), getBackgroundColor(), 256 - greyScale);
                    vertexRectScaleColor(poseStack, consumer, x1, y1, x2, y2, mixed, light);
                }
            }
        }
    }

    protected void vertexRectScaleColor(PoseStack poseStack, VertexConsumer consumer,
                                        int x1, int y1, int x2, int y2,
                                        int color, int light) {
        int r = FastColor.ARGB32.red(color);
        int g = FastColor.ARGB32.green(color);
        int b = FastColor.ARGB32.blue(color);
        int scaledColor = FastColor.ARGB32.color(255, r, g, b);

        vertexRect(poseStack, consumer, x1, y1, x2, y2, scaledColor, light);
    }

    protected void vertexRect(PoseStack poseStack, VertexConsumer consumer,
                              int x1, int y1, int x2, int y2,
                              int color, int light) {
        consumer.vertex(poseStack.last().pose(), x1, y1, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x1, y2, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y2, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y1, 0).color(color).uv2(light).endVertex();
    }

    protected int getBackgroundColor() {
        if (drawing != null) {
            return drawing.getPaperType().getColor();
        }

        return PaperType.WHITE.getColor();
    }

    protected int getForegroundColor() {
        if (drawing != null) {
            return drawing.getInkType().getColor();
        }

        return InkType.BLACK.getColor();
    }
}
