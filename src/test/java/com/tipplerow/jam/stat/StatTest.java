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
package com.tipplerow.jam.stat;

import com.tipplerow.jam.testng.NumericTestBase;
import com.tipplerow.jam.vector.VectorView;

import org.testng.annotations.Test;

/**
 * Provides a base class for tests in the {@code stat} package.
 * @author Scott Shaffer
 */
public class StatTest extends NumericTestBase {
    private static final VectorView data1 =
            VectorView.of(0.0, 1.0, 2.0, Double.NaN, -4.0, Double.POSITIVE_INFINITY, 8.0);

    @Test
    public void testMax() {
        assertDouble(8.0, Stat.max(data1));
    }

    @Test
    public void testMean() {
        assertDouble(1.4, Stat.mean(data1));
    }

    @Test
    public void testMin() {
        assertDouble(-4.0, Stat.min(data1));
    }

    @Test
    public void testSum() {
        assertDouble(7.0, Stat.sum(data1));
    }
}
