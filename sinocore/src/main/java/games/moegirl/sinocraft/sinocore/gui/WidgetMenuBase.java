package games.moegirl.sinocraft.sinocore.gui;

import games.moegirl.sinocraft.sinocore.gui.widgets.SlotStrategy;
import games.moegirl.sinocraft.sinocore.gui.widgets.WidgetLoader;
import games.moegirl.sinocraft.sinocore.gui.widgets.Widgets;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.SlotEntry;
import games.moegirl.sinocraft.sinocore.gui.widgets.entry.SlotsEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class WidgetMenuBase extends AbstractContainerMenu {

    protected final Widgets widgets;

    protected WidgetMenuBase(@Nullable MenuType<?> menuType, int containerId, Widgets widgets) {
        super(menuType, containerId);
        this.widgets = widgets;
    }

    protected WidgetMenuBase(@Nullable MenuType<?> menuType, int containerId, ResourceLocation widgetsName) {
        this(menuType, containerId, WidgetLoader.loadWidgets(widgetsName));
    }

    protected <C extends Container> Slot addSlot(C container, String slotName, int index, SlotStrategy<C> slotType) {
        SlotEntry entry = (SlotEntry) widgets.getWidget(slotName);
        Slot slot = slotType.createSlot(container, index, entry.getX(), entry.getY());
        return addSlot(slot);
    }

    protected <C extends Container> Slot addSlot(C container, String slotsName, int index, int indexForSlots, SlotStrategy<C> slotType) {
        SlotsEntry slots = (SlotsEntry) widgets.getWidget(slotsName);
        SlotEntry entry = slots.getSlot(indexForSlots);
        Slot slot = slotType.createSlot(container, index, entry.getX(), entry.getY());
        return addSlot(slot);
    }

    protected void addSlots(Container container, String name, int beginIndex, SlotStrategy<Container> slotType) {
        SlotsEntry slots = (SlotsEntry) widgets.getWidget(name);
        for (int i = 0; i < slots.slotCount(); i++) {
            SlotEntry slotEntry = slots.getSlot(i);
            Slot slot = slotType.createSlot(container, beginIndex + i, slotEntry.getX(), slotEntry.getY());
            addSlot(slot);
        }
    }
}
