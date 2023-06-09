package games.moegirl.sinocraft.sinocalligraphy.gui.components.list;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractSelectionList<T> extends AbstractContainerEventHandler implements Renderable, NarratableEntry {
    protected List<SelectionEntry<T>> displayItems = ImmutableList.of();
    protected SelectionLayout<T> layout = SelectionLayout.empty(this);
    protected final List<SelectionEntry<T>> items = new LinkedList<>();
    @Nullable
    protected SelectionEntry<T> lastGet = null, selected = null, hovered = null;

    public int width, height, left, top, guiLeft, guiTop, x, y;
    public int scrollSensitivity = 20;

    protected int scissorX, scissorY, scissorW, scissorH;

    public AbstractSelectionList<T> setRelativeLocation(int left, int top) {
        return setSize(left, top, x, y, width, height);
    }

    public AbstractSelectionList<T> setSize(int left, int top, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.guiLeft = left;
        this.guiTop = top;
        this.left = left + x;
        this.top = top + y;

        Window window = Minecraft.getInstance().getWindow();
        double scale = window.getGuiScale();
        scissorW = (int) (width * scale);
        scissorH = (int) (height * scale);
        scissorX = (int) (this.left * scale);
        scissorY = (int) (window.getHeight() - scissorH - this.top * scale);
        return this;
    }

    public AbstractSelectionList<T> setLayout(SelectionLayout<T> layout) {
        this.layout = layout;
        return this;
    }

    public List<SelectionEntry<T>> getItems() {
        return items;
    }

    public List<SelectionEntry<T>> getDisplay() {
        return displayItems;
    }

    @Nullable
    public SelectionEntry<T> getEntryAt(int x, int y) {
        if (lastGet != null && lastGet.isIn(x, y)) {
            return lastGet;
        }
        if (selected != null && selected.isIn(x, y)) {
            return selected;
        }
        if (hovered != null && hovered.isIn(x, y)) {
            return hovered;
        }
        for (SelectionEntry<T> entry : displayItems) {
            if (entry.isIn(x, y)) {
                lastGet = entry;
                return entry;
            }
        }
        return null;
    }

    public List<SelectionEntry<T>> getEntries(int x, int y) {
        List<SelectionEntry<T>> result = new ArrayList<>();
        for (SelectionEntry<T> entry : displayItems) {
            if (entry.isIn(x, y)) {
                result.add(entry);
            }
        }
        return result;
    }

    @Nullable
    public SelectionEntry<T> getEntry(T value) {
        for (SelectionEntry<T> item : items) {
            if (Objects.equals(value, item.getValue())) {
                return item;
            }
        }
        return null;
    }

    @Nullable
    public SelectionEntry<T> getSelectedItem() {
        return selected;
    }

    @Nullable
    public SelectionEntry<T> getHoveredItem() {
        return hovered;
    }

    public SelectionLayout<T> getLayout() {
        return layout;
    }

    public void setSelectedItem(@Nullable SelectionEntry<T> selected) {
        if (selected != this.selected) {
            this.selected = selected;
        }
    }

    public void measureAndLayout() {
        if (items.isEmpty()) {
            displayItems = List.of();
            return;
        }
        items.forEach(layout::measureSize);

        SelectionEntry<T> before0 = null;
        SelectionEntry<T> before1 = null;
        for (SelectionEntry<T> entry : items) {
            if (before1 != null) {
                layout.layoutItem(before1, before0, entry);
                before0 = before1;
            }
            before1 = entry;
        }
        if (before1 != null) {
            layout.layoutItem(before1, before0, null);
        }

        measureVisible();
    }

    protected void measureVisible() {
        items.forEach(layout::calculateVisible);
        ArrayList<SelectionEntry<T>> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            SelectionEntry<T> entry = items.get(i);
            entry.setIndex(i);
            if (entry.isVisible()) {
                list.add(entry);
            }
        }
        displayItems = Collections.unmodifiableList(list);
        layout.measureFinished(items);
    }

    public void setItems(Collection<T> elements) {
        items.clear();
        elements.stream()
                .map(e -> new SelectionEntry<>(this, e))
                .forEach(items::add);
        lastGet = null;
        notifyListChanged();
    }

    public void notifyListChanged() {
        measureAndLayout();
        hovered = null;
        if (selected != null && !items.contains(selected)) {
            selected = null;
        }
    }

    protected abstract void drawCanvas(List<SelectionEntry<T>> displayItems, GuiGraphics graphics,
                                       int mouseX, int mouseY, float partialTick);

    protected abstract boolean onClick(int mouseX, int mouseY, int button);

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        measureVisible();
        if (hovered != null && !hovered.isIn(mouseX, mouseY)) {
            hovered = null;
        }
        if (hovered == null) {
            for (SelectionEntry<T> entry : displayItems) {
                if (entry.isIn(mouseX, mouseY)) {
                    hovered = entry;
                }
            }
        }

        RenderSystem.enableScissor(scissorX, scissorY, scissorW, scissorH);
        graphics.pose().pushPose();
        graphics.pose().translate(left, top, 0);
        GLSwitcher b = GLSwitcher.blend().enable();
        drawCanvas(displayItems, graphics, mouseX, mouseY, partialTick);
        graphics.pose().popPose();
        b.disable();
        RenderSystem.disableScissor();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta != 0.0) {
            layout.rollY((int) (-delta * scrollSensitivity));
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= left && mouseX <= left + width && mouseY >= top && mouseY <= top + height;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button) || onClick((int) mouseX, (int) mouseY, button);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return Collections.emptyList();
    }
}
