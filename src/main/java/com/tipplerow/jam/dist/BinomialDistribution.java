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

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.math.DoubleUtil;
import com.tipplerow.jam.math.Factorial;
import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.Probability;

/**
 * Implements the binomial distribution describing the number of
 * successful trials in a sequence of {@code N} independent trials
 * where each trial has probability {@code p} of success.
 *
 * @author Scott Shaffer
 */
public abstract class BinomialDistribution extends AbstractDiscreteDistribution {
    private final int trialCount;
    private final Probability successProb;

    /**
     * Creates a binomial distribution.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @throws IllegalArgumentException if the trial count is
     * negative.
     */
    protected BinomialDistribution(int trialCount, Probability successProb) {
        validateTrialCount(trialCount);

        this.trialCount  = trialCount;
        this.successProb = successProb;
    }

    private static void validateTrialCount(int trialCount) {
        if (trialCount < 0)
            throw new IllegalArgumentException("Trial count cannot be negative.");
    }

    /**
     * Creates a binomial distribution.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @return the new binomial distribution.
     *
     * @throws IllegalArgumentException if the trial count is
     * negative.
     */
    public static BinomialDistribution create(int trialCount, Probability successProb) {
        if (useApprox(trialCount, successProb))
            return new BinomialDistributionApprox(trialCount, successProb);
        else
            return new BinomialDistributionExact(trialCount, successProb);
    }

    private static boolean useApprox(int trialCount, Probability successProb) {
        //
        // Always use the exact implementation for fewer than ten
        // trials...
        //
        if (trialCount < 10)
            return false;

        // The normal approximation is sufficiently accurate if the
        // entire range of support [0, N] lies within four standard
        // deviations of the mean...
        double mean  = mean(trialCount, successProb);
        double stdev = Math.sqrt(variance(trialCount, successProb));

        double lower = mean - 4.0 * stdev;
        double upper = mean + 4.0 * stdev;

        IntRange range = support(trialCount);
        return range.containsDouble(lower) && range.containsDouble(upper);
    }

    /**
     * Computes the mean of a binomial distribution.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @return the mean of the binomial distribution with the given
     * trial count and success probability.
     *
     * @throws IllegalArgumentException if the trial count is
     * negative.
     */
    public static double mean(int trialCount, Probability successProb) {
        validateTrialCount(trialCount);
        return trialCount * successProb.doubleValue();
    }

    /**
     * Computes the variance of a binomial distribution.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @return the variance of the binomial distribution with the given
     * trial count and success probability.
     *
     * @throws IllegalArgumentException if the trial count is
     * negative.
     */
    public static double variance(int trialCount, Probability successProb) {
        validateTrialCount(trialCount);
        return trialCount * successProb.doubleValue() * (1.0 - successProb.doubleValue());
    }

    /**
     * Computes the probability mass function for a binomial
     * distribution.
     *
     * @param k the point at which to evaluate the probability.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @return the probability mass function for the binomial
     * distribution with the given trial count and success
     * probability.
     *
     * @throws IllegalArgumentException if the trial count is
     * negative.
     */
    public static double pdf(int k, int trialCount, Probability successProb) {
        if (k < 0 || k > trialCount)
            return 0.0;
        else
            return Math.exp(logPDF(k, trialCount, successProb));
    }

    /**
     * Computes the <em>logarithm</em> of the probability mass
     * function for a binomial distribution.
     *
     * @param k the point at which to evaluate the log-probability.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @param successProb the probability of success in a single
     * trial.
     *
     * @return the <em>logarithm</em> of the probability mass function
     * for the binomial distribution with the given trial count and
     * success probability.
     */
    public static double logPDF(int k, int trialCount, Probability successProb) {
        validateTrialCount(trialCount);

        int N = trialCount;

        double logp   = Math.log(successProb.doubleValue());
        double log1_p = Math.log(1.0 - successProb.doubleValue());

        if (k < 0 || k > trialCount)
            return Double.NEGATIVE_INFINITY;
        else
            return k * logp + (N - k) * log1_p + Factorial.chooseLog(N, k);
    }

    /**
     * Returns the range of support for a binomial distribution.
     *
     * @param trialCount the number of trials for the distribution.
     *
     * @return the range of support for the binomial distribution with
     * the specified trial count.
     */
    public static IntRange support(int trialCount) {
        return IntRange.instance(0, trialCount);
    }

    /**
     * Returns the number of trials for this distribution.
     *
     * @return the number of trials for this distribution.
     */
    public final int getTrialCount() {
        return trialCount;
    }

    /**
     * Returns the probability of success in a single trial.
     *
     * @return the probability of success in a single trial.
     */
    public final Probability getSuccessProb() {
        return successProb;
    }

    @Override public IntRange effectiveRange() {
        double mean  = mean();
        double stdev = stdev();
        double width = 12.0;

        int lower = Math.max(0,          (int) Math.floor(mean - width * stdev));
        int upper = Math.min(trialCount, (int) Math.ceil( mean + width * stdev));

        return IntRange.instance(lower, upper);
    }

    @Override public double pdf(int k) {
        return pdf(k, trialCount, successProb);
    }

    @Override public double mean() {
	return mean(trialCount, successProb);
    }

    @Override public double median() {
        double p = successProb.doubleValue();
        double mean = mean();

        if (DoubleComparator.DEFAULT.isInteger(mean))
            return mean;

        if (DoubleComparator.DEFAULT.EQ(p, 0.5))
            return mean;

        if (p <= 1.0 - DoubleUtil.LOG2 || p > DoubleUtil.LOG2)
            return Math.round(mean);

        return 0.5 * (Math.floor(mean) + Math.ceil(mean));
    }

    @Override public IntRange medianRange() {
        return IntRange.instance((int) Math.floor(mean()), (int) Math.ceil(mean()));
    }

    @Override public double variance() {
	return variance(trialCount, successProb);
    }

    @Override public IntRange support() {
        return support(trialCount);
    }
}
