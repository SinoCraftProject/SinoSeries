package games.moegirl.sinocraft.sinodivination.old.util;

import it.unimi.dsi.fastutil.floats.FloatConsumer;

import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class ChangableFloat implements FloatConsumer, BooleanSupplier {

    private float value;
    private boolean isChanged;

    public ChangableFloat(float value, boolean isChanged) {
        this.value = value;
        this.isChanged = isChanged;
    }

    public ChangableFloat(float value) {
        this(value, false);
    }

    @Override
    public void accept(float t) {
        set(t);
    }

    @Override
    public boolean getAsBoolean() {
        return isChanged;
    }

    public float get() {
        return value;
    }

    public void set(float value) {
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

    public void add(float value) {
        if (value != 0) {
            this.value += value;
            isChanged = true;
        }
    }

    public void reduce(float value) {
        if (value != 0) {
            this.value -= value;
            isChanged = true;
        }
    }
}
