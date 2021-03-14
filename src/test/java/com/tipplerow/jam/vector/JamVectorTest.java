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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author Scott Shaffer
 */
public class JamVectorTest extends VectorTestBase {
    @Test
    public void testCopyOf() {
        double[] array = new double[] { 0.0, 1.0, 2.0 };
        JamVector vector = JamVector.copyOf(array);

        assertVector(array, 0.0, 1.0, 2.0);
        assertVector(vector, 0.0, 1.0, 2.0);

        // Ensure that changes are not propagated...
        array[0] = 10.0;
        vector.set(1, 11.0);

        assertVector(array, 10.0, 1.0, 2.0);
        assertVector(vector, 0.0, 11.0, 2.0);
    }

    @Test
    public void testWrap() {
        double[] array = new double[] { 0.0, 1.0, 2.0 };
        JamVector vector = JamVector.wrap(array);

        assertVector(array, 0.0, 1.0, 2.0);
        assertVector(vector, 0.0, 1.0, 2.0);

        // Ensure that changes are propagated...
        array[0] = 10.0;
        vector.set(1, 11.0);

        assertVector(array, 10.0, 11.0, 2.0);
        assertVector(vector, 10.0, 11.0, 2.0);
    }
}
