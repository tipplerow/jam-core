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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import com.tipplerow.jam.lattice.Coord;
import com.tipplerow.jam.math.StatUtil;
import com.tipplerow.jam.vector.JamVector;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class HypersphericalDistributionTest extends MultivariateDistributionTestBase {
    private static final double RADIUS = 2.0;
    private static final JamVector CENTER = JamVector.copyOf(-1.0, 0.0, 2.0);

    public HypersphericalDistributionTest() {
        super(new HypersphericalDistribution(RADIUS, CENTER));
    }

    @Test public void testMoments() {
        momentTest(0.01, 0.01, false);
    }

    @Test public void testRadius() {
        for (int sample = 0; sample < samples.length; sample++)
            assertDouble(RADIUS, StatUtil.norm2(samples[sample].minus(CENTER)));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidDimensionality() {
        new HypersphericalDistribution(1, 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidRadius() {
        new HypersphericalDistribution(3, 0.0);
    }

    @Test public void testExpansionDistribution() {
        double    radius = 0.5 * Math.sqrt(3.0);
        JamVector center = JamVector.copyOf(0.0, 0.0, 0.0);
        
        HypersphericalDistribution dist =
            new HypersphericalDistribution(radius, center);

        Multiset<Coord> coords = HashMultiset.create();

        for (int k = 0; k < 1000000; ++k)
            coords.add(Coord.nearest(dist.sample(random())));

        int[] counts = new int[4];

        for (Coord coord : coords.elementSet())
            counts[coord.getSquaredLength()] += coords.count(coord);

        assertEquals(731089, counts[1]);
        assertEquals(268911, counts[2]);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.HypersphericalDistributionTest");
    }
}
