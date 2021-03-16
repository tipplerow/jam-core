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

import com.tipplerow.jam.math.Factorial;
import com.tipplerow.jam.math.Probability;

/**
 * Represents the probability distribution that describes a sequence
 * of independent event trials.  Both the number of trials and the
 * probability that the event occurs on a single trial are fixed.
 *
 * <p>The method {@code pdf(k)} returns the probability that the event
 * occurs exactly {@code k} times (regardless of when).
 *
 * @author Scott Shaffer
 */
public final class OccurrenceDistribution extends CompactDiscreteDistribution {
    private final int trialCount;
    private final Probability eventProb;

    /**
     * Creates a new occurrence distribution.
     *
     * @param eventProb the probability that an event occurs on a
     * single trial.
     *
     * @param trialCount the number of trials.
     *
     * @throws IllegalArgumentException if the number of trials is
     * negative.
     */
    public OccurrenceDistribution(Probability eventProb, int trialCount) {
        super(computePDF(eventProb, trialCount));

        this.eventProb = eventProb;
        this.trialCount = trialCount;
    }

    private static DiscretePDF computePDF(Probability eventProb, int trialCount) {
        if (trialCount < 0)
            throw new IllegalArgumentException("The number of trials must be non-negative.");

        int N = trialCount;

        double logp   = Math.log(eventProb.doubleValue());
        double log1_p = Math.log(1.0 - eventProb.doubleValue());

        double[] values = new double[N + 1];

        for (int k = 0; k <= N; ++k)
            values[k] = Math.exp(k * logp + (N - k) * log1_p + Factorial.chooseLog(N, k));

        return DiscretePDF.create(0, values);
    }

    /**
     * Returns the probability that the event occurs <em>exactly</em>
     * {@code k} times across all trials.
     *
     * @param k the number of occurrences to test.
     *
     * @return the probability that the event occurs <em>exactly</em>
     * {@code k} times.
     */
    public double atLeast(int k) {
        return 1.0 - atMost(k - 1);
    }

    /**
     * Returns the probability that the event occurs {@code k} times
     * <em>or fewer</em> across all trials.
     *
     * @param k the number of occurrences to test.
     *
     * @return the probability that the event occurs {@code k} times
     * <em>or fewer</em>.
     */
    public double atMost(int k) {
        return cdf(k);
    }

    /**
     * Returns the probability that the event occurs <em>exactly</em>
     * {@code k} times across all trials.
     *
     * @param k the number of occurrences to test.
     *
     * @return the probability that the event occurs <em>exactly</em>
     * {@code k} times.
     */
    public double exactly(int k) {
        return pdf(k);
    }

    /**
     * Returns the probability that an event occurs on a single trial.
     *
     * @return the probability that an event occurs on a single trial.
     */
    public Probability getEventProb() {
        return eventProb;
    }

    /**
     * Returns the number of trials.
     *
     * @return the number of trials.
     */
    public int getTrialCount() {
        return trialCount;
    }
}
