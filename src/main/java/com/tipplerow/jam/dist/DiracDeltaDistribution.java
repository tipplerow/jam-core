/*
 * Copyright (C) 2021 Scott Shaffer - All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tipplerow.jam.dist;

import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents a Dirac delta distribution at a fixed impulse location.
 *
 * @author Scott Shaffer
 */
public final class DiracDeltaDistribution extends AbstractRealDistribution {
    private final double impulse;

    /**
     * Creates a Dirac delta distribution for a fixed impulse
     * location.
     *
     * @param impulse the location of the impulse.
     */
    public DiracDeltaDistribution(double impulse) {
        this.impulse = impulse;
    }

    /**
     * Returns the location of the impulse.
     *
     * @return the location of the impulse.
     */
    public double getImpulse() {
        return impulse;
    }

    @Override public double cdf(double x) {
        if (x < impulse)
            return 0.0;
        else if (x == impulse)
            return 0.5;
        else
            return 1.0;
    }

    @Override public double pdf(double x) {
        if (x == impulse)
            return Double.POSITIVE_INFINITY;
        else
            return 0.0;
    }

    @Override public double quantile(double F) {
        throw new UnsupportedOperationException("Quantile not well defined for Dirac delta distribution.");
    }

    @Override public double mean() {
	return impulse;
    }

    @Override public double median() {
	return impulse;
    }

    @Override public double variance() {
	return 0.0;
    }

    @Override public double sample(JamRandom source) {
        return impulse;
    }

    @Override public DoubleRange support() {
        return DoubleRange.closed(impulse, impulse);
    }
}
