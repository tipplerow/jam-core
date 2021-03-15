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

import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Computes and stores quantiles for a data set.
 *
 * @author Scott Shaffer
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Quantiles {
    /**
     * The vector of probabilities that define the quantile locations.
     */
    @Getter
    private final VectorView probs;

    /**
     * The vector of quantile values corresponding to the probabilities.
     */
    @Getter
    private final VectorView values;

    /**
     * The first quartile.
     */
    public static final double Q1 = 0.25;

    /**
     * The median quartile.
     */
    public static final double MEDIAN = 0.50;

    /**
     * The third quartile.
     */
    public static final double Q3 = 0.75;

    /**
     * Creates a new quantile set for fixed quantile locations and a fixed
     * data set.
     *
     * @param data  the observations to quantize.
     * @param probs the probabilities that define the quantile locations.
     *
     * @return the quantile set corresponding to the specified observations
     * and quantile locations.
     *
     * @throws RuntimeException unless all quantile locations are valid.
     */
    public static Quantiles compute(VectorView data, double... probs) {
        JamVector probVector = JamVector.copyOf(probs);
        JamVector valueVector = JamVector.dense(probs.length);
        QuantileCalculator calculator = QuantileCalculator.create(data);

        for (int index = 0; index < probs.length; ++index)
            valueVector.set(index, calculator.compute(probs[index]));

        return new Quantiles(probVector, valueVector);
    }

    /**
     * Computes the summary quantile set consisting of the first quartile,
     * the median, and the third quartile.
     *
     * @param data the observations to quantize.
     *
     * @return the summary quantile set for the specified observations.
     */
    public static Quantiles summary(VectorView data) {
        return compute(data, Q1, MEDIAN, Q3);
    }
}
