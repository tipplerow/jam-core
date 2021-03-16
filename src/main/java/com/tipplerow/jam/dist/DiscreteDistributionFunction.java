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

import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents the cumulative probability distribution (CDF) or
 * probability density function (PDF) for a discrete probability
 * distribution.
 *
 * @author Scott Shaffer
 */
public abstract class DiscreteDistributionFunction {
    /**
     * The range of support for the distribution.
     */
    protected final IntRange support;

    /**
     * The values of the distribution function for each point in the
     * range of support, where {@code values.get(k)} contains the value
     * at {@code support.lower() + k}.
     */
    protected final VectorView values;

    /**
     * Creates a new distribution function.
     *
     * @param support the range of support for the distribution.
     *
     * @param values the values of the distribution function for each
     * point in the range of support; {@code values.get(k)} must contain
     * the value at location {@code support.lower() + k}.
     *
     * @throws IllegalArgumentException unless the number of values is
     * equal to the number of points in the range of support.
     */
    protected DiscreteDistributionFunction(IntRange support, VectorView values) {
        validate(support, values);

        this.support = support;
        this.values  = values;
    }

    private static void validate(IntRange support, VectorView values) {
        if (values.length() != support.size())
            throw new IllegalArgumentException("Values to not span the range of support.");
    }

    /**
     * Summarizes this distribution in a string suitable for writing
     * to the console or a file.
     *
     * @return a summary description suitable for writing to the
     * console or a file.
     */
    public String display() {
        StringBuilder builder = new StringBuilder();

        for (int k : support)
            builder.append(String.format("%6d => %8.6f\n", k, evaluate(k)));

        return builder.toString();
    }

    /**
     * Evaluates this distribution function at a given location.
     *
     * @param k the point at which to evaluate this distribution.
     *
     * @return the value of this distribution function at the
     * specified location.
     *
     * @throws IllegalArgumentException unless the point falls within
     * the range of support.
     */
    public double evaluate(int k) {
        if (!support.contains(k))
            throw new IllegalArgumentException("Evaluation point is outside the range of support.");

        return values.get(indexOf(k));
    }

    private int indexOf(int k) {
        return k - support.lower();
    }

    /**
     * Returns the range of support for this distribution function.
     *
     * @return the range of support for this distribution function.
     */
    public IntRange support() {
        return support;
    }

    /**
     * Compares this distribution function with another.
     *
     * @param that the other distribution function.
     *
     * @return {@code true} iff this distribution function has the
     * same support range as the input function and its values are
     * equal within the tolerance of the default DoubleComparator.
     */
    protected boolean equalsDistribution(DiscreteDistributionFunction that) {
        return this.support.equals(that.support) && this.values.equalsVector(that.values);
    }

    @Override public String toString() {
        return display();
    }
}
