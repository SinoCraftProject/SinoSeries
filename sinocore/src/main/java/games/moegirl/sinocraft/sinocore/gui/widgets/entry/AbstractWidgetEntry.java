package games.moegirl.sinocraft.sinocore.gui.widgets.entry;

import games.moegirl.sinocraft.sinocore.gui.widgets.WidgetLoader;

public sealed class AbstractWidgetEntry
        permits ButtonEntry, EditBoxEntry, PointEntry, ProgressEntry, RectEntry, SlotEntry, SlotsEntry, TextEntry, TextureEntry {

    public static final String UNNAMED = "_UNNAMED_WIDGET_";

    protected String name = UNNAMED;
    protected final String type;

    protected AbstractWidgetEntry() {
        this.type = WidgetLoader.getTypeName(getClass());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        if (!UNNAMED.equals(this.name)) {
            throw new IllegalStateException("Cannot set name to a widget: already has name " + name);
        }
        this.name = name;
    }
}
