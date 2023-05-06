package games.moegirl.sinocraft.sinodivination.old.util;

import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class ChangableInt implements IntConsumer, IntSupplier, BooleanSupplier {

    private int value;
    private boolean isChanged;

    public ChangableInt(int value, boolean isChanged) {
        this.value = value;
        this.isChanged = isChanged;
    }

    public ChangableInt(int value) {
        this(value, false);
    }

    @Override
    public void accept(int value) {
        set(value);
    }

    @Override
    public int getAsInt() {
        return value;
    }

    @Override
    public boolean getAsBoolean() {
        return isChanged;
    }

    public int get() {
        return value;
    }

    public void set(int value) {
        if (value != this.value) {
            this.value = value;
            isChanged = true;
        }
    }

    public boolean changed() {
        return isChanged;
    }

    public void ready() {
        isChanged = false;
    }

    public void add(int value) {
        if (value != 0) {
            this.value += value;
            isChanged = true;
        }
    }

    public void reduce(int value) {
        if (value != 0) {
            this.value -= value;
            isChanged = true;
        }
    }
}
