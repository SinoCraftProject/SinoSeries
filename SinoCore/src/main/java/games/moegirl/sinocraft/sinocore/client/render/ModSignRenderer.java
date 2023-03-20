package games.moegirl.sinocraft.sinocore.client.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import games.moegirl.sinocraft.sinocore.block.ModSignBlockStanding;
import games.moegirl.sinocraft.sinocore.block.entity.ModSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer.SignModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static net.minecraft.world.level.block.StandingSignBlock.ROTATION;
import static net.minecraft.world.level.block.WallSignBlock.FACING;

public class ModSignRenderer implements BlockEntityRenderer<ModSignBlockEntity> {
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private static final int BLACK_TEXT_OUTLINE_COLOR = -988212; // #FFF0EBCC
    private final Map<WoodType, SignModel> signModels;
    private final Font font;

    public ModSignRenderer(BlockEntityRendererProvider.Context context) {
        this.signModels = WoodType.values().collect(ImmutableMap.toImmutableMap(Function.identity(),
                type -> new SignModel(context.bakeLayer(ModelLayers.createSignModelName(type)))));
        this.font = context.getFont();
    }

    public void render(ModSignBlockEntity signEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState signBlock = signEntity.getBlockState();
        stack.pushPose();
        WoodType woodtype = getWoodType(signBlock.getBlock());
        SignModel signModel = signModels.get(woodtype);
        stack.translate(0.5D, 0.5D, 0.5D);
        if (signBlock.getBlock() instanceof ModSignBlockStanding) {
            float rotation = -RotationSegment.convertToDegrees(signBlock.getValue(ROTATION));
            stack.mulPose(Axis.YP.rotationDegrees(rotation));
            signModel.stick.visible = true;
        } else {
            float facing = -signBlock.getValue(FACING).toYRot();
            stack.mulPose(Axis.YP.rotationDegrees(facing));
            stack.translate(0F, -0.3125F, -0.4375F);
            signModel.stick.visible = false;
        }

        // renderSign(poseStack, bufferSource, packedLight, packedOverlay, 0.6666667F, woodType, signModel);
        stack.pushPose();
        float scale = 0.6666667F;
        stack.scale(scale, -scale, -scale);
        Material material = Sheets.getSignMaterial(woodtype);
        VertexConsumer consumer = material.buffer(bufferSource, signModel::renderType);
        signModel.root.render(stack, consumer, packedLight, packedOverlay);
        stack.popPose();

        // this.renderSignText(blockEntity, poseStack, bufferSource, packedLight, 0.6666667F);
        float f = 0.015625F * scale;
        Vec3 textOffset = new Vec3(0, 0.5 * scale, 0.07 * scale);
        stack.translate(textOffset.x, textOffset.y, textOffset.z);
        stack.scale(f, -f, f);
        int darkColor = getDarkColor(signEntity);
        int width = signEntity.getMaxTextLineWidth();
        FormattedCharSequence[] sequences = signEntity.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), component -> {
            List<FormattedCharSequence> list = this.font.split(component, width);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int light;
        int color;
        boolean hasOutline;
        if (signEntity.hasGlowingText()) {
            color = signEntity.getColor().getTextColor();
            hasOutline = isOutlineVisible(signEntity, color);
            light = 15728880;
        } else {
            color = darkColor;
            hasOutline = false;
            light = packedLight;
        }
        int height = 4 * signEntity.getTextLineHeight() / 2;
        for (int line = 0; line < 4; ++line) {
            FormattedCharSequence sequence = sequences[line];
            float w = (float) (-this.font.width(sequence) / 2);
            if (hasOutline) {
                font.drawInBatch8xOutline(sequence, w, line * signEntity.getMaxTextLineWidth() - height, color, darkColor, stack.last().pose(), bufferSource, light);
            } else {
                font.drawInBatch(sequence, w, line * signEntity.getMaxTextLineWidth() - height, color, false, stack.last().pose(), bufferSource, false, 0, light);
            }
        }
        stack.popPose();
    }

    private static boolean isOutlineVisible(ModSignBlockEntity pBlockEntity, int pTextColor) {
        if (pTextColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping()) {
                return true;
            } else {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pBlockEntity.getBlockPos())) < (double) OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    private static int getDarkColor(ModSignBlockEntity signEntity) {
        int color = signEntity.getColor().getTextColor();
        int r = (int) (NativeImage.getR(color) * 0.4);
        int g = (int) (NativeImage.getG(color) * 0.4);
        int b = (int) (NativeImage.getB(color) * 0.4);
        return color == DyeColor.BLACK.getTextColor() && signEntity.hasGlowingText()
                ? BLACK_TEXT_OUTLINE_COLOR
                : NativeImage.combine(0, b, g, r);
    }

    public static WoodType getWoodType(Block block) {
        return block instanceof SignBlock sign ? sign.type() : WoodType.OAK;
    }
}
