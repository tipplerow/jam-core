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
package com.tipplerow.jam.vector;

import com.tipplerow.jam.testng.NumericTestBase;

import static org.testng.Assert.*;

/**
 * @author Scott Shaffer
 */
public class VectorTestBase extends NumericTestBase {
    public static final double[] array1 =
            new double[] { 0.0, 1.0, 2.0, Double.NaN, 4.0, Double.POSITIVE_INFINITY };

    public static final VectorView arrayView1 = VectorView.of(array1);

    public void assertVector(double[] actual, double... expected) {
        assertTrue(VectorView.of(actual).equalsVector(VectorView.of(expected)));
    }

    public void assertVector(VectorView actual, double... expected) {
        assertTrue(actual.equalsVector(VectorView.of(expected)));
    }
}
