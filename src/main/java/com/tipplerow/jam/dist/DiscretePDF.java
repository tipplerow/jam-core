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
import java.util.Collections;

import com.google.common.collect.Multiset;

import com.tipplerow.jam.math.DoubleUtil;
import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.IntUtil;
import com.tipplerow.jam.math.Probability;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents the probability density function (PDF) for a discrete
 * probability distribution.
 *
 * @author Scott Shaffer
 */
public final class DiscretePDF extends DiscreteDistributionFunction {
    private DiscretePDF(IntRange support, VectorView values) {
        super(support, values);
        Probability.validate(values);
    }

    /**
     * Computes the PDF and CDF of another discrete distribution over
     * its effective range and returns a new cached distribution that
     * is <em>nearly equivalent</em> to the input distribution.
     *
     * <p>The new distribution will differ from the input distribution
     * by less than one part per billion.
     *
     * @param dist the distribution to cache.
     *
     * @return a new distribution with the PDF and CDF pre-computed
     * over the effective range of the input distribution.
     */
    public static DiscretePDF cache(DiscreteDistribution dist) {
        IntRange  range = dist.effectiveRange();
        JamVector PDF   = JamVector.dense(range.size());

        for (int k = 0; k < PDF.length(); ++k)
            PDF.set(k, dist.pdf(range.lower() + k));

        PDF.normalize();
        return create(range.lower(), PDF);
    }

    /**
     * Creates a new discrete distribution function with fixed values.
     *
     * @param lower the lower bound of the range of support.
     *
     * @param PDF the fixed probability density values, where {@code
     * PDF[k]} is the probability for observation {@code lower + k}.
     *
     * @return the discrete distribution function.
     */
    public static DiscretePDF create(int lower, double[] PDF) {
        return create(lower, VectorView.of(PDF));
    }

    /**
     * Creates a new discrete distribution function with fixed values.
     *
     * @param lower the lower bound of the range of support.
     *
     * @param PDF the fixed probability density values, where 
     * {@code PDF.getDouble(k)} is the probability for observation 
     * {@code lower + k}.
     *
     * @return the discrete distribution function.
     */
    public static DiscretePDF create(int lower, VectorView PDF) {
        IntRange support = IntRange.instance(lower, lower + PDF.length() - 1);
        JamVector values = JamVector.copyOf(PDF); // Defensive copy

        return new DiscretePDF(support, values);
    }

    /**
     * Computes the discrete probability density function describing a
     * set of empirical observations.
     *
     * @param observations the observations to describe.
     *
     * @return the discrete probability density function describing
     * the given data.
     */
    public static DiscretePDF compute(Collection<Integer> observations) {
        return compute(IntUtil.count(observations));
    }

    /**
     * Computes the discrete probability density function describing a
     * set of empirical observations.
     *
     * @param observations the observations to describe.
     *
     * @return the discrete probability density function describing
     * the given data.
     */
    public static DiscretePDF compute(int... observations) {
        return compute(IntUtil.count(observations));
    }

    /**
     * Computes the discrete probability density function describing a
     * set of empirical observations.
     *
     * @param counts the observations to describe, grouped in a bag
     * (multiset).
     *
     * @return the discrete probability density function describing
     * the given data.
     *
     * @throws IllegalArgumentException if there are no observations.
     */
    public static DiscretePDF compute(Multiset<Integer> counts) {
        if (counts.isEmpty())
            throw new IllegalArgumentException("At least one observation is required.");

        int lower = Collections.min(counts.elementSet());
        int upper = Collections.max(counts.elementSet());

        IntRange support = IntRange.instance(lower, upper);
        JamVector values = JamVector.dense(support.size());

        for (int observation : support)
            values.set(observation - lower, DoubleUtil.ratio(counts.count(observation), counts.size()));

        return new DiscretePDF(support, values);
    }

    /**
     * Returns the CDF corresponding to this distribution.
     *
     * @return the CDF corresponding to this distribution.
     */
    public DiscreteCDF cdf() {
        return DiscreteCDF.compute(this);
    }

    /**
     * Evaluates this distribution function at a given location.
     *
     * @param k the point at which to evaluate this distribution.
     *
     * @return the value of this distribution function at the
     * specified location.
     */
    public double evaluate(int k) {
        if (support().contains(k))
            return super.evaluate(k);
        else
            return 0.0;
    }

    /**
     * Returns the mean value for this probability density.
     *
     * @return the mean value for this probability density.
     */
    public double mean() {
        double result = 0.0;

        for (int k : support())
            result += evaluate(k) * k;

        return result;
    }

    /**
     * Returns the variance for this probability density.
     *
     * @return the variance for this probability density.
     */
    public double variance() {
        double mean = mean();
        double result = 0.0;

        for (int k : support())
            result += evaluate(k) * DoubleUtil.square(k - mean);

        return result;
    }

    @Override public boolean equals(Object that) {
        return (that instanceof DiscretePDF) && equalsDistribution((DiscretePDF) that);
    }
}
