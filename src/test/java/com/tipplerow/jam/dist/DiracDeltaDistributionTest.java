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

public class DiracDeltaDistributionTest extends RealDistributionTestBase {
    private static final double TOLERANCE = 1.0e-12;

    private final double impulse = 3.33;

    private final RealDistribution dist = 
        RealDistributionType.DIRAC_DELTA.create(impulse);

    public DiracDeltaDistributionTest() {
        super(TOLERANCE);
    }

    @Test public void testCDF() {
        assertDouble(0.0, dist.cdf(impulse - TOLERANCE));
        assertDouble(0.5, dist.cdf(impulse));
        assertDouble(1.0, dist.cdf(impulse + TOLERANCE));
    }

    @Test public void testPDF() {
        assertDouble(0.0, dist.pdf(impulse - TOLERANCE));
        assertDouble(0.0, dist.pdf(impulse + TOLERANCE));
    }

    @Test public void testMoments() {
	momentTest(dist, 10000, TOLERANCE, TOLERANCE, TOLERANCE, false);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.DiracDeltaDistributionTest");
    }
}
