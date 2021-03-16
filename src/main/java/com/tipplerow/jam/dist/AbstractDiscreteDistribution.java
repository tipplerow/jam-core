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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import com.tipplerow.jam.math.IntUtil;
import com.tipplerow.jam.math.JamRandom;
import com.tipplerow.jam.stat.StatSummary;

/**
 * Provides a skeleton implementation of the {@code DiscreteDistribution}
 * interface.
 *
 * @author Scott Shaffer
 */
public abstract class AbstractDiscreteDistribution implements DiscreteDistribution {
    // Summary derived from random sampling, created on demand...
    private StatSummary summary = null;

    // Number of samples used to compute the statistical summary...
    private static final int SAMPLE_COUNT = 1000000;

    @Override public double cdf(int k) {
        int lower = effectiveRange().lower();
        int upper = k;

        return cumulatePDF(lower, upper);
    }

    @Override public double cdf(int j, int k) {
        //
        // "j + 1", because the argument "j" is not included in the
        // CDF range...
        //
        int lower = j + 1;
        int upper = k;

        return cumulatePDF(lower, upper);
    }

    private double cumulatePDF(int lower, int upper) {
        double result = 0.0;

        for (int k = lower; k <= upper; ++k)
            result += pdf(k);

        return result;
    }

    @Override public double mean() {
        return sampleSummary().getMean();
    }

    @Override public double median() {
        return sampleSummary().getMedian();
    }

    @Override public double sdev() {
        return Math.sqrt(variance());
    }

    @Override public double variance() {
        return sampleSummary().getVariance();
    }

    private StatSummary sampleSummary() {
        if (summary == null)
            summary = StatSummary.compute(IntUtil.toDouble(sample(SAMPLE_COUNT)));

        return summary;
    }

    @Override public int sample() {
        return sample(JamRandom.global());
    }

    @Override public Multiset<Integer> sample(int count) {
        return sample(JamRandom.global(), count);
    }

    @Override public Multiset<Integer> sample(JamRandom source, int count) {
        Multiset<Integer> values = HashMultiset.create();

        for (int index = 0; index < count; index++)
            values.add(sample(source));

        return values;
    }
}
