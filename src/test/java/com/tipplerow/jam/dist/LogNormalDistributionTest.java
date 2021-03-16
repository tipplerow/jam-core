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

public class LogNormalDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-06;

    private final double meanLog  = -1.0;
    private final double stdevLog =  0.5;

    private final RealDistribution dist = 
        RealDistributionType.LOG_NORMAL.create(meanLog, stdevLog);

    public LogNormalDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.004591432, dist.cdf(0.1));
        assertDouble(0.730295069, dist.cdf(0.5));
        assertDouble(0.977249868, dist.cdf(1.0));
    }

    @Test public void testPDF() {
        assertDouble(0.2680285, dist.pdf(0.1));
        assertDouble(1.3218583, dist.pdf(0.5));
        assertDouble(0.1079819, dist.pdf(1.0));
    }

    @Test public void testMoments() {
	momentTest(dist, 1000000, 0.0002, 0.0002, 0.04, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStDev() {
        new LogNormalDistribution(0.0, -1.0);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.LogNormalDistributionTest");
    }
}
