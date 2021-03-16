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

import java.util.Collection;
import com.google.common.collect.Multiset;
import com.tipplerow.jam.math.IntUtil;

/**
 * Represents a univariate probability distribution taking integer
 * values derived from an empirical data set.
 *
 * @author Scott Shaffer
 */
public final class EmpiricalDiscreteDistribution extends CompactDiscreteDistribution {
    private final int nob;

    private EmpiricalDiscreteDistribution(int nob, DiscretePDF pdf) {
        super(pdf);
        this.nob = nob;
    }

    /**
     * Creates a new discrete distribution describing a set of
     * empirical observations.
     *
     * @param observations the observations to describe.
     *
     * @return a discrete distribution describing the given data.
     */
    public static EmpiricalDiscreteDistribution compute(Collection<Integer> observations) {
        return compute(IntUtil.count(observations));
    }

    /**
     * Creates a new discrete distribution describing a set of
     * empirical observations.
     *
     * @param observations the observations to describe.
     *
     * @return a discrete distribution describing the given data.
     */
    public static EmpiricalDiscreteDistribution compute(int... observations) {
        return compute(IntUtil.count(observations));
    }

    /**
     * Creates a new discrete distribution describing a set of
     * empirical observations.
     *
     * @param counts the observations to describe, grouped in a bag
     * (multiset).
     *
     * @return the discrete distribution describing the given data.
     */
    public static EmpiricalDiscreteDistribution compute(Multiset<Integer> counts) {
        return new EmpiricalDiscreteDistribution(counts.size(), DiscretePDF.compute(counts));
    }

    /**
     * Returns the number of observations used to estimate this
     * distribution.
     *
     * @return the number of observations used to estimate this
     * distribution.
     */
    public int countObservations() {
        return nob;
    }

    /**
     * Returns the standard error around the mean value.
     *
     * @return the standard error around the mean value.
     */
    public double stderr() {
        //
        // Assume independent observations...
        //
        return sdev() / Math.sqrt(nob);
    }
}
