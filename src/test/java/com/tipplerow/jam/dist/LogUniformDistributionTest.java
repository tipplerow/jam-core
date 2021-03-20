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

import com.tipplerow.jam.stat.StatSummary;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LogUniformDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-06;

    private final double lowerLog = -3.0;
    private final double upperLog =  3.0;

    private final RealDistribution dist = 
        RealDistributionType.LOG_UNIFORM.create(lowerLog, upperLog);

    public LogUniformDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.0,      dist.cdf(  0.01));
        assertDouble(0.116236, dist.cdf(  0.1));
        assertDouble(0.5,      dist.cdf(  1.0));
        assertDouble(0.883764, dist.cdf( 10.0));
        assertDouble(1.0,      dist.cdf(100.0));
    }

    @Test public void testPDF() {
        assertDouble(0.0,      dist.pdf(  0.01));
        assertDouble(1.666667, dist.pdf(  0.1));
        assertDouble(0.166667, dist.pdf(  1.0));
        assertDouble(0.016667, dist.pdf( 10.0));
        assertDouble(0.0,      dist.pdf(100.0));
    }

    @Test public void testSample() {
        StatSummary summary = StatSummary.compute(dist.sample(random(), 1000000));

        assertEquals(3.339292, summary.getMean(), 0.05);
        assertEquals(4.740041, summary.getSdev(), 0.05);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.LogUniformDistributionTest");
    }
}
