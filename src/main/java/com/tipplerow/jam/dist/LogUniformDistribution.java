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
 * Represents a probability distribution in which the logarithm of the
 * random variable is distributed uniformly within a fixed range.
 *
 * @author Scott Shaffer
 */
public final class LogUniformDistribution extends LogDerivedDistribution {
    private final UniformRealDistribution uniform;

    /**
     * Creates a log-uniform probability distribution for a fixed
     * logarithmic range.
     *
     * @param lowerLog the lower bound of the logarithm of the random
     * variable.
     *
     * @param upperLog the upper bound of the logarithm of the random
     * variable.
     *
     * @throws IllegalArgumentException if {@code lower >= upper}.
     */
    public LogUniformDistribution(double lowerLog, double upperLog) {
        this.uniform = new UniformRealDistribution(lowerLog, upperLog);
    }

    /**
     * Returns the lower bound of the logarithm of the random variable.
     *
     * @return the lower bound of the logarithm of the random variable.
     */
    public double getLowerLog() {
        return uniform.getLower();
    }

    /**
     * Returns the upper bound of the logarithm of the random variable.
     *
     * @return the upper bound of the logarithm of the random variable.
     */
    public double getUpperLog() {
        return uniform.getUpper();
    }

    @Override protected RealDistribution getLogDistribution() {
        return uniform;
    }
}
