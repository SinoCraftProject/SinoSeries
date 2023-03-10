package games.moegirl.sinocraft.sinocore.old.utility.render;

public class UVPairFloat {
    public float u;
    public float v;

    public UVPairFloat() {
        this(0.0f, 0.0f);
    }

    public UVPairFloat(UVPairFloat p) {
        this(p.u, p.v);
    }

    public UVPairFloat(float xLoc, float yLoc) {
        u = xLoc;
        v = yLoc;
    }

    public double getU() {
        return u;
    }

    public double getV() {
        return v;
    }

    public void set(float xLoc, float yLoc) {
        u = xLoc;
        v = yLoc;
    }

    public void add(float dx, float dy) {
        u += dx;
        v += dy;
    }

    public void multiply(float mX, float mY) {
        u *= mX;
        v *= mY;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UVPairFloat) {
            UVPairFloat pair = (UVPairFloat) obj;
            return (u == pair.u) && (v == pair.v);
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "{UVPair: {u: " + u + ", v: " + v + "}}";
    }
}
