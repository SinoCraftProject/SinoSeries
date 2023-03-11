package games.moegirl.sinocraft.sinocore.old.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import games.moegirl.sinocraft.sinocore.old.woodwork.*;
import games.moegirl.sinocraft.sinocore.old.client.render.ModSignRenderer;
import games.moegirl.sinocraft.sinocore.old.woodwork.ModSignBlockEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import java.util.stream.IntStream;

@SuppressWarnings("NotNullFieldNotInitialized")
public class ModSignEditScreen extends Screen {

    private final ModSignBlockEntity sign;
    private final String[] messages;

    private int frame;
    private int currentLine;

    private TextFieldHelper signField;
    private WoodType woodType;
    private ModSignRenderer.SignModel signModel;

    public ModSignEditScreen(ModSignBlockEntity pSign, boolean isTextFilteringEnabled) {
        super(Component.translatable("sign.edit"));
        messages = IntStream.range(0, 4)
                .mapToObj(line -> pSign.getMessage(line, isTextFilteringEnabled))
                .map(Component::getString)
                .toArray(String[]::new);
        sign = pSign;
    }

    @Override
    protected void init() {
        assert minecraft != null;
        // minecraft.keyboardHandler.setSendRepeatsToGui(true);
        Button btn = Button.builder(CommonComponents.GUI_DONE, p -> onDone())
                .pos(width / 2 - 100, height / 4 + 120)
                .size(200, 20)
                .build();
        addRenderableWidget(btn);
        signField = new TextFieldHelper(() -> messages[currentLine],
                message -> {
                    messages[currentLine] = message;
                    sign.setMessage(currentLine, Component.literal(message));
                },
                TextFieldHelper.createClipboardGetter(minecraft),
                TextFieldHelper.createClipboardSetter(minecraft),
                message -> minecraft.font.width(message) <= 90);
        BlockState blockstate = sign.getBlockState();
        woodType = ModSignRenderer.getWoodType(blockstate.getBlock());
        signModel = ModSignRenderer.createSignModel(minecraft.getEntityModels(), woodType);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        assert minecraft != null;

        Lighting.setupForFlatItems();
        renderBackground(stack);
        drawCenteredString(stack, font, title, width / 2, 40, 0xFFFFFF);
        stack.pushPose();
        stack.translate(width / 2.0, 0.0D, 50.0D);
        stack.scale(93.75F, -93.75F, 93.75F);
        stack.translate(0.0D, -1.3125D, 0.0D);
        boolean isOnWall = sign.getBlockState().getBlock() instanceof ModSignBlockWall;
        if (isOnWall) {
            stack.translate(0.0D, -0.3125D, 0.0D);
        }

        // bg model
        stack.pushPose();
        stack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        MultiBufferSource.BufferSource source = minecraft.renderBuffers().bufferSource();
        Material material = Sheets.getSignMaterial(woodType);
        VertexConsumer buffer = material.buffer(source, signModel::renderType);
        signModel.stick.visible = !isOnWall;
        signModel.root.render(stack, buffer, 15728880, OverlayTexture.NO_OVERLAY);
        stack.popPose();

        // message and cursor
        stack.translate(0.0D, 0.33333334F, 0.046666667F);
        stack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int color = sign.getColor().getTextColor();
        int cursorPos = signField.getCursorPos();
        int cursorY = currentLine * 10 - messages.length * 5;
        boolean isCursorDisplay = frame / 6 % 2 == 0;
        Matrix4f pose = stack.last().pose();
        for (int i = 0; i < messages.length && messages[i] != null; ++i) {
            String s = font.isBidirectional() ? font.bidirectionalShaping(messages[i]) : messages[i];
            float x = -minecraft.font.width(s) / 2f;
            minecraft.font.drawInBatch(s, x, i * 10 - messages.length * 5, color, false, pose, source, false, 0, 15728880, false);
            if (i == currentLine && cursorPos >= 0 && isCursorDisplay) {
                int cursorX = minecraft.font.width(s.substring(0, Math.min(cursorPos, s.length()))) - minecraft.font.width(s) / 2;
                if (cursorPos >= s.length()) {
                    minecraft.font.drawInBatch("_", cursorX, cursorY, color, false, pose, source, false, 0, 15728880, false);
                }
            }
        }
        source.endBatch();

        // selection
        int selection = signField.getSelectionPos();
        if (currentLine < messages.length && messages[currentLine] != null && cursorPos >= 0) {
            String s = messages[currentLine];
            int width = minecraft.font.width(s.substring(0, Math.min(cursorPos, s.length())));
            int x = width - minecraft.font.width(s) / 2;
            if (isCursorDisplay && cursorPos < s.length()) {
                fill(stack, x, cursorY - 1, x + 1, cursorY + 9, -16777216 | color);
            }
            if (selection != cursorPos) {
                int min = Math.min(cursorPos, selection);
                int max = Math.max(cursorPos, selection);
                int point0 = minecraft.font.width(s.substring(0, min)) - minecraft.font.width(s) / 2;
                int point1 = minecraft.font.width(s.substring(0, max)) - minecraft.font.width(s) / 2;
                int x0 = Math.min(point0, point1);
                int x1 = Math.max(point0, point1);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder builder = tesselator.getBuilder();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.disableTexture();
                RenderSystem.enableColorLogicOp();
                RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                builder.vertex(pose, x0, (cursorY + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, x1, (cursorY + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, x1, cursorY, 0.0F).color(0, 0, 255, 255).endVertex();
                builder.vertex(pose, x0, cursorY, 0.0F).color(0, 0, 255, 255).endVertex();
                builder.end();
                BufferUploader.draw(builder.end());
                RenderSystem.disableColorLogicOp();
                RenderSystem.enableTexture();
            }
        }
        stack.popPose();

        Lighting.setupFor3DItems();
        super.render(stack, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        ++frame;
        if (!sign.getType().isValid(sign.getBlockState())) {
            onDone();
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
            currentLine = currentLine - 1 & 3;
            signField.setCursorToEnd();
            return true;
        } else if (pKeyCode != GLFW.GLFW_KEY_DOWN && pKeyCode != GLFW.GLFW_KEY_ENTER && pKeyCode != GLFW.GLFW_KEY_KP_ENTER) {
            return signField.keyPressed(pKeyCode) || super.keyPressed(pKeyCode, pScanCode, pModifiers);
        } else {
            currentLine = currentLine + 1 & 3;
            signField.setCursorToEnd();
            return true;
        }
    }

    @Override
    public void removed() {
        WoodworkManager.getManager(BlockEntityType.getKey(sign.getType()))
                .map(WoodworkManager::network)
                .ifPresent(nw -> nw.sendToServer(new SignTextUpdateC2SPacket(sign.getBlockPos(), messages)));
    }

    @Override
    public void onClose() {
        onDone();
    }

    private void onDone() {
        assert minecraft != null;
        sign.setChanged();
        minecraft.setScreen(null);
    }
}
