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

/**
 * Provides an approximate sampling implementation of the Poisson distribution
 * attributed to Donald Knuth.
 *
 * @author Scott Shaffer
 */
public class PoissonDistributionKnuth extends PoissonDistribution {
    private final int maxit;
    private final double accept;

    PoissonDistributionKnuth(double mean) {
        super(mean);

        this.maxit = (int) Math.round(1000.0 * mean);
        this.accept = Math.exp(-mean);

        if (this.maxit < 1)
            throw new IllegalStateException("Knuth sample is not appropriate for very small mean values.");
    }

    @Override public int sample(JamRandom source) {
        //
        // Algorithm attributed to Knuth...
        //
        int    count = 0;
        double draw  = source.nextDouble();

        while (draw > accept && count < maxit) {
            ++count;
            draw *= source.nextDouble();
        }

        return count;
    }
}
