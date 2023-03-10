package games.moegirl.sinocraft.sinocore.old.api.utility;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;

import java.util.LinkedList;
import java.util.List;

public final class IntModifier {

    private final int baseValue;
    private final List<Int2IntFunction> calcBefore;
    private final List<Int2IntFunction> calcAfter;
    private int delta;

    public IntModifier(int baseValue) {
        this(baseValue, 0, new LinkedList<>(), new LinkedList<>());
    }

    IntModifier(int baseValue, int delta, List<Int2IntFunction> calcBefore, List<Int2IntFunction> calcAfter) {
        this.baseValue = baseValue;
        this.delta = delta;
        this.calcBefore = calcBefore;
        this.calcAfter = calcAfter;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public int getResult() {
        int result = calc(baseValue, calcBefore);
        result += delta;
        return calc(baseValue, calcAfter);
    }

    private int calc(int baseValue, List<Int2IntFunction> funcs) {
        if (funcs.isEmpty()) {
            return baseValue;
        }
        for (Int2IntFunction func : funcs) {
            baseValue = func.apply(baseValue);
        }
        return baseValue;
    }

    public IntModifier add(int value) {
        delta += value;
        return this;
    }

    public IntModifier sub(int value) {
        delta -= value;
        return this;
    }

    public IntModifier mulDelta(int value) {
        delta *= value;
        return this;
    }

    public IntModifier divDelta(int value) {
        delta /= delta;
        return this;
    }

    public IntModifier applyDelta(Int2IntFunction func) {
        delta = func.apply(delta);
        return this;
    }

    public IntModifier calcBefore(Int2IntFunction func) {
        calcBefore.add(func);
        return this;
    }

    public IntModifier calcAfter(Int2IntFunction func) {
        calcAfter.add(func);
        return this;
    }

    public IntModifier copy(int baseValue) {
        return new IntModifier(baseValue, delta, calcBefore, calcAfter);
    }
}
