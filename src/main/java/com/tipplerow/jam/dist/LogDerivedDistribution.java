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

/**
 * Represents a probability distribution in which the logarithm of the
 * random variable is described by another distribution.
 *
 * @author Scott Shaffer
 */
public abstract class LogDerivedDistribution extends AbstractRealDistribution {
    /**
     * Returns the distribution of the logarithm of the random
     * variable.
     *
     * @return the distribution of the logarithm of the random
     * variable.
     */
    protected abstract RealDistribution getLogDistribution();

    @Override public double cdf(double x) {
        return getLogDistribution().cdf(Math.log(x));
    }

    @Override public double median() {
	return Math.exp(getLogDistribution().median());
    }

    @Override public double pdf(double x) {
        return getLogDistribution().pdf(Math.log(x)) / x;
    }

    @Override public double quantile(double F) {
        return Math.exp(getLogDistribution().quantile(F));
    }

    @Override public double sample(JamRandom source) {
        return Math.exp(getLogDistribution().sample(source));
    }

    @Override public DoubleRange support() {
        return DoubleRange.POSITIVE;
    }
}
