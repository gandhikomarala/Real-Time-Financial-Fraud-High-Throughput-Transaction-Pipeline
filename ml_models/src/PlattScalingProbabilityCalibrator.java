package com.financial.pipeline.ml;

import java.io.Serializable;

public class PlattScalingProbabilityCalibrator implements Serializable {
    private static final long serialVersionUID = 1L;
    private final double parameterA = -1.45;
    private final double parameterB = 0.12;

    public double calibrateLogit(double rawLogit) {
        return 1.0 / (1.0 + Math.exp(parameterA * rawLogit + parameterB));
    }
}
