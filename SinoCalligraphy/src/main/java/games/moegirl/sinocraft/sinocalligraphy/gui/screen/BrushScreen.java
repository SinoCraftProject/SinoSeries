package games.moegirl.sinocraft.sinocalligraphy.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.BrushCanvas;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.ColorSelectionList;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.DrawingSaveC2SPacket;
import games.moegirl.sinocraft.sinocalligraphy.utility.DrawingHelper;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinocore.client.component.AnimatedText;
import games.moegirl.sinocraft.sinocore.client.component.EditBoxOptional;
import games.moegirl.sinocraft.sinocore.gui.menu.inventory.InventoryNoTitleWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.util.Lazy;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

public class BrushScreen extends AbstractContainerScreen<BrushMenu> {
    private static final TextureMapClient CLIENT_TEXTURE = new TextureMapClient(BrushMenu.TEXTURE);

    private final Lazy<AnimatedText> text = Lazy.of(() -> new AnimatedText(130, 130));
    private final Lazy<ColorSelectionList> list = Lazy.of(() -> ColorSelectionList.create(this));

    private final Lazy<BrushCanvas> canvas;
    protected EditBoxOptional titleBox;

    public BrushScreen(BrushMenu menu, Inventory inventory, Component title) {
        super(menu, new InventoryNoTitleWrapper(inventory), title);

        width = 212;
        height = 256;

        imageWidth = 212;
        imageHeight = 256;

        canvas =  Lazy.of(() -> new BrushCanvas(this, CLIENT_TEXTURE, leftPos + 58, topPos + 11, 130, 130, menu::getColorLevel, menu::setColorLevel, SCAConstants.COLOR_WHITE, InkType.BLACK));
    }

    @Override
    protected void init() {
        super.init();

        // qyl27: Ensure the text below the canvas.
        addRenderableOnly(text.get().resize(leftPos + 58 + (130 / 2 - 10), topPos + 11 + 132, font));

        list.get().setRelativeLocation(leftPos, topPos);
        addRenderableWidget(list.get());

        addRenderableWidget(canvas.get());

        CLIENT_TEXTURE.placeButton("copy_button", this)
                .onLeftClick(this::copyDraw)
                .onRightClick(this::pasteDraw);
        CLIENT_TEXTURE.placeButton("output_button", this).onLeftClick(this::saveToFile);
        CLIENT_TEXTURE.placeButton("draw_apply_button", this).onLeftClick(this::applyDraw);
        CLIENT_TEXTURE.placeButton("draw_clear_button", this).onLeftClick(this::clearDraw);
        titleBox = CLIENT_TEXTURE.placeEditBox("draw_name", this)
                .setResponder(this::onTitleChanged)
                .focused();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        titleBox.handleTick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        CLIENT_TEXTURE.blitTexture(graphics, "background", this, GLSwitcher.blend().enable(), GLSwitcher.depth().enable());
        CLIENT_TEXTURE.blitTexture(graphics, "draw_title_box_texture", this, GLSwitcher.blend().enable(), GLSwitcher.depth().enable());
        CLIENT_TEXTURE.blitTexture(graphics, "draw_button_yes_texture", this, GLSwitcher.blend().enable(), GLSwitcher.depth().enable());
        CLIENT_TEXTURE.blitTexture(graphics, "draw_button_no_texture", this, GLSwitcher.blend().enable(), GLSwitcher.depth().enable());
    }

    /// <editor-fold desc="Handle input.">

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (canvas.get().isEnabled()) {
            canvas.get().mouseClicked(mouseX, mouseY, button);
        }

        titleBox.focused();

        // Fixme: qyl27: titleBox focus can not re-gain after lost.
        return titleBox.handleMouseClicked(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (canvas.get().isEnabled()) {
            canvas.get().mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (canvas.get().isEnabled()) {
            canvas.get().mouseMoved(mouseX, mouseY);
        }

        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        canvas.get().mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (canvas.get().isEnabled()
                && (canvas.get().isMouseOver(mouseX, mouseY) || list.get().isMouseOver(mouseX, mouseY))) {
            list.get().mouseScrolled(mouseX, mouseY, delta);
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (canvas.get().isEnabled()) {
            canvas.get().keyPressed(keyCode, scanCode, modifiers);
        }

        if (keyCode == 256) {
            assert minecraft != null;
            assert minecraft.player != null;
            minecraft.player.closeContainer();
        }

        return titleBox.handleKeyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (canvas.get().isEnabled()) {
            canvas.get().keyReleased(keyCode, scanCode, modifiers);
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return titleBox.handleCharTyped(codePoint, modifiers) || super.charTyped(codePoint, modifiers);
    }

    /// </editor-fold>

    public ColorSelectionList getColorSelection() {
        return list.get();
    }

    public AnimatedText getText() {
        return text.get();
    }

    public BrushCanvas getCanvas() {
        return canvas.get();
    }

    public EditBoxOptional getTitleBox() {
        return titleBox;
    }

    public void updateCanvas(int paperColor, InkType inkType) {
        canvas.get().setPaperType(paperColor);
        canvas.get().setInkType(inkType);
    }

    /// <editor-fold desc="Button pressed.">

    private void copyDraw(Button button) {
        var drawing = canvas.get().getDrawing();
        var data = drawing.serializeNBT().getAsString();
        Minecraft.getInstance().keyboardHandler.setClipboard(data);
        text.get().begin(Duration.ofSeconds(1), 0, 0, 255, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_COPIED));
    }

    private void pasteDraw(Button button) {
        String data = Minecraft.getInstance().keyboardHandler.getClipboard();
        try {
            canvas.get().getDrawing().deserializeNBT(TagParser.parseTag(data));
            text.get().begin(Duration.ofSeconds(1), 0, 255, 0, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_PASTED));
        } catch (Exception ex) {
            text.get().begin(Duration.ofSeconds(1), 0, 255, 0, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_PASTE_FAILED));
//            SinoCalligraphy.LOGGER.warn(ex.toString());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void saveToFile(Button button) {
        var player = Minecraft.getInstance().player;
        var drawing = canvas.get().getDrawing();
        try (var image = DrawingHelper.toNaiveImage(drawing)) {
            File name = new File(Minecraft.getInstance().gameDirectory,
                    "sinoseries/sinocalligraphy/drawings/" + drawing.getAuthor().getString() +
                            "/" + System.currentTimeMillis() + ".png");
            name.getParentFile().mkdirs();
            if (!name.exists()) {
                name.createNewFile();
            }
            image.writeToFile(name);
            if (player != null) {
                var path = Component.literal(name.toString());
                path.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, name.getAbsolutePath()));

                player.displayClientMessage(Component.translatable(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS,
                        path), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (player != null) {
                player.displayClientMessage(Component.translatable(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_FAIL,
                        e.getMessage()), false);
            }
        }
    }

    private void clearDraw(Button button) {
        canvas.get().clear();
    }

    private void applyDraw(Button button) {
        SinoCalligraphy.getInstance().getNetworking().sendToServer(new DrawingSaveC2SPacket(canvas.get().getDrawing()));
    }

    private void onTitleChanged(String title) {
        Optional<String> value = titleBox.getValue();
        if (value.isEmpty()) {
            canvas.get().getDrawing().setTitle(Component.translatable(SCAConstants.TRANSLATE_DRAWING_TITLE_UNKNOWN_KEY));
        } else {
            canvas.get().getDrawing().setTitle(value.get());
        }
    }

    /// </editor-fold>
}
