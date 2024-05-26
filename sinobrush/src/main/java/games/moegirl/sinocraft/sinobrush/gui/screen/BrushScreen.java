package games.moegirl.sinocraft.sinobrush.gui.screen;

import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.Drawing;
import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.gui.widget.CanvasWidget;
import games.moegirl.sinocraft.sinobrush.network.C2SDrawingPacket;
import games.moegirl.sinocraft.sinobrush.network.S2CDrawingPacket;
import games.moegirl.sinocraft.sinobrush.network.SBRNetworks;
import games.moegirl.sinocraft.sinocore.gui.WidgetScreenBase;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.EditBoxWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.ImageButtonWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.RectEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class BrushScreen extends WidgetScreenBase<BrushMenu> {

    private ImageButton btnBrush;
    private final ImageButtonWidget[] colorButtons = new ImageButtonWidget[16];

    private EditBoxWidget title;
    private CanvasWidget canvas;
    private boolean isSaving = false;

    public BrushScreen(BrushMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        LocalPlayer player = Minecraft.getInstance().player;
        assert player != null;
        addButton("button_clear", this::clearCanvas);
        btnBrush = addButton("button_brush", this::saveCanvas);
        addButton("button_save", this::saveCanvasToPng);
        addButton("button_copy", this::copyCanvas).setOnRightClick(this::pasteCanvas);
        colorButtons[0] = addButton("button_color_0", btn -> selectColor(0));
        colorButtons[1] = addButton("button_color_1", btn -> selectColor(1));
        colorButtons[2] = addButton("button_color_2", btn -> selectColor(2));
        colorButtons[3] = addButton("button_color_3", btn -> selectColor(3));
        colorButtons[4] = addButton("button_color_4", btn -> selectColor(4));
        colorButtons[5] = addButton("button_color_5", btn -> selectColor(5));
        colorButtons[6] = addButton("button_color_6", btn -> selectColor(6));
        colorButtons[7] = addButton("button_color_7", btn -> selectColor(7));
        colorButtons[8] = addButton("button_color_8", btn -> selectColor(8));
        colorButtons[9] = addButton("button_color_9", btn -> selectColor(9));
        colorButtons[10] = addButton("button_color_10", btn -> selectColor(10));
        colorButtons[11] = addButton("button_color_11", btn -> selectColor(11));
        colorButtons[12] = addButton("button_color_12", btn -> selectColor(12));
        colorButtons[13] = addButton("button_color_13", btn -> selectColor(13));
        colorButtons[14] = addButton("button_color_14", btn -> selectColor(14));
        colorButtons[15] = addButton("button_color_15", btn -> selectColor(15));
//        colorButtons[16] = addButton("button_color_16", btn -> selectColor(16));
        title = addEditBox("file_name");
        addRenderableWidget(getOrBuildCanvas());
        selectColor(15);

        menu.setScreen(this);
    }

    private CanvasWidget getOrBuildCanvas() {
        RectEntry rect = (RectEntry) widgets.getWidget("canvas");
        if (canvas == null) {
            canvas = new CanvasWidget(this, rect.getX() + leftPos, rect.getY() + topPos, rect.getWidth(), rect.getHeight(), this::isCanvasDrawable);
        } else {
            canvas.setX(rect.getX() + leftPos);
            canvas.setY(rect.getY() + topPos);
            canvas.setWidth(rect.getWidth());
            canvas.setHeight(rect.getHeight());
        }
        return canvas;
    }

    public void updateCanvas(int paperColor, int inkColor, int paperExpend) {
        canvas.getDrawing().setPaperColor(paperColor);
        canvas.getDrawing().setInkColor(inkColor);
        canvas.getDrawing().setWidth(SBRConstants.DRAWING_MIN_LENGTH << paperExpend);
        canvas.getDrawing().setHeight(SBRConstants.DRAWING_MIN_LENGTH << paperExpend);
    }

    private boolean isCanvasDrawable() {
        return !isSaving && !menu.inkAndPaperContainer.getItem(0).isEmpty() && !menu.inkAndPaperContainer.getItem(1).isEmpty();
    }

    private void clearCanvas(Button button) {
        canvas.getDrawing().clear();
    }

    private void saveCanvas(Button button) {
        Drawing drawing = canvas.getDrawing();
        drawing.setTitle(title.getValue());
        C2SDrawingPacket packet = new C2SDrawingPacket(drawing);
        SBRNetworks.NETWORKS.sendToServer(packet);
        beginSaving();
    }

    private void saveCanvasToPng(Button button) {
        try {
            Path saveTarget = prepareSaveFile(".png");
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> saveDrawFile(saveTarget));
            } else {
                saveDrawFile(saveTarget);
            }
        } catch (IOException e) {
            SinoBrush.LOGGER.error("BrushScreen downloadCanvas", e);
            sendMessage(e.getMessage(), false);
        }
    }

    private Path prepareSaveFile(String ext) throws IOException {
        if (!ext.startsWith(".")) {
            ext = "." + ext;
        }

        assert minecraft != null;
        assert minecraft.player != null;
        LocalPlayer player = minecraft.player;
        Path saveDir = new File(minecraft.gameDirectory, "sinobrush").toPath();
        Files.createDirectories(saveDir);
        String title = this.title.getValue();
        if (StringUtils.isBlank(title)) {
            title = "untitled";
        }
        String fileName = title + " by " + player.getName().getString();
        Path saveTarget = saveDir.resolve(fileName + ext);
        if (Files.isRegularFile(saveTarget)) {
            saveTarget = saveDir.resolve(fileName + "-" + Instant.now().getEpochSecond() + ext);
        }
        Files.createFile(saveTarget);
        return saveTarget;
    }

    private void saveDrawFile(Path file) {
        Drawing drawing = canvas.getDrawing();
        NativeImage nativeImage = new NativeImage(drawing.getWidth(), drawing.getHeight(), false);
        nativeImage.fillRect(0, 0, drawing.getWidth(), drawing.getHeight(), drawing.getPaperColor());
        for (int i = 0; i < drawing.getWidth(); i++) {
            for (int j = 0; j < drawing.getHeight(); j++) {
                nativeImage.setPixelRGBA(i, j, drawing.getPixel(i, j));
            }
        }
        try (nativeImage) {
            nativeImage.writeToFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyCanvas(Button button) {
        assert minecraft != null;
        try {
            minecraft.keyboardHandler.setClipboard(getDrawString());
            sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_CANVAS_COPIED), true);
        } catch (Exception e) {
            SinoBrush.LOGGER.error("BrushScreen copyCanvas", e);
            sendMessage(e.getMessage(), false);
        }
    }

    private void pasteCanvas(Button button) {
        assert minecraft != null;
        try {
            var str = minecraft.keyboardHandler.getClipboard();
            setDrawString(str);
            sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_CANVAS_PASTED), true);
        } catch (Exception e) {
            SinoBrush.LOGGER.error("BrushScreen pasteCanvas", e);
            sendMessage(e.getMessage(), false);
        }
    }

    public void selectColor(int color) {
        for (ImageButtonWidget button : colorButtons) {
            button.active = true;
        }
        colorButtons[color].active = false;
        canvas.setColor(color);
    }

    public void handleServiceData(int status) {
        if (isSaving) {
            switch (status) {
                case S2CDrawingPacket.STATUS_SUCCEED: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_SUCCESSFUL), true);
                    break;
                }
                case S2CDrawingPacket.STATUS_FAILED_PAPER: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_PAPER), false);
                    break;
                }
                case S2CDrawingPacket.STATUS_FAILED_INK: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_INK), false);
                    break;
                }
                case S2CDrawingPacket.STATUS_FAILED_DRAW: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED), false);
                    break;
                }
            }
            afterSaved();
        }
    }

    private void beginSaving() {
        isSaving = true;
        btnBrush.active = false;
    }

    private void afterSaved() {
        isSaving = false;
        btnBrush.active = true;
    }

    private void sendMessage(String message, boolean ok) {
        sendMessage(Component.literal(message), ok);
    }

    private void sendMessage(MutableComponent message, boolean ok) {
        assert minecraft != null;
        assert minecraft.player != null;
        message.withStyle(ok ? ChatFormatting.GREEN : ChatFormatting.RED);
        minecraft.player.sendSystemMessage(message);
    }

    private String getDrawString() {
        CompoundTag tag = canvas.getDrawing().writeToCompound();
        return NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, tag).toString();
    }

    private void setDrawString(String value) {
        CompoundTag tag = (CompoundTag) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, JsonParser.parseString(value));
        canvas.setDrawing(Drawing.fromTag(tag));
    }
}
