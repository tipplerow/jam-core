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

import com.google.common.collect.Multiset;

import com.tipplerow.jam.lang.JamProperties;
import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents a univariate probability distribution taking integer
 * values.
 *
 * @author Scott Shaffer
 */
public interface DiscreteDistribution {
    /**
     * Creates a <em>nearly equivalent</em> pre-computed probability
     * distribution over the effective range of this distribution.
     *
     * <p>The CDF and PDF of the cached distribution may differ from
     * those in the true distribution by one part per billion.
     *
     * @return a new discrete distribution with the PDF and CDF
     * pre-computed over the effective range of this distribution.
     */
    default DiscreteDistribution cache() {
        return CompactDiscreteDistribution.cache(this);
    }

    /**
     * Computes the cumulative distribution function at a point.  For
     * a random variable {@code X} drawn from this distribution, this
     * method returns the probability {@code P(X <= k)}.
     *
     * @param k the point at which the CDF is evaluated.
     *
     * @return the probability that a random variable drawn from this
     * distribution is less than or equal to {@code k}.
     */
    double cdf(int k);

    /**
     * Computes the cumulative distribution function for a half-open
     * range.  For a random variable {@code X} drawn from this
     * distribution, this method returns {@code P(j < X <= k)}.
     *
     * @param j the greatest integer <em>not</em> in the sample range.
     *
     * @param k the greatest integer in the sample range.
     *
     * @return the probability that a random variable drawn from this
     * distribution lies in the half-open range {@code (j, k])}.
     *
     * @throws IllegalArgumentException if {@code j > k}.
     */
    double cdf(int j, int k);

    /**
     * Computes the cumulative distribution function over a closed
     * range.
     *
     * @param range the sample range.
     *
     * @return the probability that the specified range contains a
     * random variable drawn from this distribution.
     */
    default double cdf(IntRange range) {
        return cdf(range.lower() - 1, range.upper());
    }

    /**
     * Computes the probability mass function at a point.
     *
     * @param k the point at which the PDF is evaluated.
     *
     * @return the probability mass at {@code x}.
     */
    double pdf(int k);

    /**
     * Returns the mean value of this distribution.
     *
     * @return the mean value of this distribution.
     */
    double mean();

    /**
     * Returns the median value of this distribution.
     *
     * @return the median value of this distribution.
     */
    double median();

    /**
     * Returns the smallest integer range containing the median.
     *
     * @return the smallest integer range containing the median.
     */
    default IntRange medianRange() {
        double median = median();
        return IntRange.instance((int) Math.floor(median), (int) Math.ceil(median));
    }

    /**
     * Returns the standard deviation this distribution.
     *
     * @return the standard deviation of this distribution.
     */
    double sdev();

    /**
     * Returns the variance of this distribution.
     *
     * @return the variance of this distribution.
     */
    double variance();

    /**
     * Samples from this distribution using the globally shared random
     * number source.
     *
     * @return the next value from this distribution.
     */
    int sample();

    /**
     * Samples from this distribution using a specified random number
     * source.
     *
     * @param source the source of uniform random deviates.
     *
     * @return the next value from this distribution.
     */
    int sample(JamRandom source);

    /**
     * Samples from this distribution using the globally shared random
     * number source.
     *
     * @param count the number of samples to generate.
     *
     * @return the next {@code count} values from this distribution.
     */
    Multiset<Integer> sample(int count);

    /**
     * Samples from this distribution using a specified random number
     * source.
     *
     * @param source the source of uniform random deviates.
     *
     * @param count the number of samples to generate.
     *
     * @return the next {@code count} values from this distribution.
     */
    Multiset<Integer> sample(JamRandom source, int count);

    /**
     * Returns the single closed contiguous range of integers with
     * non-zero probability mass.
     *
     * @return the single closed contiguous range of integers with
     * non-zero probability mass.
     *
     * @throws IllegalStateException unless this distribution has a
     * single contiguous range of support.
     */
    IntRange support();

    /**
     * Returns the effective range of this distribution: a range that
     * omits at most one-billionth (1.0E-09) of the total probability
     * mass.
     *
     * @return the effective range of this distribution.
     */
    default IntRange effectiveRange() {
        //
        // The effective range for a normal distribution is seven
        // standard deviations around the mean, so we use this as
        // a default rule of thumb...
        //
        double mean = mean();
        double sdev = sdev();

        int lower = (int) Math.floor(mean - 7.0 * sdev);
        int upper = (int) Math.ceil( mean + 7.0 * sdev);

        lower = Math.max(lower, support().lower());
        upper = Math.min(upper, support().upper());
        
        return IntRange.instance(lower, upper);
    }

    /**
     * Summarizes this distribution in a string suitable for writing
     * to the console or a file.
     *
     * @param range the range over which to display this distribution.
     *
     * @return a summary description suitable for writing to the
     * console or a file.
     */
    default String display(IntRange range) {
        StringBuilder builder = new StringBuilder();

        builder.append(" k      pdf        cdf   \n");
        builder.append("---  ---------  ---------\n");

        for (int k = range.lower(); k <= range.upper(); k++)
            builder.append(String.format("%3d   %8.6f   %8.6f\n", k, pdf(k), cdf(k)));

        return builder.toString();
    }

    /**
     * Creates a {@code DiscreteDistribution} that has been specified by a
     * properly-encoded system property.
     *
     * @param propertyName the name of the system property containing
     * the string that defines the distribution.
     *
     * @return the {@code DiscreteDistribution} defined by the system
     * property with the given name.
     *
     * @throws IllegalArgumentException unless the system property
     * with the given name defines a valid probability distribution.
     */
    static DiscreteDistribution resolve(String propertyName) {
        return DiscreteDistributionType.parse(JamProperties.getRequired(propertyName));
    }

    /**
     * Sample a series of random deviates and write them to standard
     * error to facilitate analysis of their statistical properties,
     * say in R.
     *
     * @param args a single string argument defining the probability
     * distribution in the format: {@code TYPE; param1, param2, ...},
     * where {@code TYPE} is the enumerated type code and {@code
     * param1, param2, ...} are the comma-separated parameters
     * required to define a distribution of the specified type.
     *
     * @throws IllegalArgumentException unless the input string
     * defines a valid probability distribution.
     */
    static void main(String[] args) {
        final int SAMPLE_COUNT = 100000;

        if (args.length != 1) {
            System.err.println("Usage: jam.dist.DiscreteDistribution \"TYPE; param1, param2, ...\"");
            System.exit(1);
        }

        DiscreteDistribution distribution =
            DiscreteDistributionType.parse(args[0]);

        JamRandom source = JamRandom.generator();

        for (int index = 0; index < SAMPLE_COUNT; index++)
            System.err.println(distribution.sample(source));
    }
}
