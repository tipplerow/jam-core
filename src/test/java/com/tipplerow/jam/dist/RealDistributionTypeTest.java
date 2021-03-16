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

import com.tipplerow.jam.junit.NumericTestBase;

import org.junit.*;
import static org.junit.Assert.*;

public class RealDistributionTypeTest extends NumericTestBase {
    @Test public void testDiracDelta() {
        DiracDeltaDistribution dist1 = new DiracDeltaDistribution(1.0);
        DiracDeltaDistribution dist2 = (DiracDeltaDistribution) RealDistributionType.parse("DIRAC_DELTA; 1.0");

        assertDouble(dist1.getImpulse(), dist2.getImpulse());
    }

    @Test public void testLogNormal() {
        LogNormalDistribution dist1 = new LogNormalDistribution(-1.0, 3.0);
        LogNormalDistribution dist2 = (LogNormalDistribution) RealDistributionType.parse("LOG_NORMAL; -1.0, 3.0");

        assertDouble(dist1.getMeanLog(),  dist2.getMeanLog());
        assertDouble(dist1.getStDevLog(), dist2.getStDevLog());
    }

    @Test public void testLogUniform() {
        LogUniformDistribution dist1 = new LogUniformDistribution(-1.0, 3.0);
        LogUniformDistribution dist2 = (LogUniformDistribution) RealDistributionType.parse("LOG_UNIFORM; -1.0, 3.0");

        assertDouble(dist1.getLowerLog(), dist2.getLowerLog());
        assertDouble(dist1.getUpperLog(), dist2.getUpperLog());
    }

    @Test public void testNormal() {
        NormalDistribution dist1 = new NormalDistribution(-1.0, 3.0);
        NormalDistribution dist2 = (NormalDistribution) RealDistributionType.parse("NORMAL; -1.0, 3.0");

        assertDouble(dist1.mean(),  dist2.mean());
        assertDouble(dist1.stdev(), dist2.stdev());
    }

    @Test public void testUniform() {
        UniformRealDistribution dist1 = new UniformRealDistribution(-1.0, 3.0);
        UniformRealDistribution dist2 = (UniformRealDistribution) RealDistributionType.parse("UNIFORM; -1.0, 3.0");

        assertDouble(dist1.getLower(), dist2.getLower());
        assertDouble(dist1.getUpper(), dist2.getUpper());
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.RealDistributionTypeTest");
    }
}
