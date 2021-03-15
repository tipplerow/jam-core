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

import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.vector.VectorView;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

/**
 * Computes quantiles for a fixed vector of observations.
 *
 * @author Scott Shaffer
 */
public final class QuantileCalculator {
    private final Percentile percentile;

    private QuantileCalculator(double[] array) {
        this.percentile = new Percentile();
        this.percentile.setData(array);
    }

    /**
     * The range of valid quantiles: {@code (0.0, 1.0]}.
     */
    public static final DoubleRange QUANTILE_RANGE = DoubleRange.leftOpen(0.0, 1.0);

    /**
     * Creates a new quantile calculator for a fixed vector of observations.
     *
     * @param data the observations on which to operate.
     *
     * @return a new quantile calculator for the specified observations.
     */
    public static QuantileCalculator create(double[] data) {
        return new QuantileCalculator(data);
    }

    /**
     * Creates a new quantile calculator for a fixed vector of observations.
     *
     * @param data the observations on which to operate.
     *
     * @return a new quantile calculator for the specified observations.
     */
    public static QuantileCalculator create(VectorView data) {
        return new QuantileCalculator(data.toArray());
    }

    /**
     * Validates a quantile value.
     *
     * @param quantile the (fractional) quantile value to compute.
     *
     * @throws RuntimeException unless the quantile value is in the valid
     * range {@code (0.0, 1.0]}.
     */
    public static void validateQuantile(double quantile) {
        QUANTILE_RANGE.validate("quantile", quantile);
    }

    /**
     * Computes a particular quantile value.
     *
     * @param quantile the (fractional) quantile value to compute.
     *
     * @return the specified quantile value for the underlying data.
     *
     * @throws RuntimeException unless the quantile value is in the valid
     * range {@code (0.0, 1.0]}.
     */
    public double compute(double quantile) {
        validateQuantile(quantile);
        return percentile.evaluate(100.0 * quantile);
    }
}
