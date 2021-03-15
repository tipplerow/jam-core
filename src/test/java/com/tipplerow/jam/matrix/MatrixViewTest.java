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
public class MatrixViewTest extends MatrixTestBase {
    @Test
    public void testWrap() {
        double[][] array = new double[][] {
                {  0.0,  1.0,  2.0 },
                { 10.0, 11.0, 12.0 }
        };

        MatrixView view = MatrixView.of(array);

        assertEquals(2, view.nrow());
        assertEquals(3, view.ncol());

        assertDouble(0.0, view.get(0, 0));
        assertDouble(1.0, view.get(0, 1));
        assertDouble(2.0, view.get(0, 2));

        assertDouble(10.0, view.get(1, 0));
        assertDouble(11.0, view.get(1, 1));
        assertDouble(12.0, view.get(1, 2));

        array[0][0] = 88.0;
        array[1][2] = 99.0;

        assertDouble(88.0, view.get(0, 0));
        assertDouble( 1.0, view.get(0, 1));
        assertDouble( 2.0, view.get(0, 2));

        assertDouble(10.0, view.get(1, 0));
        assertDouble(11.0, view.get(1, 1));
        assertDouble(99.0, view.get(1, 2));
    }
}
