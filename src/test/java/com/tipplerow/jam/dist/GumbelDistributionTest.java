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

public class GumbelDistributionTest extends RealDistributionTestBase {

    @Test public void testMoments() {
	GumbelDistribution dist1 = new GumbelDistribution(-10.0, 3.0);
	GumbelDistribution dist2 = new GumbelDistribution(2.0, 0.333);

	momentTest(dist1, 1000000, 0.01, 0.01, 0.05, false);
	momentTest(dist2, 1000000, 0.001, 0.001, 0.001, false);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("jam.dist.GumbelDistributionTest");
    }
}
