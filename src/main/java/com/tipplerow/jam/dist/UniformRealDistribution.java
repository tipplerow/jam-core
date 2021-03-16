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
import com.tipplerow.jam.math.DoubleUtil;
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents a uniform probability distribution over a fixed range.
 *
 * @author Scott Shaffer
 */
public final class UniformRealDistribution extends AbstractRealDistribution {
    private final double lower;
    private final double upper;
    private final double density;
    private final DoubleRange support;

    /**
     * Creates a uniform probability distribution for a fixed range.
     *
     * @param lower the lower bound of the range.
     *
     * @param upper the upper bound of the range.
     *
     * @throws IllegalArgumentException if {@code lower >= upper}.
     */
    public UniformRealDistribution(double lower, double upper) {
        validateRange(lower, upper);

        this.lower = lower;
        this.upper = upper;

        this.density = computeDensity(lower, upper);
        this.support = DoubleRange.closed(lower, upper);
    }

    private static void validateRange(double lower, double upper) {
        if (lower >= upper)
            throw new IllegalArgumentException("Invalid range.");
    }

    private static double computeDensity(double lower, double upper) {
        return 1.0 / (upper - lower);
    }

    /**
     * Returns the lower bound of the fixed range.
     *
     * @return the lower bound of the fixed range.
     */
    public double getLower() {
        return lower;
    }

    /**
     * Returns the upper bound of the fixed range.
     *
     * @return the upper bound of the fixed range.
     */
    public double getUpper() {
        return upper;
    }

    @Override public double cdf(double x) {
        if (x <= lower)
            return 0.0;

        if (x >= upper)
            return 1.0;

        return density * (x - lower);
    }

    @Override public double pdf(double x) {
        if (x < lower)
            return 0.0;

        if (x > upper)
            return 0.0;

        return density;
    }

    @Override public double quantile(double F) {
        return lower + (upper - lower) * F;
    }

    @Override public double mean() {
	return 0.5 * (lower + upper);
    }

    @Override public double median() {
	return mean();
    }

    @Override public double variance() {
	return DoubleUtil.square(upper - lower) / 12.0;
    }

    @Override public double sample(JamRandom source) {
        return source.nextDouble(lower, upper);
    }

    @Override public DoubleRange support() {
        return support;
    }
}
