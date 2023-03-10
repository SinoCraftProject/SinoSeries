package games.moegirl.sinocraft.sinocore.old.api.client.render;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import games.moegirl.sinocraft.sinocore.api.woodwork.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.api.woodwork.ModSignBlockStanding;
import games.moegirl.sinocraft.sinocore.api.woodwork.Woodwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static net.minecraft.world.level.block.StandingSignBlock.ROTATION;
import static net.minecraft.world.level.block.WallSignBlock.FACING;

public class ModSignRenderer implements BlockEntityRenderer<ModSignBlockEntity> {
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private final Map<WoodType, SignModel> signModels;
    private final Font font;

    public ModSignRenderer(BlockEntityRendererProvider.Context pContext) {
        this.signModels = WoodType.values().collect(ImmutableMap.toImmutableMap(Function.identity(),
                type -> new SignModel(pContext.bakeLayer(ModelLayers.createSignModelName(type)))));
        this.font = pContext.getFont();
    }

    public void render(ModSignBlockEntity signEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        BlockState blockstate = signEntity.getBlockState();
        stack.pushPose();
        WoodType woodtype = getWoodType(blockstate.getBlock());
        SignModel signModel = this.signModels.get(woodtype);
        if (blockstate.getBlock() instanceof ModSignBlockStanding) {
            stack.translate(0.5D, 0.5D, 0.5D);
            float f1 = -((float) (blockstate.getValue(ROTATION) * 360) / 16.0F);
            stack.mulPose(Vector3f.YP.rotationDegrees(f1));
            signModel.stick.visible = true;
        } else {
            stack.translate(0.5D, 0.5D, 0.5D);
            float f4 = -blockstate.getValue(FACING).toYRot();
            stack.mulPose(Vector3f.YP.rotationDegrees(f4));
            stack.translate(0.0D, -0.3125D, -0.4375D);
            signModel.stick.visible = false;
        }

        stack.pushPose();
        stack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        Material material = Sheets.getSignMaterial(woodtype);
        VertexConsumer consumer = material.buffer(bufferSource, signModel::renderType);
        signModel.root.render(stack, consumer, packedLight, packedOverlay);
        stack.popPose();
        stack.translate(0.0, 0.33333334, 0.046666667);
        stack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int darkColor = getDarkColor(signEntity);
        FormattedCharSequence[] sequences = signEntity.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), component -> {
            List<FormattedCharSequence> list = this.font.split(component, 90);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        boolean hasOutline;
        int light;
        int color;
        if (signEntity.hasGlowingText()) {
            color = signEntity.getColor().getTextColor();
            hasOutline = isOutlineVisible(signEntity, color);
            light = 15728880;
        } else {
            color = darkColor;
            hasOutline = false;
            light = packedLight;
        }

        for (int line = 0; line < 4; ++line) {
            FormattedCharSequence sequence = sequences[line];
            float x = (float) (-this.font.width(sequence) / 2);
            if (hasOutline) {
                font.drawInBatch8xOutline(sequence, x, (float) (line * 10 - 20), color, darkColor, stack.last().pose(), bufferSource, light);
            } else {
                font.drawInBatch(sequence, x, (float) (line * 10 - 20), color, false, stack.last().pose(), bufferSource, false, 0, light);
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

    private static int getDarkColor(ModSignBlockEntity pBlockEntity) {
        int color = pBlockEntity.getColor().getTextColor();
        int r = (int) ((double) NativeImage.getR(color) * 0.4D);
        int g = (int) ((double) NativeImage.getG(color) * 0.4D);
        int b = (int) ((double) NativeImage.getB(color) * 0.4D);
        return color == DyeColor.BLACK.getTextColor() && pBlockEntity.hasGlowingText() ? -988212 : NativeImage.combine(0, b, g, r);
    }

    public static WoodType getWoodType(Block block) {
        return block instanceof SignBlock sign ? sign.type() : WoodType.OAK;
    }

    public static SignModel createSignModel(EntityModelSet set, WoodType pWoodType) {
        return new SignModel(set.bakeLayer(ModelLayers.createSignModelName(pWoodType)));
    }

    public static LayerDefinition registerLayer(Woodwork woodwork, EntityRenderersEvent.RegisterLayerDefinitions event) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.ZERO);
        LayerDefinition layer = LayerDefinition.create(meshdefinition, 64, 32);
        event.registerLayerDefinition(ModelLayers.createSignModelName(woodwork.type), () -> layer);
        return layer;
    }

    public static final class SignModel extends Model {
        public final ModelPart root;
        public final ModelPart stick;

        public SignModel(ModelPart pRoot) {
            super(RenderType::entityCutoutNoCull);
            this.root = pRoot;
            this.stick = pRoot.getChild("stick");
        }

        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        }
    }
}
