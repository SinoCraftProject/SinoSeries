package games.moegirl.sinocraft.sinocore.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import games.moegirl.sinocraft.sinocore.block.entity.ModSignBlockEntity;
import games.moegirl.sinocraft.sinocore.client.render.ModSignRenderer;
import games.moegirl.sinocraft.sinocore.packet.SignTextUpdateC2SPacket;
import games.moegirl.sinocraft.sinocore.tree.depr.woodwork.Woodwork;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.stream.IntStream;

import static net.minecraft.client.gui.screens.inventory.SignEditScreen.MAGIC_TEXT_SCALE;

public class ModSignEditScreen extends Screen {

    private static final Vector3f TEXT_SCALE = new Vector3f(MAGIC_TEXT_SCALE, MAGIC_TEXT_SCALE, MAGIC_TEXT_SCALE);

    private final ModSignBlockEntity sign;
    private final String[] messages;
    private final WoodType woodType;

    private int frame;
    private int line;

    private TextFieldHelper signField;
    private final SignRenderer.SignModel model;

    public ModSignEditScreen(ModSignBlockEntity signEntity, boolean isTextFilteringEnabled) {
        super(Component.translatable("sign.edit"));
        woodType = ModSignRenderer.getWoodType(signEntity.getBlockState().getBlock());
        messages = IntStream.range(0, 4)
                .mapToObj(line -> signEntity.getMessage(line, isTextFilteringEnabled))
                .map(Component::getString)
                .toArray(String[]::new);
        sign = signEntity;
        model = SignRenderer.createSignModel(minecraft.getEntityModels(), woodType);
    }

    @Override
    protected void init() {
        assert minecraft != null;
        // minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Button btn = Button.builder(CommonComponents.GUI_DONE, p -> onClose())
                .bounds(width / 2 - 100, height / 4 + 120, 200, 20)
                .build();
        addRenderableWidget(btn);
        signField = new TextFieldHelper(() -> messages[line], message -> {
            messages[line] = message;
            sign.setMessage(line, Component.literal(message));
        },
                TextFieldHelper.createClipboardGetter(minecraft),
                TextFieldHelper.createClipboardSetter(minecraft),
                message -> minecraft.font.width(message) <= sign.getMaxTextLineWidth());
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        Lighting.setupForFlatItems();
        renderBackground(stack);
        drawCenteredString(stack, font, title, width / 2, 40, 0xFFFFFF);
        renderSign(stack);
        Lighting.setupFor3DItems();
        super.render(stack, mouseX, mouseY, partialTick);
    }

    private void renderSign(PoseStack stack) {
        MultiBufferSource.BufferSource source = minecraft.renderBuffers().bufferSource();
        stack.pushPose();
        stack.translate(width / 2f, 90f, 50f);

        // renderSignBackground(poseStack, bufferSource, blockState);
        stack.pushPose();
        if (model != null) {
            stack.translate(0f, 31f, 0f);
            stack.scale(62.500004F, 62.500004F, -62.500004F);
            Material material = Sheets.getSignMaterial(woodType);
            VertexConsumer consumer = material.buffer(source, model::renderType);
            model.stick.visible = sign.getBlockState().getBlock() instanceof StandingSignBlock;
            model.root.render(stack, consumer, 15728880, OverlayTexture.NO_OVERLAY);
        }
        stack.popPose();

        // renderSignText(poseStack, bufferSource);
        stack.translate(0f, 0f, 4f);
        stack.scale(TEXT_SCALE.x(), TEXT_SCALE.y(), TEXT_SCALE.z());
        int textColor = sign.getColor().getTextColor();
        boolean renderCursor = frame / 6 % 2 == 0;
        int cursorPos = signField.getCursorPos();
        int selectionPos = signField.getSelectionPos();
        int textY = -4 * sign.getTextLineHeight() / 2;
        int cursorY = line * sign.getTextLineHeight() + textY;
        Matrix4f pose = stack.last().pose();
        for (int i = 0; i < messages.length; i++) {
            String string = messages[i];
            if (string == null) continue;
            if (font.isBidirectional())
                string = font.bidirectionalShaping(string);
            int textWidth = minecraft.font.width(string);
            int x = -textWidth / 2;
            minecraft.font.drawInBatch(string, x, i * sign.getTextLineHeight() + textY, textColor, false, pose, source, false, 0, 0xF000F0, false);
            if (i != line || !renderCursor) continue;
            if (cursorPos >= string.length()) {
                int cursorX = textWidth + x;
                minecraft.font.drawInBatch("_", cursorX, cursorY, textColor, false, pose, source, false, 0, 0xF000F0, false);
            }
        }
        source.endBatch();

        if (line < messages.length && messages[line] == null) {
            String string = messages[line];
            int x = -minecraft.font.width(string) / 2;
            int cursorX = minecraft.font.width(string.substring(0, cursorPos)) + x;
            fill(stack, cursorX, cursorY - 1, cursorX + 1, cursorY + sign.getTextLineHeight(), 0xFF000000 | textColor);
            if (selectionPos != cursorPos) {
                int selectionLeft = Math.min(selectionPos, cursorPos);
                int selectionRight = Math.max(selectionPos, cursorPos);
                int tmpSelectionX0 = minecraft.font.width(string.substring(0, selectionLeft)) + x;
                int tmpSelectionX1 = minecraft.font.width(string.substring(0, selectionRight)) + x;
                int selectionX0 = Math.min(tmpSelectionX0, tmpSelectionX1);
                int selectionX1 = Math.max(tmpSelectionX0, tmpSelectionX1);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder builder = tesselator.getBuilder();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.disableTexture();
                RenderSystem.enableColorLogicOp();
                RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                builder.vertex(pose, selectionX0, cursorY + sign.getTextLineHeight(), 0).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, selectionX1, cursorY + sign.getTextLineHeight(), 0).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, selectionX0, cursorY, 0).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, selectionX1, cursorY, 0).color(0, 0, 255, 255).endVertex();
                BufferUploader.drawWithShader(builder.end());
                RenderSystem.disableColorLogicOp();
                RenderSystem.enableTexture();;
            }
        }
        stack.popPose();
    }

    @Override
    public void tick() {
        ++frame;
        if (!sign.getType().isValid(sign.getBlockState())) {
            onClose();
        }
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        signField.charTyped(pCodePoint);
        return true;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_UP) {
            line = line - 1 & 3;
            signField.setCursorToEnd();
            return true;
        } else if (pKeyCode != GLFW.GLFW_KEY_DOWN && pKeyCode != GLFW.GLFW_KEY_ENTER && pKeyCode != GLFW.GLFW_KEY_KP_ENTER) {
            return signField.keyPressed(pKeyCode) || super.keyPressed(pKeyCode, pScanCode, pModifiers);
        } else {
            line = line + 1 & 3;
            signField.setCursorToEnd();
            return true;
        }
    }

    @Override
    public void removed() {
        Woodwork.network(sign.getWoodwork().name.getNamespace())
                .sendToServer(new SignTextUpdateC2SPacket(sign.getBlockPos(), messages));
    }

    @Override
    public void onClose() {
        sign.setChanged();
        minecraft.setScreen(null);
    }
}
