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
package com.tipplerow.jam.stat;

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.vector.VectorView;

import java.util.stream.DoubleStream;

/**
 * Defines an interface for univariate statistics.
 *
 * @author Scott Shaffer
 */
public interface Stat {
    /**
     * Computes the statistic from a vector of data.
     *
     * @param data the data to process.
     *
     * @return the value of this statistic for the specified data.
     */
    double compute(VectorView data);

    /**
     * Computes the statistic from an array of data.
     *
     * @param data the data to process.
     *
     * @return the value of this statistic for the specified data.
     */
    default double compute(double[] data) {
        return compute(VectorView.of(data));
    }

    /**
     * Computes the statistic from a stream of data.
     *
     * @param data the data to process.
     *
     * @return the value of this statistic for the specified data.
     */
    default double compute(DoubleStream data) {
        return compute(VectorView.of(data));
    }

    /**
     * Returns the maximum statistic calculator.
     * @return the maximum statistic calculator.
     */
    static Stat max() {
        return StreamMax.INSTANCE;
    }

    /**
     * Finds the maximum value in a vector.
     *
     * @param data the data to process.
     *
     * @return the maximum value in the specified vector.
     */
    static double max(VectorView data) {
        return max().compute(data);
    }

    /**
     * Returns the mean statistic calculator.
     * @return the mean statistic calculator.
     */
    static Stat mean() {
        return StreamMean.INSTANCE;
    }

    /**
     * Finds the mean value in a vector.
     *
     * @param data the data to process.
     *
     * @return the mean value in the specified vector.
     */
    static double mean(VectorView data) {
        return mean().compute(data);
    }

    /**
     * Returns the median statistic calculator.
     * @return the median statistic calculator.
     */
    static Stat median() {
        return VectorMedian.INSTANCE;
    }

    /**
     * Finds the mean value in a vector.
     *
     * @param data the data to process.
     *
     * @return the mean value in the specified vector.
     */
    static double median(VectorView data) {
        return median().compute(data);
    }

    /**
     * Returns the minimum statistic calculator.
     * @return the minimum statistic calculator.
     */
    static Stat min() {
        return StreamMin.INSTANCE;
    }

    /**
     * Finds the minimum value in a vector.
     *
     * @param data the data to process.
     *
     * @return the minimum value in the specified vector.
     */
    static double min(VectorView data) {
        return min().compute(data);
    }

    /**
     * Returns the 1-norm statistic calculator.
     * @return the 1-norm statistic calculator.
     */
    static Stat norm1() {
        return StreamNorm1.INSTANCE;
    }

    /**
     * Computes the 1-norm for the finite values in a vector:
     * the sum of the absolute values.
     *
     * @param data the data to process.
     *
     * @return the 1-norm for the finite values in the vector.
     */
    static double norm1(VectorView data) {
        return norm1().compute(data);
    }

    /**
     * Returns the 2-norm statistic calculator.
     * @return the 2-norm statistic calculator.
     */
    static Stat norm2() {
        return StreamNorm2.INSTANCE;
    }

    /**
     * Computes the 2-norm for the finite values in a vector:
     * the square root of the sum of the squared values.
     *
     * @param data the data to process.
     *
     * @return the 2-norm for the finite values in the vector.
     */
    static double norm2(VectorView data) {
        return norm2().compute(data);
    }

    /**
     * Returns the sum statistic calculator.
     * @return the sum statistic calculator.
     */
    static Stat sum() {
        return StreamSum.INSTANCE;
    }

    /**
     * Computes the sum of the finite values in a vector.
     *
     * @param data the data to process.
     *
     * @return the sum of the finite values in the vector.
     */
    static double sum(VectorView data) {
        return sum().compute(data);
    }

    /**
     * Converts a variance into a standard deviation.
     *
     * @param variance the variance to convert.
     *
     * @return the standard deviation that corresponds to the variance.
     *
     * @throws RuntimeException if the variance is negative.
     */
    static double toStdDev(double variance) {
        validateVariance(variance);
        return Math.sqrt(variance);
    }

    /**
     * Converts a standard deviation into a variance.
     *
     * @param stdDev the standard deviation to convert.
     *
     * @return the variance that corresponds to the standard deviation.
     *
     * @throws RuntimeException if the standard deviation is negative.
     */
    static double toVariance(double stdDev) {
        validateVariance(stdDev);
        return stdDev * stdDev;
    }

    /**
     * Ensures that a variance is positive.
     *
     * @param variance the variance to validate.
     *
     * @throws RuntimeException if the variance is negative.
     */
    static void validateVariance(double variance) {
        if (DoubleComparator.DEFAULT.isNegative(variance))
            throw new IllegalArgumentException("Negative variance.");
    }
}

