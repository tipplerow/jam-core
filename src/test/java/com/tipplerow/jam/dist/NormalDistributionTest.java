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

public class NormalDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-06;

    private final double mean  = -1.0;
    private final double stdev =  3.0;

    private final RealDistribution dist = 
        RealDistributionType.NORMAL.create(mean, stdev);

    public NormalDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.1586553, dist.cdf(-4.0));
        assertDouble(0.5,       dist.cdf(-1.0));
        assertDouble(0.9772499, dist.cdf( 5.0));
    }

    @Test public void testPDF() {
        assertDouble(0.08065691, dist.pdf(-4.0));
        assertDouble(0.13298076, dist.pdf(-1.0));
        assertDouble(0.01799699, dist.pdf( 5.0));
    }

    @Test public void testMoments() {
	momentTest(dist, 1000000, 0.001, 0.005, 0.005, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStDev() {
        new NormalDistribution(0.0, -1.0);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.NormalDistributionTest");
    }
}
