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

public class EmpiricalDiscreteDistributionTest extends DiscreteDistributionTestBase {
    private final EmpiricalDiscreteDistribution dist =
        EmpiricalDiscreteDistribution.compute(1, 2, 3, 5, 2, 3, 5, 3, 5, 5);

    @Test public void testCDF() {
        assertDouble(0.0, dist.cdf(0));
        assertDouble(0.1, dist.cdf(1));
        assertDouble(0.3, dist.cdf(2));
        assertDouble(0.6, dist.cdf(3));
        assertDouble(0.6, dist.cdf(4));
        assertDouble(1.0, dist.cdf(5));
        assertDouble(1.0, dist.cdf(6));
    }

    @Test public void testPDF() {
        assertDouble(0.0, dist.pdf(0));
        assertDouble(0.1, dist.pdf(1));
        assertDouble(0.2, dist.pdf(2));
        assertDouble(0.3, dist.pdf(3));
        assertDouble(0.0, dist.pdf(4));
        assertDouble(0.4, dist.pdf(5));
        assertDouble(0.0, dist.pdf(6));
    }

    @Test public void testObservationCount() {
        assertEquals(10, dist.countObservations());
    }

    @Test public void testMean() {
        assertDouble(3.4, dist.mean());
    }

    @Test public void testStErr() {
        assertEquals(0.451664, dist.stderr(), 0.000001);
    }

    @Test public void testVariance() {
        assertDouble(2.04, dist.variance());
    }

    @Test public void testSample() {
        momentTest(dist, 10000, 0.01, 0.01, false);
    }

    @Test public void testSupport() {
        assertEquals(1, dist.support().lower());
        assertEquals(5, dist.support().upper());
    }
}
