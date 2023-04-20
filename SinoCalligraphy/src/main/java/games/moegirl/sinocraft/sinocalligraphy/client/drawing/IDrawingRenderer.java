package games.moegirl.sinocraft.sinocalligraphy.client.drawing;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

/**
 * A renderer to render a drawing
 */
public interface IDrawingRenderer {

    /**
     * Render the draw directly
     * qyl27: This was used for GUI.
     *
     * @param poseStack stack
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     */
    void draw(PoseStack poseStack, int x, int y, int width, int height);

    /**
     * Render the draw to a buffer with light
     * @param poseStack stack
     * @param buffer buffer
     * @param packedLight light data
     */
    void draw(PoseStack poseStack, MultiBufferSource buffer, int packedLight);

//    /**
//     * Render the draw to a buffer
//     * @param poseStack stack
//     * @param buffer buffer
//     */
//    void draw(PoseStack poseStack, MultiBufferSource buffer);
}
