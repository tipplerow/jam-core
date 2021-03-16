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

import com.tipplerow.jam.math.JamRandom;

import java.util.Arrays;

/**
 * Provides an exact implementation of the Poisson distribution.
 *
 * @author Scott Shaffer
 */
public class PoissonDistributionExact extends PoissonDistribution {
    private final double[] sampleCDF;

    PoissonDistributionExact(double mean) {
        super(mean);
        this.sampleCDF = computeSampleCDF(mean);
    }

    private static double[] computeSampleCDF(double mean) {
        //
        // Choose an initial length "L" sure to be larger than the
        // effective maximum value (the value of "k" for which the
        // cumulative distribution is within the default tolerance
        // of 1.0)...
        //
        double[] CDF = new double[100];
        CDF[0] = pdf(0, mean);

        for (int k = 1; k < CDF.length; ++k) {
            CDF[k] = CDF[k - 1] + pdf(k, mean);

            if (1.0 - CDF[k] < 1.0E-15) {
                CDF[k + 1] = 1.0;
                return Arrays.copyOf(CDF, k + 2);
            }
        }

        throw new IllegalStateException("The mean value is too large for explicit CDF sampling.");
    }

    @Override public int sample(JamRandom source) {
        return source.selectCDF(sampleCDF);
    }
}
