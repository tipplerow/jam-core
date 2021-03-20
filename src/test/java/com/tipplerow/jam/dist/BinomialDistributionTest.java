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

import org.apache.commons.math3.random.Well44497b;

import com.tipplerow.jam.math.Probability;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class BinomialDistributionTest extends DiscreteDistributionTestBase {
    private static final double TOLERANCE = 1.0e-12;
    private static final Well44497b RNG = new Well44497b(20171114);

    private final com.tipplerow.jam.dist.BinomialDistribution jam0 = jam(0, 0.5);

    private final com.tipplerow.jam.dist.BinomialDistribution jam_01_20 = jam(1, 0.2);
    private final com.tipplerow.jam.dist.BinomialDistribution jam_01_60 = jam(1, 0.6);

    private final com.tipplerow.jam.dist.BinomialDistribution jam_05_10 = jam(5, 0.10);
    private final com.tipplerow.jam.dist.BinomialDistribution jam_05_50 = jam(5, 0.50);

    private final com.tipplerow.jam.dist.BinomialDistribution jam_30_50 = jam(30, 0.50);
    private final com.tipplerow.jam.dist.BinomialDistribution jam_30_90 = jam(30, 0.90);

    private final com.tipplerow.jam.dist.BinomialDistribution jam_95_25 = jam(95, 0.25);
    private final com.tipplerow.jam.dist.BinomialDistribution jam_95_60 = jam(95, 0.60);

    private final org.apache.commons.math3.distribution.BinomialDistribution apache0 = apache(0, 0.5);

    private final org.apache.commons.math3.distribution.BinomialDistribution apache_01_20 = apache(1, 0.20);
    private final org.apache.commons.math3.distribution.BinomialDistribution apache_01_60 = apache(1, 0.60);

    private final org.apache.commons.math3.distribution.BinomialDistribution apache_05_10 = apache(5, 0.10);
    private final org.apache.commons.math3.distribution.BinomialDistribution apache_05_50 = apache(5, 0.50);

    private final org.apache.commons.math3.distribution.BinomialDistribution apache_30_50 = apache(30, 0.50);
    private final org.apache.commons.math3.distribution.BinomialDistribution apache_30_90 = apache(30, 0.90);

    private final org.apache.commons.math3.distribution.BinomialDistribution apache_95_25 = apache(95, 0.25);
    private final org.apache.commons.math3.distribution.BinomialDistribution apache_95_60 = apache(95, 0.60);

    private static com.tipplerow.jam.dist.BinomialDistribution jam(int count, double prob) {
        return com.tipplerow.jam.dist.BinomialDistribution.create(count, Probability.of(prob));
    }

    private static org.apache.commons.math3.distribution.BinomialDistribution apache(int count, double prob) {
        return new org.apache.commons.math3.distribution.BinomialDistribution(RNG, count, prob);
    }

    public BinomialDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testImplementation() {
        assertEquals("BinomialDistributionExact", jam0.getClass().getSimpleName());

        assertEquals("BinomialDistributionExact", jam_01_20.getClass().getSimpleName());
        assertEquals("BinomialDistributionExact", jam_01_60.getClass().getSimpleName());

        assertEquals("BinomialDistributionExact", jam_05_10.getClass().getSimpleName());
        assertEquals("BinomialDistributionExact", jam_05_50.getClass().getSimpleName());

        assertEquals("BinomialDistributionApprox", jam_30_50.getClass().getSimpleName());
        assertEquals("BinomialDistributionExact",  jam_30_90.getClass().getSimpleName());

        assertEquals("BinomialDistributionApprox", jam_95_25.getClass().getSimpleName());
        assertEquals("BinomialDistributionApprox", jam_95_60.getClass().getSimpleName());
    }

    @Test public void testCDF() {
        compareCDF(jam0, apache0);

        compareCDF(jam_01_20, apache_01_20);
        compareCDF(jam_01_60, apache_01_60);

        compareCDF(jam_05_10, apache_05_10);
        compareCDF(jam_05_50, apache_05_50);

        compareCDF(jam_30_50, apache_30_50);
        compareCDF(jam_30_90, apache_30_90);

        compareCDF(jam_95_25, apache_95_25);
        compareCDF(jam_95_60, apache_95_60);
    }

    private void compareCDF(com.tipplerow.jam.dist.BinomialDistribution jam,
                            org.apache.commons.math3.distribution.BinomialDistribution apache) {
        for (Integer k : jam.effectiveRange())
            assertEquals(jam.cdf(k), apache.cumulativeProbability(k), 1.0E-06);
    }

    @Test public void testPDF() {
        comparePDF(jam0, apache0);

        comparePDF(jam_01_20, apache_01_20);
        comparePDF(jam_01_60, apache_01_60);

        comparePDF(jam_05_10, apache_05_10);
        comparePDF(jam_05_50, apache_05_50);

        comparePDF(jam_30_50, apache_30_50);
        comparePDF(jam_30_90, apache_30_90);

        comparePDF(jam_95_25, apache_95_25);
        comparePDF(jam_95_60, apache_95_60);
    }

    private void comparePDF(com.tipplerow.jam.dist.BinomialDistribution jam,
                            org.apache.commons.math3.distribution.BinomialDistribution apache) {
        for (Integer k : jam.effectiveRange())
            assertEquals(jam.pdf(k), apache.probability(k), 1.0E-06);
    }

    @Test public void testCache() {
        cacheTest(jam_01_20, 1.0e-12, false);
        cacheTest(jam_01_60, 1.0e-12, false);
        cacheTest(jam_05_10, 1.0e-12, false);
        cacheTest(jam_05_50, 1.0e-12, false);
        cacheTest(jam_30_50, 1.0e-12, false);
        cacheTest(jam_30_90, 1.0e-12, false);
    }

    @Test public void testEffectiveRange() {
        checkEffectiveRange(0);
        checkEffectiveRange(1);
        checkEffectiveRange(10);
        checkEffectiveRange(100);
        checkEffectiveRange(1000);
    }

    private void checkEffectiveRange(int trialCount) {
        effectiveRangeTest(jam(trialCount, 0.02), 1.0e-09, false);
        effectiveRangeTest(jam(trialCount, 0.20), 1.0e-09, false);
        effectiveRangeTest(jam(trialCount, 0.50), 1.0e-09, false);
        effectiveRangeTest(jam(trialCount, 0.80), 1.0e-09, false);
        effectiveRangeTest(jam(trialCount, 0.98), 1.0e-09, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidTrialCount() {
        com.tipplerow.jam.dist.BinomialDistribution.create(-1, Probability.of(0.5));
    }

    @Test public void testMoments() {
	momentTest(jam0, 100, 1.0E-12, 1.0E-12, false);

        momentTest(jam_01_20, 100000, 0.001, 0.001, false);
        momentTest(jam_01_60, 100000, 0.005, 0.001, false);

        momentTest(jam_05_10, 100000, 0.001, 0.002, false);
        momentTest(jam_05_50, 100000, 0.05,  0.002, false);

        momentTest(jam_30_50, 100000, 0.01,  0.2,   false);
        momentTest(jam_30_90, 100000, 0.005, 0.005, false);

        momentTest(jam_95_25, 100000, 0.01, 0.1,  false);
        momentTest(jam_95_60, 100000, 0.01, 0.05, false);
    }

    @Test public void testParse() {
        com.tipplerow.jam.dist.BinomialDistribution dist =
            (com.tipplerow.jam.dist.BinomialDistribution) DiscreteDistributionType.parse("BINOMIAL; 5, 0.2");

        assertTrue(dist instanceof com.tipplerow.jam.dist.BinomialDistribution);
        assertEquals(5, dist.getTrialCount());
        assertDouble(0.2, dist.getSuccessProb().doubleValue());
    }

    @Test public void testSamples() {
        sampleTest(1000, 0.0001, 0.0001, jam0, apache0);

        sampleTest(100000, 0.005, 0.005, jam_01_20, apache_01_20);
        sampleTest(100000, 0.005, 0.005, jam_01_60, apache_01_60);

        sampleTest(100000, 0.05, 0.05, jam_05_10, apache_05_10);
        sampleTest(100000, 0.05, 0.05, jam_05_50, apache_05_50);

        sampleTest(100000, 0.2, 0.2, jam_30_50, apache_30_50);
        sampleTest(100000, 0.2, 0.2, jam_30_90, apache_30_90);

        sampleTest(100000, 0.5, 0.5, jam_95_25, apache_95_25);
        sampleTest(100000, 0.5, 0.5, jam_95_60, apache_95_60);
    }
}
