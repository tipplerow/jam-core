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
package com.tipplerow.jam.matrix;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author Scott Shaffer
 */
public class JamMatrixTest extends MatrixTestBase {
    @Test
    public void testCopyOf() {
        double[][] array = new double[][] {
                {  0.0,  1.0,  2.0 },
                { 10.0, 11.0, 12.0 }
        };

        JamMatrix matrix = JamMatrix.copyOf(array);

        assertMatrix(array, new double[][] {{ 0.0, 1.0, 2.0 }, { 10.0, 11.0, 12.0 }});
        assertMatrix(matrix, new double[][] {{ 0.0, 1.0, 2.0 }, { 10.0, 11.0, 12.0 }});

        // Ensure that changes are not propagated...
        array[0][0] = 88.0;
        array[1][2] = 99.0;

        matrix.set(0, 1, 55.0);
        matrix.set(1, 0, 77.0);

        assertMatrix(array, new double[][] {{ 88.0, 1.0, 2.0 }, { 10.0, 11.0, 99.0 }});
        assertMatrix(matrix, new double[][] {{ 0.0, 55.0, 2.0 }, { 77.0, 11.0, 12.0 }});
    }

    @Test
    public void testWrap() {
        double[][] array = new double[][] {
                {  0.0,  1.0,  2.0 },
                { 10.0, 11.0, 12.0 }
        };

        JamMatrix matrix = JamMatrix.wrap(array);

        assertMatrix(array, new double[][] {{ 0.0, 1.0, 2.0 }, { 10.0, 11.0, 12.0 }});
        assertMatrix(matrix, new double[][] {{ 0.0, 1.0, 2.0 }, { 10.0, 11.0, 12.0 }});

        // Ensure that changes are propagated...
        array[0][0] = 88.0;
        array[1][2] = 99.0;

        matrix.set(0, 1, 55.0);
        matrix.set(1, 0, 77.0);

        assertMatrix(array, new double[][] {{ 88.0, 55.0, 2.0 }, { 77.0, 11.0, 99.0 }});
        assertMatrix(matrix, new double[][] {{ 88.0, 55.0, 2.0 }, { 77.0, 11.0, 99.0 }});
    }
}
