package games.moegirl.sinocraft.sinocore.old.utility.render;

public class XYPointInt {
    public int x;
    public int y;

    public XYPointInt() {
        this(0, 0);
    }

    public XYPointInt(XYPointInt p) {
        this(p.x, p.y);
    }

    public XYPointInt(int xLoc, int yLoc) {
        x = xLoc;
        y = yLoc;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void set(int xLoc, int yLoc) {
        x = xLoc;
        y = yLoc;
    }

    public void add(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public boolean equals(Object obj) {
        if (obj instanceof XYPointInt point) {
            return (x == point.x) && (y == point.y);
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "{Point: {x: " + x + ", y: " + y + "}}";
    }
}
