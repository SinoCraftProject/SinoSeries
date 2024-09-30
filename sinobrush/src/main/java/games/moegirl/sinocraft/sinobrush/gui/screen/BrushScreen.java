package games.moegirl.sinocraft.sinobrush.gui.screen;

import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.JsonOps;
import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.SinoBrush;
import games.moegirl.sinocraft.sinobrush.drawing.MutableDrawing;
import games.moegirl.sinocraft.sinobrush.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinobrush.gui.widget.CanvasWidget;
import games.moegirl.sinocraft.sinobrush.item.XuanPaperItem;
import games.moegirl.sinocraft.sinobrush.network.C2SSaveDrawPacket;
import games.moegirl.sinocraft.sinobrush.network.S2CDrawResultPacket;
import games.moegirl.sinocraft.sinobrush.utility.CanvasStashHelper;
import games.moegirl.sinocraft.sinobrush.utility.ColorHelper;
import games.moegirl.sinocraft.sinocore.gui.WidgetScreenBase;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.EditBoxWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.ImageButtonWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.RectEntry;
import games.moegirl.sinocraft.sinocore.network.NetworkManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class BrushScreen extends WidgetScreenBase<BrushMenu> {

    private Button btnBrush;
    private final ImageButtonWidget[] colorButtons = new ImageButtonWidget[16];

    private EditBoxWidget title;
    private CanvasWidget canvas;

    private boolean hasInitialized = false;
    private boolean isSaving = false;
    private int selectedColor = 0;

    public BrushScreen(BrushMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
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
        title = addEditBox("file_name");

        var rect = (RectEntry) widgets.getWidget("canvas");
        canvas = new CanvasWidget(this, rect.getX() + leftPos, rect.getY() + topPos, rect.getWidth(), rect.getHeight());

        addRenderableWidget(canvas);
        selectColor(15);

        menu.container.addListener(container -> {
            var ink = menu.container.getItem(BrushMenu.INK_SLOT);
            var paper = menu.container.getItem(BrushMenu.PAPER_SLOT);
            canvas.getDrawing().setInkColor(ColorHelper.getColor(ink));
            canvas.getDrawing().setPaperColor(ColorHelper.getColor(paper));
            var size = SBRConstants.DRAWING_MIN_LENGTH << XuanPaperItem.getExpend(paper);
            canvas.getDrawing().resize(size, size);
            updateCanvas();
        });

        if (!hasInitialized) {
            var drawing = CanvasStashHelper.unstashCanvas(Minecraft.getInstance());
            if (drawing != null) {
                canvas.getDrawing().setWidth(drawing.getWidth());
                canvas.getDrawing().setHeight(drawing.getHeight());
                canvas.getDrawing().setPixels(drawing.getPixels());
                updateCanvas();
            }
        }

        hasInitialized = true;
    }

    @Override
    public void onClose() {
        var drawing = new MutableDrawing();
        drawing.setWidth(canvas.getDrawing().getWidth());
        drawing.setHeight(canvas.getDrawing().getHeight());
        drawing.setPixels(canvas.getDrawing().getPixels());
        CanvasStashHelper.stashCanvas(Minecraft.getInstance(), drawing);
        super.onClose();
    }

    private void updateCanvas() {
        canvas.active = isCanvasDrawable();
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    private boolean isCanvasDrawable() {
        return !isSaving && menu.isDrawable();
    }

    private void clearCanvas(Button button) {
        canvas.getDrawing().clear();
    }

    private void saveCanvas(Button button) {
        var drawing = canvas.getDrawing();
        drawing.setTitle(title.getValue());
        C2SSaveDrawPacket packet = new C2SSaveDrawPacket(drawing, menu.brushSlotId);
        NetworkManager.sendToServer(packet);
        beginSaving();
    }

    private void saveCanvasToPng(Button button) {
        try {
            var saveTarget = prepareSaveFile(".png");
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> saveDrawFile(saveTarget));
            } else {
                saveDrawFile(saveTarget);
            }
        } catch (IOException e) {
            SinoBrush.LOGGER.error("BrushScreen downloadCanvas", e);
            sendErrorMessage(e.getMessage());
        }
    }

    private Path prepareSaveFile(String ext) throws IOException {
        if (!ext.startsWith(".")) {
            ext = "." + ext;
        }

        assert minecraft != null;
        assert minecraft.player != null;
        var player = minecraft.player;
        var saveDir = new File(minecraft.gameDirectory, "sinoseries/sinobrush/drawings").toPath();
        Files.createDirectories(saveDir);
        var title = this.title.getValue();
        if (title.isBlank()) {
            title = "untitled";
        }
        var author = player.getName().getString();
        if (author.isBlank()) {
            author = "anonymous";
        }
        var saveTarget = saveDir.resolve(title + "-" + author + "-" + Instant.now().getEpochSecond() + ext);
        Files.createFile(saveTarget);
        return saveTarget;
    }

    private void saveDrawFile(Path file) {
        var drawing = canvas.getDrawing();
        var nativeImage = new NativeImage(drawing.getWidth(), drawing.getHeight(), false);
        nativeImage.fillRect(0, 0, drawing.getWidth(), drawing.getHeight(), drawing.getPaperColor());
        for (var i = 0; i < drawing.getWidth(); i++) {
            for (var j = 0; j < drawing.getHeight(); j++) {
                nativeImage.setPixelRGBA(i, j, ColorHelper.pixelColorToARGB(drawing.getPixel(i, j), drawing.getInkColor()));
            }
        }
        try (nativeImage) {
            nativeImage.writeToFile(file);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void copyCanvas(Button button) {
        assert minecraft != null;
        try {
            minecraft.keyboardHandler.setClipboard(drawToString());
            sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_CANVAS_COPIED), true);
        } catch (Exception e) {
            SinoBrush.LOGGER.error("BrushScreen copyCanvas", e);
            sendErrorMessage(e.getMessage());
        }
    }

    private void pasteCanvas(Button button) {
        assert minecraft != null;
        try {
            var str = minecraft.keyboardHandler.getClipboard();
            drawFromString(str);
            sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_CANVAS_PASTED), true);
        } catch (Exception e) {
            SinoBrush.LOGGER.error("BrushScreen pasteCanvas", e);
            sendErrorMessage(e.getMessage());
        }
    }

    public void selectColor(int color) {
        for (ImageButtonWidget button : colorButtons) {
            button.active = true;
        }
        colorButtons[color].active = false;
        selectedColor = color;
    }

    public void handleServiceData(S2CDrawResultPacket.Status status) {
        if (isSaving) {
            switch (status) {
                case S2CDrawResultPacket.Status.SUCCEED: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_SUCCESSFUL), true);
                    break;
                }
                case S2CDrawResultPacket.Status.FAILED_MISSING_PAPER: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_PAPER), false);
                    break;
                }
                case S2CDrawResultPacket.Status.FAILED_MISSING_INK: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_INK), false);
                    break;
                }
                case S2CDrawResultPacket.Status.FAILED_DRAW: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_OUTPUT_OCCUPIED), false);
                    break;
                }
                case S2CDrawResultPacket.Status.FAILED_MISSING_BRUSH: {
                    sendMessage(Component.translatable(SBRConstants.Translation.GUI_BRUSH_SAVE_FAILED_NO_BRUSH_ON_HAND), false);
                    break;
                }
            }
            afterSaved();
        }
    }

    private void beginSaving() {
        isSaving = true;
        btnBrush.active = false;
        updateCanvas();
    }

    private void afterSaved() {
        isSaving = false;
        btnBrush.active = true;
        updateCanvas();
    }

    private void sendErrorMessage(String message) {
        sendMessage(Component.literal(message), false);
    }

    private void sendMessage(MutableComponent message, boolean ok) {
        assert minecraft != null;
        assert minecraft.player != null;
        message.withStyle(ok ? ChatFormatting.GREEN : ChatFormatting.RED);
        minecraft.player.sendSystemMessage(message);
    }

    private String drawToString() {
        CompoundTag tag = canvas.getDrawing().writeToCompound(Minecraft.getInstance().level.registryAccess());
        return NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, tag).toString();
    }

    private void drawFromString(String value) {
        CompoundTag tag = (CompoundTag) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, JsonParser.parseString(value));
        var drawing = new MutableDrawing();
        drawing.readFromCompound(tag, Minecraft.getInstance().level.registryAccess());
        canvas.setDrawing(drawing);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (canvas.isMouseOver(mouseX, mouseY)) {
            canvas.mouseMoved(mouseX, mouseY);
            return;
        }

        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        canvas.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        canvas.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (canvas.isMouseOver(mouseX, mouseY)) {
            return canvas.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (canvas.isMouseOver(mouseX, mouseY)) {
            if (scrollY != 0) {
                var color = getSelectedColor();
                if (scrollY > 0) {
                    color += 1;
                } else if (scrollY < 0) {
                    color -= 1;
                }

                color += 16;
                color %= 16;

                selectColor(color);
                return true;
            }
        }

        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (title.isFocused() && Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
            return true;
        }

        if (canvas.isHovered()) {
            return canvas.keyPressed(keyCode, scanCode, modifiers);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (canvas.isHovered()) {
            return canvas.keyReleased(keyCode, scanCode, modifiers);
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }
}
