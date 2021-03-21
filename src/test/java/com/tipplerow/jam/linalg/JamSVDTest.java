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
package com.tipplerow.jam.linalg;

import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.testng.NumericTestBase;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author Scott Shaffer
 */
public final class JamSVDTest extends NumericTestBase {
    @Test
    public void testInverse() {
        JamMatrix mat1 = randomMatrix(10, 10);
        JamMatrix mat2 = randomMatrix(10, 20);
        JamMatrix mat3 = randomMatrix(20, 10);

        JamMatrix inv1 = JamSVD.compute(mat1).invert();
        JamMatrix inv2 = JamSVD.compute(mat2).invert();
        JamMatrix inv3 = JamSVD.compute(mat3).invert();

        JamMatrix I10 = JamMatrix.identity(10);

        assertEquals(mat1.times(inv1), I10);
        assertEquals(mat2.times(inv2), I10);
        assertEquals(inv3.times(mat3), I10);
    }
}
