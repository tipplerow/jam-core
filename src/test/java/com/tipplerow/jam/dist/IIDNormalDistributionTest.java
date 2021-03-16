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

import com.tipplerow.jam.math.VectorMoment;
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.vector.JamVector;

import org.junit.*;
import static org.junit.Assert.*;

public class IIDNormalDistributionTest extends MultivariateDistributionTestBase {
    private static final int    NVAR  =  3;
    private static final double MEAN  = -1.0;
    private static final double STDEV =  0.5;

    private final JamVector expectedMean = 
        JamVector.valueOf(MEAN, MEAN, MEAN);

    private final JamMatrix expectedCovar =
        JamMatrix.byrow(3, 3,
                        STDEV * STDEV, 0.0, 0.0,
                        0.0, STDEV * STDEV, 0.0,
                        0.0, 0.0, STDEV * STDEV);

    public IIDNormalDistributionTest() {
        super(new IIDNormalDistribution(NVAR, MEAN, STDEV));
    }

    @Test public void testDim() {
        assertEquals(NVAR, dist.dim());
    }

    @Test public void testMean() {
        assertEquals(expectedMean, dist.mean());
    }

    @Test public void testCovar() {
        assertEquals(expectedCovar, dist.covar());
    }

    @Test public void testMoments() {
        momentTest(0.001, 0.01, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDim() {
        new IIDNormalDistribution(0, 1.0, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidStDev() {
        new IIDNormalDistribution(3, 0.0, 0.0);
    }

    public static void main(String[] args) {
    }
}
