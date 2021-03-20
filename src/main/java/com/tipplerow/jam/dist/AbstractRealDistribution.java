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
import com.tipplerow.jam.stat.StatSummary;

/**
 * Provides a skeleton implementation of the {@code RealDistribution}
 * interface.
 *
 * @author Scott Shaffer
 */
public abstract class AbstractRealDistribution implements RealDistribution {
    //
    // Number of samples used to compute mean, median, or variance
    // when analytical values are not available.
    //
    private static final int SAMPLE_COUNT = 1000000;

    /**
     * Computes a standard Z-score.
     *
     * @param x an observation.
     *
     * @param mean the mean of a real distribution.
     *
     * @param sdev the standard deviation of a real distribution.
     *
     * @return the standard Z-score: {@code (x - mean) / sdev}.
     */
    public static double scoreZ(double x, double mean, double sdev) {
        validateSD(sdev);
        return (x - mean) / sdev;
    }

    /**
     * Validates a standard deviation.
     *
     * @param sdev the standard deviation to validate.
     *
     * @throws IllegalArgumentException unless the standard deviation
     * is positive.
     */
    public static void validateSD(double sdev) {
        if (sdev <= 0.0)
            throw new IllegalArgumentException("Non-positive standard deviation.");
    }

    @Override public double cdf(double lower, double upper) {
        DoubleRange.validate(lower, upper);
        return cdf(upper) - cdf(lower);
    }

    @Override public double mean() {
	return sampleSummary().getMean();
    }

    @Override public double median() {
	return sampleSummary().getMedian();
    }

    @Override public double sdev() {
	return Math.sqrt(variance());
    }

    @Override public double variance() {
	return sampleSummary().getVariance();
    }

    private StatSummary sampleSummary() {
	return StatSummary.compute(sample(SAMPLE_COUNT));
    }

    @Override public double sample(JamRandom source) {
        return quantile(source.nextDouble());
    }

    @Override public double sample() {
        return sample(JamRandom.global());
    }

    @Override public double[] sample(int count) {
        return sample(JamRandom.global(), count);
    }

    @Override public double[] sample(JamRandom source, int count) {
        double[] values = new double[count];

        for (int index = 0; index < values.length; index++)
            values[index] = sample(source);

        return values;
    }
}
