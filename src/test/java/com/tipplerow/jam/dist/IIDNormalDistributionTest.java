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

import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.vector.JamVector;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class IIDNormalDistributionTest extends MultivariateDistributionTestBase {
    private static final int    NVAR =  3;
    private static final double MEAN = -1.0;
    private static final double SDEV =  0.5;

    private final JamVector expectedMean = 
        JamVector.wrap(MEAN, MEAN, MEAN);

    private final JamMatrix expectedCovar =
        JamMatrix.byrow(3, 3,
                        SDEV * SDEV, 0.0, 0.0,
                        0.0, SDEV * SDEV, 0.0,
                        0.0, 0.0, SDEV * SDEV);

    public IIDNormalDistributionTest() {
        super(new IIDNormalDistribution(NVAR, MEAN, SDEV));
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidDim() {
        new IIDNormalDistribution(0, 1.0, 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidStDev() {
        new IIDNormalDistribution(3, 0.0, 0.0);
    }

    public static void main(String[] args) {
    }
}
