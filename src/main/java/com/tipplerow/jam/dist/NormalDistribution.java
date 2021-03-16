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

import org.apache.commons.math3.special.Erf;

import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.math.DoubleUtil;
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents a normal (Gaussian) probability distribution.
 *
 * @author Scott Shaffer
 */
public final class NormalDistribution extends AbstractRealDistribution {
    private final double mean;
    private final double stdev;

    /**
     * The standard normal distribution with zero mean and unit
     * variance.
     */
    public static final NormalDistribution STANDARD = new NormalDistribution(0.0, 1.0);

    /**
     * Creates a normal probability distribution.
     *
     * @param mean the mean of the distribution.
     *
     * @param stdev the standard deviation of the distribution.
     *
     * @throws IllegalArgumentException unless the standard deviation
     * is positive.
     */
    public NormalDistribution(double mean, double stdev) {
        validateStDev(stdev);
        this.mean = mean;
        this.stdev = stdev;
    }

    /**
     * Computes the cumulative distribution function for a normal
     * distribution with a given mean and standard deviation.
     *
     * @param x the location at which to evaluate the CDF.
     *
     * @param mean the mean of the distribution.
     *
     * @param stdev the standard deviation of the distribution.
     *
     * @return the cumulative distribution function for a normal
     * distribution with the given mean and standard deviation.
     *
     * @throws IllegalArgumentException unless the standard deviation
     * is positive.
     */
    public static double cdf(double x, double mean, double stdev) {
        validateStDev(stdev);
        return validCDF(x, mean, stdev);
    }

    private static double validCDF(double x, double mean, double stdev) {
        return 0.5 * (1.0 + Erf.erf(scoreZ(x, mean, stdev) / DoubleUtil.SQRT2));
    }

    /**
     * Computes the probability density function for a normal
     * distribution with a given mean and standard deviation.
     *
     * @param x the location at which to evaluate the PDF.
     *
     * @param mean the mean of the distribution.
     *
     * @param stdev the standard deviation of the distribution.
     *
     * @return the probability density function for a normal
     * distribution with the given mean and standard deviation.
     *
     * @throws IllegalArgumentException unless the standard deviation
     * is positive.
     */
    public static double pdf(double x, double mean, double stdev) {
        validateStDev(stdev);
        return validPDF(x, mean, stdev);
    }

    private static double validPDF(double x, double mean, double stdev) {
        double z = scoreZ(x, mean, stdev);
        return Math.exp(-0.5 * z * z) / (stdev * DoubleUtil.SQRT_TWO_PI);
    }

    @Override public double cdf(double x) {
        return validCDF(x, mean, stdev);
    }

    @Override public double pdf(double x) {
        return validPDF(x, mean, stdev);
    }

    public double quantile(double F) {
        return mean + DoubleUtil.SQRT2 * stdev * Erf.erfInv(2.0 * F - 1.0);
    }

    @Override public double mean() {
	return mean;
    }

    @Override public double median() {
	return mean;
    }

    @Override public double variance() {
	return stdev * stdev;
    }

    @Override public double sample(JamRandom source) {
        return source.nextGaussian(mean, stdev);
    }

    @Override public DoubleRange support() {
        return DoubleRange.INFINITE;
    }
}
