package games.moegirl.sinocraft.sinocalligraphy.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.list.SelectionEntry;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.list.AbstractSelectionList;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.list.VerticalListLayout;
import games.moegirl.sinocraft.sinocalligraphy.gui.screen.BrushScreen;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinocore.utility.texture.PointEntry;
import games.moegirl.sinocraft.sinocore.utility.texture.TextureEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

import static games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu.TEXTURE;

public class ColorSelectionList extends AbstractSelectionList<ColorSelectionList.ColorEntry> {
    public static final TextureEntry COLOR = TEXTURE.textures().ensureGet("color");
    public static final TextureEntry COLOR_SELECTOR = TEXTURE.textures().ensureGet("color_selector");
    public static final PointEntry SELECTED = TEXTURE.points().ensureGet("selected");

    private static final TextureMapClient CLIENT = new TextureMapClient(TEXTURE);

    public static ColorSelectionList create(BrushScreen screen) {
        int width = COLOR.w();
        int border = (COLOR_SELECTOR.w() - width) / 2;
        int height = COLOR_SELECTOR.h() - border * 2;
        int x = COLOR_SELECTOR.x() + border;
        int y = COLOR_SELECTOR.y() + border;
        return new ColorSelectionList(screen, width, height, x, y, screen.getGuiTop(), screen.getGuiLeft());
    }

    private final BrushScreen screen;
    private int dy = 0;

    public ColorSelectionList(BrushScreen screen, int width, int height, int x, int y, int top, int left) {
        setSize(left, top, x, y, width, height);
        this.screen = screen;
        setLayout(new VerticalListLayout<>(this, COLOR.w(), COLOR.h() + 6));
        setItems(IntStream.range(0, 16).mapToObj(c -> new ColorEntry(this, c)).toList());
    }

    @Override
    public AbstractSelectionList<ColorEntry> setSize(int left, int top, int x, int y, int width, int height) {
        super.setSize(left, top, x, y, width, height);
        dy = SELECTED.y() - y - 6;
        return this;
    }

    @Override
    public void notifyListChanged() {
        super.notifyListChanged();
        if (selected == null) {
            setSelectedItem(getItems().get(screen.getMenu().getColorLevel()));
        } else {
            screen.getMenu().setColorLevel(selected.getValue().index);
        }
    }

    @Override
    public void setSelectedItem(@Nullable SelectionEntry<ColorEntry> selected) {
        super.setSelectedItem(selected);
        screen.getMenu().setColorLevel(selected == null ? 0 : selected.getValue().index);
    }

    @Override
    protected void drawCanvas(List<SelectionEntry<ColorEntry>> displayItems, PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (!items.isEmpty()) {
            int rollY = layout.getRollY();
            SelectionEntry<ColorEntry> entry;
            if (rollY >= layout.getCanvasHeight() - dy) {
                entry = items.get(items.size() - 1);
            } else if (rollY <= -dy) {
                entry = items.get(0);
            } else if ((entry = getEntryAt(SELECTED.x() + guiLeft, SELECTED.y() + guiTop)) == null) {
                int y = SELECTED.y();
                for (SelectionEntry<ColorEntry> item : displayItems) {
                    if (entry == null || Math.abs(entry.getYInGui() - y) > Math.abs(item.getYInGui() - y)) {
                        entry = item;
                    }
                }
            }
            if (entry != null) {
                setSelectedItem(entry);
            }
        }
        if (selected != null) {
            int targetY = selected.getYInCanvas() - dy;
            int dY = targetY - layout.getRollY();
            if (Math.abs(dY) <= 3) {
                layout.rollY(dY);
            } else {
                layout.rollY((int) (dY * 0.3));
            }
        }
        CLIENT.bindTexture();
        for (SelectionEntry<ColorEntry> entry : displayItems) {
            int x = entry.getXInList();
            int y = entry.getYInList() + 3;
            float c = entry.getValue().color;
            RenderSystem.setShaderColor(c, c, c, 1);
            blit(poseStack, x, y, COLOR.w(), COLOR.h(), COLOR.u(), COLOR.v(), COLOR.tw(), COLOR.th(), TEXTURE.width(), TEXTURE.height());
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        CLIENT.blitTexture(poseStack, "color_selector", screen, GLSwitcher.blend().enable());
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta != 0 && !screen.getMenu().getInk().isEmpty() && !screen.getMenu().getPaper().isEmpty()) {
            return super.mouseScrolled(mouseX, mouseY, delta);
        }
        return false;
    }

    @Override
    protected boolean onClick(int mouseX, int mouseY, int button) {
        if (hovered != null && !screen.getMenu().getInk().isEmpty() && !screen.getMenu().getPaper().isEmpty()) {
            setSelectedItem(hovered);
            return true;
        }
        return false;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.HOVERED;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }

    public SelectionEntry<ColorEntry> getEntry(int color) {
        var c = 0;
        if (color >= 0 && color <= 16) {
            c = color;
        }

        final var fC = c;
        var result = getItems().stream().filter(i -> i.getValue().index == fC).findFirst();
        return result.orElseThrow();
    }

    record ColorEntry(int index, float color, int rollTarget) {
        ColorEntry(ColorSelectionList list, int index) {
            this(index, 1 - index / 16f, COLOR.h() * index + list.top - SELECTED.y() - COLOR.h() / 2);
        }
    }
}
