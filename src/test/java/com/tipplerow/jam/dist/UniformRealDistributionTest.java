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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class UniformRealDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-12;

    private final double lower = -1.0;
    private final double upper =  3.0;

    private final RealDistribution dist = 
        RealDistributionType.UNIFORM.create(lower, upper);

    public UniformRealDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.0,  dist.cdf(-2.0));
        assertDouble(0.0,  dist.cdf(-1.0));
        assertDouble(0.25, dist.cdf( 0.0));
        assertDouble(0.50, dist.cdf( 1.0));
        assertDouble(0.75, dist.cdf( 2.0));
        assertDouble(1.0,  dist.cdf( 3.0));
        assertDouble(1.0,  dist.cdf( 4.0));

        assertDouble(0.0,   dist.cdf(-0.5, -0.5));
        assertDouble(0.125, dist.cdf(-0.5,  0.0));
        assertDouble(0.25,  dist.cdf(-0.5,  0.5));
    }

    @Test public void testPDF() {
        assertDouble(0.0, dist.pdf(lower - TOLERANCE));
        assertDouble(0.0, dist.pdf(upper + TOLERANCE));

        assertDouble(0.25, dist.pdf(lower + TOLERANCE));
        assertDouble(0.25, dist.pdf(0.0));
        assertDouble(0.25, dist.pdf(1.0));
        assertDouble(0.25, dist.pdf(upper - TOLERANCE));
    }

    @Test public void testMoments() {
	momentTest(dist, 2000000, 0.0001, 0.002, 0.002, false);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidRange() {
        new UniformRealDistribution(0.0, -1.0);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.UniformRealDistributionTest");
    }
}
