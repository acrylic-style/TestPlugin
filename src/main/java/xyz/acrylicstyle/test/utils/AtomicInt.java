package xyz.acrylicstyle.test.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInt extends AtomicInteger {
    private final int initialValue;

    public AtomicInt(int initialValue) {
        super(initialValue);
        this.initialValue = initialValue;
    }

    public AtomicInt() {
        super();
        this.initialValue = 0;
    }

    public void reset() {
        this.set(initialValue);
    }
}
