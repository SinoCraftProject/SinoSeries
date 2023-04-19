package games.moegirl.sinocraft.sinocalligraphy.gui.components.list;

public class SelectionEntry<T> {
    // list
    private final AbstractSelectionList<T> list;
    private T value;

    // item size
    private int width, height;
    // position in canvas
    private int canvasX, canvasY;
    // position in selection list
    private int listX, listY;
    // position in gui
    private int viewX, viewY;
    // left-top position of draw relative to item
    private int renderX, renderY;
    private int renderWidth, renderHeight;
    // true if the item need to render
    private boolean isVisible = false;
    // true if the item is rendered
    private boolean isDisplay = false;

    // item state
    private int index = -1;

    public SelectionEntry(AbstractSelectionList<T> list, T value) {
        this.list = list;
        this.value = value;
    }

    public AbstractSelectionList<T> getList() {
        return list;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getXInCanvas() {
        return canvasX;
    }

    public int getYInCanvas() {
        return canvasY;
    }

    public void setPositionInCanvas(int x, int y) {
        this.canvasX = x;
        this.canvasY = y;
    }

    public int getXInList() {
        return listX;
    }

    public int getYInList() {
        return listY;
    }

    public void setPositionInList(int x, int y) {
        this.listX = x;
        this.listY = y;
    }

    public int getXInGui() {
        return viewX;
    }

    public int getYInGui() {
        return viewY;
    }

    public void setPositionInGui(int x, int y) {
        this.viewX = x;
        this.viewY = y;
    }

    public int getRenderX() {
        return renderX;
    }

    public int getRenderY() {
        return renderY;
    }

    public int getRenderWidth() {
        return renderWidth;
    }

    public int getRenderHeight() {
        return renderHeight;
    }

    public void setRenderX(int renderX, int width) {
        this.renderX = renderX;
        this.renderWidth = width;

        if (isVisible && renderWidth <= 0) {
            isVisible = false;
        }
    }

    public void setRenderY(int renderY, int height) {
        this.renderY = renderY;
        this.renderHeight = height;

        if (isVisible && renderHeight <= 0) {
            isVisible = false;
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isSelected() {
        return list.getSelectedItem() == this;
    }

    public boolean isHovered() {
        return list.getHoveredItem() == this;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    void setDisplay(boolean display) {
        isDisplay = display;
    }

    public int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    public boolean isIn(int x, int y) {
        return x > viewX && x < viewX + renderWidth && y > viewY && y < viewY + renderHeight;
    }
}
