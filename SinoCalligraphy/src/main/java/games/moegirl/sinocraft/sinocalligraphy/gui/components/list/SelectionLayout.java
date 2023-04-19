package games.moegirl.sinocraft.sinocalligraphy.gui.components.list;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SelectionLayout<T> {

    public static <T> SelectionLayout<T> empty(AbstractSelectionList<T> list) {
        return new SelectionLayout<>(list) {
            @Override
            public void measureSize(SelectionEntry<T> entry) {
                entry.setSize(0, 0);
            }

            @Override
            public void layoutItem(SelectionEntry<T> entry, @Nullable SelectionEntry<T> previous, @Nullable SelectionEntry<T> next) {
                entry.setPositionInCanvas(0, 0);
            }
        };
    }

    protected final AbstractSelectionList<T> list;
    protected int rollX, rollY;
    protected int canvasWidth, canvasHeight;

    public SelectionLayout(AbstractSelectionList<T> list) {
        this.list = list;
    }

    /**
     * step1 measure item size. Set total size and measure size
     *  in this function, selected is meaningful
     *  width, height should be set.
     */
    public abstract void measureSize(SelectionEntry<T> entry);

    /**
     * Step2 layout the item in a virtual canvas
     *  canvasX, canvasY should be set
     * @param previous the previous entry, if current is the first, it is null
     * @param next the next entry, if current is the last, it is null
     */
    public abstract void layoutItem(SelectionEntry<T> entry, @Nullable SelectionEntry<T> previous, @Nullable SelectionEntry<T> next);

    /**
     * Step3 calculate positions:
     *  itemX, itemY, viewX, viewY, renderX, renderY, renderWidth, renderHeight, isVisible should be set
     */
    public void calculateVisible(SelectionEntry<T> entry) {
        entry.setVisible(true);

        int entryX = entry.getXInCanvas() - rollX;
        int entryY = entry.getYInCanvas() - rollY;
        int width = entry.getWidth();
        int height = entry.getHeight();

        entry.setPositionInList(entryX, entryY);
        entry.setPositionInGui(entryX + list.left, entryY + list.top);

        if (entryX < 0 && entryX > -width) {
            entry.setRenderX(-entryX, Math.min(list.width, width - entry.getRenderX()));
        } else if (entryX >= 0 && entryX < list.width) {
            entry.setRenderX(0, Math.min(width, list.width - entryX));
        } else {
            entry.setRenderX(0, 0);
        }

        if (entryY < 0 && entryY > -height) {
            entry.setRenderY(-entryY, Math.min(list.height, height - entry.getRenderY()));
        } else if (entryY >= 0 && entryY < list.height) {
            entry.setRenderY(0, Math.min(height, list.height - entryY));
        } else {
            entry.setRenderY(0, 0);
        }
    }

    /**
     * Step4 finished callback
     *  canvasWidth, canvasHeight in manager should be set
     */
    public void measureFinished(List<SelectionEntry<T>> entries) {
        canvasWidth = 0;
        canvasHeight = 0;

        for (SelectionEntry<T> entry : entries) {
            int width = entry.getXInCanvas() + entry.getWidth();
            if (canvasWidth < width) {
                canvasWidth = width;
            }

            int height = entry.getYInCanvas() + entry.getHeight();
            if (canvasHeight < height) {
                canvasHeight = height;
            }
        }
    }

    public void roll(int x, int y) {
        rollTo(x + rollX, y + rollY);
    }

    public void rollX(int x) {
        rollToX(x + rollX);
    }

    public void rollY(int y) {
        rollToY(y + rollY);
    }

    public void rollTo(int x, int y) {
        rollToX(x);
        rollToY(y);
    }

    public void rollToX(int x) {
        if (x < -list.width) {
            rollX = list.width;
        } else rollX = Math.min(x, canvasWidth);
    }

    public void rollToY(int y) {
        if (y < -list.height) {
            rollY = -list.height;
        } else rollY = Math.min(y, canvasHeight);
    }

    public boolean canRollX() {
        return canvasWidth >= list.height;
    }

    public boolean canRollY() {
        return canvasHeight >= list.height;
    }

    public float getRollProcessX() {
        if (rollX == 0) return 0;
        return Math.min(1, rollX / (float) (canvasWidth - list.width));
    }

    public float getRollProcessY() {
        if (rollY == 0) return 0;
        return Math.min(1, rollY / (float) (canvasHeight - list.width));
    }

    public AbstractSelectionList<T> getList() {
        return list;
    }

    public <LIST extends AbstractSelectionList<T>> LIST getListAs() {
        return (LIST) list;
    }

    public int getRollX() {
        return rollX;
    }

    public int getRollY() {
        return rollY;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }
}
