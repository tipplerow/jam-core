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

import org.apache.commons.math3.special.Gamma;

import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.math.IntRange;

/**
 * Implements a discrete Poisson distribution.
 *
 * @author Scott Shaffer
 */
public abstract class PoissonDistribution extends AbstractDiscreteDistribution {
    private final double mean;

    // Use the Knuth sampling algorithm for mean values above this
    // limit...
    private static final double KNUTH_MEAN_LIMIT = 1.0;

    // Use a normal approximation for mean values above this limit...
    private static final double NORMAL_MEAN_LIMIT = 50.0;

    /**
     * The range of valid mean values.
     */
    public static final DoubleRange MEAN_RANGE = DoubleRange.POSITIVE;

    /**
     * Creates a discrete Poisson distribution.
     *
     * @param mean the mean of the distribution.
     *
     * @throws IllegalArgumentException unless the mean is positive.
     */
    protected PoissonDistribution(double mean) {
        MEAN_RANGE.validate("Mean", mean);
        this.mean = mean;
    }

    private static void validateMean(double mean) {
        if (mean <= 0.0)
            throw new IllegalArgumentException("Mean must be positive.");
    }

    /**
     * Creates a discrete Poisson distribution.
     *
     * @param mean the mean of the distribution.
     *
     * @return the new Poisson distribution.
     *
     * @throws IllegalArgumentException unless the mean is positive.
     */
    public static PoissonDistribution create(double mean) {
        if (mean < KNUTH_MEAN_LIMIT)
            return new PoissonDistributionExact(mean);
        else if (mean < NORMAL_MEAN_LIMIT)
            return new PoissonDistributionKnuth(mean);
        else
            return new PoissonDistributionNormal(mean);
    }

    /**
     * Computes the probability mass function for a Poisson
     * distribution with a given mean.
     *
     * @param k the point at which to evaluate the probability.
     *
     * @param mean the mean of the distribution.
     *
     * @return the probability mass function for a Poisson
     * distribution with the given mean.
     */
    public static double pdf(int k, double mean) {
        if (k < 0)
            return 0.0;
        else
            return Math.exp(logPDF(k, mean));
    }

    /**
     * Computes the <em>logarithm</em> of the probability mass
     * function for a Poisson distribution with a given mean.
     *
     * @param k the point at which to evaluate the log-probability.
     *
     * @param mean the mean of the distribution.
     *
     * @return the <em>logarithm</em> of the probability mass function
     * for a Poisson distribution with the given mean.
     */
    public static double logPDF(int k, double mean) {
        validateMean(mean);

        if (k < 0)
            return Double.NEGATIVE_INFINITY;
        else
            return -mean + k * Math.log(mean) - Gamma.logGamma(k + 1);
    }

    @Override public IntRange effectiveRange() {
        int lower;
        int upper;

        if (mean < 0.01) {
            lower = 0;
            upper = 3;
        }
        else if (mean < 0.1) {
            lower = 0;
            upper = 6;
        }
        else if (mean < 1.0) {
            lower = 0;
            upper = 12;
        }
        else if (mean < 10.0) {
            lower = 0;
            upper = (int) Math.ceil(12.0 * sdev());
        }
        else {
            lower = Math.max(0, (int) Math.floor(mean - 8.0 * sdev()));
            upper = (int) Math.ceil(mean + 8.0 * sdev());
        }

        return IntRange.instance(lower, upper);
    }

    @Override public double pdf(int k) {
        return pdf(k, mean);
    }

    @Override public double mean() {
	return mean;
    }

    @Override public double median() {
        //
        // Approximate expression...
        //
	return Math.max(0.0, Math.floor(mean() + 1.0 / 3.0 - 0.02 / mean()));
    }

    @Override public double variance() {
	return mean();
    }

    @Override public IntRange support() {
        return IntRange.NON_NEGATIVE;
    }
}
