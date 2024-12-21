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

import java.util.List;

public abstract class AbstractWidgetMenu extends AbstractContainerMenu {

    protected final Widgets widgets;

    protected AbstractWidgetMenu(@Nullable MenuType<?> menuType, int containerId, Widgets widgets) {
        super(menuType, containerId);
        this.widgets = widgets;
    }

    protected AbstractWidgetMenu(@Nullable MenuType<?> menuType, int containerId, ResourceLocation widgetsName) {
        this(menuType, containerId, WidgetLoader.loadWidgets(widgetsName));
    }

    protected <C extends Container> Slot addSlot(C container, String slotName, int index, SlotStrategy<C> slotType) {
        SlotEntry entry = (SlotEntry) widgets.getWidget(slotName);
        Slot slot = slotType.createSlot(container, index, entry.getX(), entry.getY());
        return addSlot(slot);
    }

    private void addSlotInternal(SlotStrategy<Container> slotType, Container container, int slot, int x, int y) {
        addSlot(slotType.createSlot(container, slot, x, y));
    }

    /**
     * Add slots of a container.
     * qyl27: Usually used to fill players inventory or containers contents in menu.
     * @param container Container.
     * @param name Widget name.
     * @param beginIndex Begin index in container.
     * @param slotStrategy Strategy of slot.
     */
    protected void addSlots(Container container, String name, int beginIndex, SlotStrategy<Container> slotStrategy) {
        SlotsEntry slots = (SlotsEntry) widgets.getWidget(name);
        for (int i = 0; i < slots.slotCount(); i++) {
            SlotEntry slotEntry = slots.getSlot(i);
            addSlotInternal(slotStrategy, container, beginIndex + i, slotEntry.getX(), slotEntry.getY());
        }
    }

    /**
     * Add slots with a slot blocks operations.
     * qyl27: It was usually used in a handheld container, players can't move the item out.
     * @param container Container.
     * @param name Widget name.
     * @param beginIndex Begin index in container.
     * @param defaultStrategy Strategy of slot.
     * @param blocked Blocked index in container.
     */
    protected void addSlotsWithSlotBlocked(Container container, String name, int beginIndex,
                                           SlotStrategy<Container> defaultStrategy, List<Integer> blocked) {
        SlotsEntry slots = (SlotsEntry) widgets.getWidget(name);
        for (int i = 0; i < slots.slotCount(); i++) {
            SlotEntry slotEntry = slots.getSlot(i);
            var Strategy = blocked.contains(i) ? SlotStrategy.blockAll() : defaultStrategy;
            addSlotInternal(Strategy, container, beginIndex + i, slotEntry.getX(), slotEntry.getY());
        }
    }
}
