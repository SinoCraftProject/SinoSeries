package games.moegirl.sinocraft.sinocore.gui;

import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.EditBoxWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.ImageButtonWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.component.ProgressWidget;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.*;
import games.moegirl.sinocraft.sinocore.utility.GLSwitcher;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.Consumer;
import java.util.function.DoubleSupplier;

public class AbstractWidgetScreen<T extends AbstractWidgetMenu> extends AbstractContainerScreen<T> {

    protected final Widgets widgets;

    public AbstractWidgetScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.widgets = menu.widgets;

        if (widgets.containsWidget("background")) {
            TextureEntry entry = (TextureEntry) widgets.getWidget("background");
            imageWidth = entry.getWidth();
            imageHeight = entry.getHeight();
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // qyl27: Do nothing. To prevent render "Inventory" in our GUI.
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        if (widgets.containsWidget("background")) {
            blitTexture(guiGraphics, "background", 0, 0);
        }
    }

    protected ImageButtonWidget addButton(String name, Button.OnPress onPress) {
        ButtonEntry entry = (ButtonEntry) widgets.getWidget(name);
        Component tooltip = entry.getTooltip().map(Component::translatable).orElseGet(Component::empty);
        ImageButtonWidget button = new ImageButtonWidget(this, entry, onPress, tooltip);
        addRenderableWidget(button);
        return button;
    }

    protected EditBoxWidget addEditBox(String name, Font font) {
        EditBoxEntry entry = (EditBoxEntry) widgets.getWidget(name);
        EditBoxWidget editBox = new EditBoxWidget(font, entry, leftPos, topPos);
        addRenderableWidget(editBox);
        editBox.initializeFocus(this);
        return editBox;
    }

    protected EditBoxWidget addEditBox(String name, Font font, Consumer<String> responder) {
        EditBoxWidget editBox = addEditBox(name, font);
        editBox.setResponder(responder);
        return editBox;
    }

    protected EditBoxWidget addEditBox(String name) {
        return addEditBox(name, font);
    }

    protected EditBoxWidget addEditBox(String name, Consumer<String> responder) {
        return addEditBox(name, font, responder);
    }

    protected ProgressWidget addProgress(GuiGraphics guiGraphics, String name, DoubleSupplier progress) {
        ProgressEntry entry = (ProgressEntry) widgets.getWidget(name);
        ProgressWidget widget = new ProgressWidget(leftPos, topPos, widgets, entry, progress);
        addRenderableWidget(widget);
        return widget;
    }

    protected void drawText(GuiGraphics guiGraphics, String name, Font font) {
        TextEntry entry = (TextEntry) widgets.getWidget(name);
        if (entry.getText().isEmpty()) {
            if (entry.getRawText().isPresent()) {
                drawText(guiGraphics, font, entry, Component.literal(entry.getRawText().get()));
            }
        } else {
            drawText(guiGraphics, font, entry, Component.translatable(entry.getText().get()));
        }
    }

    protected void drawText(GuiGraphics guiGraphics, String name) {
        drawText(guiGraphics, name, font);
    }

    private void drawText(GuiGraphics guiGraphics, Font font, TextEntry entry, Component text) {
        int tx = entry.getX();
        int ty = entry.getY();
        if (entry.isCenter()) {
            tx += font.width(text) / 2;
        }
        guiGraphics.drawString(font, text, tx, ty, entry.getColor(), entry.hasShadow());
    }

    public void blitTexture(GuiGraphics guiGraphics, String name) {
        TextureEntry entry = (TextureEntry) widgets.getWidget(name);
        blitTexture(guiGraphics, name, entry.getX(), entry.getY(), entry.getWidth(), entry.getHeight());
    }

    public void blitTexture(GuiGraphics guiGraphics, String name, int x, int y) {
        TextureEntry entry = (TextureEntry) widgets.getWidget(name);
        blitTexture(guiGraphics, name, x, y, entry.getWidth(), entry.getHeight());
    }

    public void blitTexture(GuiGraphics guiGraphics, String name, int x, int y, int width, int height) {
        TextureEntry entry = (TextureEntry) widgets.getWidget(name);
        guiGraphics.blit(widgets.getTexture(), leftPos + x, topPos + y, width, height, entry.getTextureX(), entry.getTextureY(),
                entry.getTextureWidth(), entry.getTextureHeight(), widgets.getWidth(), widgets.getHeight());
    }

    public void blitTexture(GuiGraphics guiGraphics, String name, int x, int y, int width, int height, GLSwitcher... configurations) {
        blitTexture(guiGraphics, name, x, y, width, height);
        for (GLSwitcher switcher : configurations) {
            switcher.resume();
        }
    }

    public int getLeftPos() {
        return leftPos;
    }

    public int getTopPos() {
        return topPos;
    }

    public Font getFont() {
        return font;
    }

    public Widgets getWidgets() {
        return widgets;
    }
}
