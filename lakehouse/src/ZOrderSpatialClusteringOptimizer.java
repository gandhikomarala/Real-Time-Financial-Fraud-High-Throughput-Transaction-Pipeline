package com.financial.pipeline.lakehouse;

import java.io.Serializable;

public class ZOrderSpatialClusteringOptimizer implements Serializable {
    private static final long serialVersionUID = 1L;
    public long interleaveBits(int x, int y) {
        long z = 0;
        for (int i = 0; i < 32; i++) {
            z |= ((long) (x & (1 << i)) << i) | ((long) (y & (1 << i)) << (i + 1));
        }
        return z;
    }
}
