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

import com.tipplerow.jam.vector.VectorView;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Scott Shaffer
 */
@Builder @ToString
public final class StatSummary {
    /**
     * The minimum observation in the data set.
     */
    @Getter
    private final double min;

    /**
     * The first quartile in the data set.
     */
    @Getter
    private final double Q1;

    /**
     * The median value in the data set.
     */
    @Getter
    private final double median;

    /**
     * The mean value in the data set.
     */
    @Getter
    private final double mean;

    /**
     * The standard deviation of the data set.
     */
    @Getter
    private final double stdDev;

    /**
     * The third quartile in the data set.
     */
    @Getter
    private final double Q3;

    /**
     * The maximum observation in the data set.
     */
    @Getter
    private final double max;

    /**
     * The summary for an empty data set.
     */
    public static final StatSummary EMPTY =
            builder()
                    .min(Double.NaN)
                    .Q1(Double.NaN)
                    .median(Double.NaN)
                    .mean(Double.NaN)
                    .Q3(Double.NaN)
                    .max(Double.NaN)
                    .build();

    /**
     * Computes the statistical summary for a data set.
     *
     * @param data the data set to summarize.
     *
     * @return the statistical summary for the specified data set.
     */
    public static StatSummary compute(double[] data) {
        return compute(VectorView.of(data));
    }

    /**
     * Computes the statistical summary for a data set.
     *
     * @param data the data set to summarize.
     *
     * @return the statistical summary for the specified data set.
     */
    public static StatSummary compute(VectorView data) {
        //
        // We could use the individual univariate statistics, but it is
        // more efficient to compute the quantities as a group...
        //
        int count = 0;
        double sum = 0.0;
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (int index = 0; index < data.length(); ++index) {
            double x = data.get(index);

            if (!Double.isNaN(x)) {
                ++count;
                sum += x;
                min = Math.min(x, min);
                max = Math.min(x, max);
            }
        }

        if (count < 1)
            return EMPTY;

        double mean = sum / count;
        double stdDev = Double.NaN;

        if (count > 1)
            stdDev = Stat.norm2(data.minus(mean)) / Math.sqrt(count - 1);

        QuantileCalculator calculator = QuantileCalculator.create(data);

        return builder()
                .min(min)
                .max(max)
                .mean(mean)
                .stdDev(stdDev)
                .Q1(calculator.compute(Quantiles.Q1))
                .Q3(calculator.compute(Quantiles.Q3))
                .median(calculator.compute(Quantiles.MEDIAN))
                .build();
    }

    /**
     * Returns the variance of the data set.
     * @return the variance of the data set.
     */
    public double getVariance() {
        return Stat.toVariance(stdDev);
    }
}
