package com.financial.pipeline.streaming;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryRingBufferStateStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int bufferCapacity;
    private final AtomicLong headPointer = new AtomicLong(0);

    public MemoryRingBufferStateStore(int bufferCapacity) {
        this.bufferCapacity = bufferCapacity > 0 ? bufferCapacity : 100000;
    }

    public int getCapacity() { return bufferCapacity; }
    public long getCurrentHead() { return headPointer.get(); }
}
