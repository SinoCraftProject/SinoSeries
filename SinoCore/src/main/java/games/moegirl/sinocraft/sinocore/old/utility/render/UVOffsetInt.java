package games.moegirl.sinocraft.sinocore.old.utility.render;

public class UVOffsetInt {
    public int u;
    public int v;

    public UVOffsetInt() {
        this(0, 0);
    }

    public UVOffsetInt(UVOffsetInt p) {
        this(p.u, p.v);
    }

    public UVOffsetInt(int xLoc, int yLoc) {
        u = xLoc;
        v = yLoc;
    }

    public int getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public void set(int xLoc, int yLoc) {
        u = xLoc;
        v = yLoc;
    }

    public void add(int dx, int dy) {
        u += dx;
        v += dy;
    }

    public void multiply(int mX, int mY) {
        u *= mX;
        v *= mY;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UVOffsetInt) {
            UVOffsetInt pair = (UVOffsetInt) obj;
            return (u == pair.u) && (v == pair.v);
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "{UVOffsetPair: {u: " + u + ", v: " + v + "}}";
    }
}
