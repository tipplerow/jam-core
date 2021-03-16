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

import org.junit.*;
import static org.junit.Assert.*;

public class ExponentialDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-06;

    private final double rate1 = 0.60;
    private final double rate2 = 1.20;

    private final RealDistribution dist1 = 
        RealDistributionType.EXPONENTIAL.create(rate1);

    private final RealDistribution dist2 = 
        RealDistributionType.EXPONENTIAL.create(rate2);

    public ExponentialDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.259182, dist1.cdf(0.5));
        assertDouble(0.451188, dist1.cdf(1.0));
        assertDouble(0.698806, dist1.cdf(2.0));

        assertDouble(0.451188, dist2.cdf(0.5));
        assertDouble(0.698806, dist2.cdf(1.0));
        assertDouble(0.909282, dist2.cdf(2.0));
    }

    @Test public void testPDF() {
        assertDouble(0.444491, dist1.pdf(0.5));
        assertDouble(0.329287, dist1.pdf(1.0));
        assertDouble(0.180717, dist1.pdf(2.0));

        assertDouble(0.658574, dist2.pdf(0.5));
        assertDouble(0.361433, dist2.pdf(1.0));
        assertDouble(0.108862, dist2.pdf(2.0));
    }

    @Test public void testQuantile() {
        assertDouble(0.479470, dist1.quantile(0.25));
        assertDouble(1.155245, dist1.quantile(0.50));
        assertDouble(2.310491, dist1.quantile(0.75));

        assertDouble(0.239735, dist2.quantile(0.25));
        assertDouble(0.577623, dist2.quantile(0.50));
        assertDouble(1.155245, dist2.quantile(0.75));
    }

    @Test public void testMoments() {
	momentTest(dist1, 1000000, 0.001, 0.005, 0.005, false);
	momentTest(dist2, 1000000, 0.001, 0.005, 0.005, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroRate() {
        new ExponentialDistribution(0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeRate() {
        new ExponentialDistribution(-1.0);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.ExponentialDistributionTest");
    }
}
