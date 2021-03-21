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
package com.tipplerow.jam.math;

import com.tipplerow.jam.testng.NumericTestBase;
import com.tipplerow.jam.vector.VectorView;

import org.testng.annotations.Test;

public class DistanceTest extends NumericTestBase {
    private final VectorView v1 = VectorView.of(1.0, 2.0, 3.0);
    private final VectorView v2 = VectorView.of(0.0, 4.0, 7.0);

    @Test public void testChebyshev() {
        assertDouble(0.0, Distance.CHEBYSHEV.compute(v1, v1));
        assertDouble(0.0, Distance.CHEBYSHEV.compute(v2, v2));

        assertDouble(4.0, Distance.CHEBYSHEV.compute(v1, v2));
        assertDouble(4.0, Distance.CHEBYSHEV.compute(v2, v1));
    }

    @Test public void testEuclidean() {
        assertDouble(0.0, Distance.EUCLIDEAN.compute(v1, v1));
        assertDouble(0.0, Distance.EUCLIDEAN.compute(v2, v2));

        assertDouble(Math.sqrt(21.0), Distance.EUCLIDEAN.compute(v1, v2));
        assertDouble(Math.sqrt(21.0), Distance.EUCLIDEAN.compute(v2, v1));
    }

    @Test public void testManhattan() {
        assertDouble(0.0, Distance.MANHATTAN.compute(v1, v1));
        assertDouble(0.0, Distance.MANHATTAN.compute(v2, v2));

        assertDouble(7.0, Distance.MANHATTAN.compute(v1, v2));
        assertDouble(7.0, Distance.MANHATTAN.compute(v2, v1));
    }
}
