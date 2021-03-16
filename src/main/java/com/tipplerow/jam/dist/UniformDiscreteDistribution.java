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

import com.tipplerow.jam.math.DoubleUtil;
import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.JamRandom;

/**
 * Implements a uniform discrete probability distribution.
 *
 * @author Scott Shaffer
 */
public final class UniformDiscreteDistribution extends AbstractDiscreteDistribution {
    private final int lower; // Inclusive
    private final int upper; // Exclusive
    private final double density;
    private final IntRange support;

    /**
     * Creates a discrete distribution with integer values distributed
     * uniformly on the interval {@code (lower, upper]}.
     *
     * @param lower the lower bound of the interval (inclusive).
     *
     * @param upper the upper bound of the interval (exclusive).
     *
     * @throws IllegalArgumentException unless the interval is valid.
     */
    public UniformDiscreteDistribution(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;

        this.support = IntRange.instance(lower, upper - 1);

        if (support.size() < 1)
            throw new IllegalArgumentException("Invalid support range.");

        this.density = DoubleUtil.ratio(1, support.size());
    }

    /**
     * Returns the lower bound of the interval (inclusive).
     *
     * @return the lower bound of the interval (inclusive).
     */
    public int getLower() {
        return lower;
    }

    /**
     * Returns the upper bound of the interval (exclusive).
     *
     * @return the upper bound of the interval (exclusive).
     */
    public int getUpper() {
        return upper;
    }

    @Override public double cdf(int k) {
        if (k < lower)
            return 0.0;
        else if (k < upper)
            return density * (k + 1 - lower);
        else
            return 1.0;
    }

    @Override public double pdf(int k) {
        if (k < lower)
            return 0.0;
        else if (k < upper)
            return density;
        else
            return 0.0;
    }

    @Override public double mean() {
	return 0.5 * (lower + upper - 1);
    }

    @Override public double median() {
        return mean();
    }

    @Override public IntRange medianRange() {
        return IntRange.instance((int) Math.floor(mean()), (int) Math.ceil(mean()));
    }

    @Override public IntRange support() {
        return support;
    }

    @Override public int sample(JamRandom random) {
        return random.nextInt(lower, upper);
    }
}
