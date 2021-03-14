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
package com.tipplerow.jam.testng;

import java.util.List;

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.math.JamRandom;

import static org.testng.Assert.*;

/**
 * Provides a base class for all numerical JUnit tests in the
 * {@code jam} project.
 *
 * @author Scott Shaffer
 */
public abstract class NumericTestBase extends JamTestBase {
    private final double tolerance;
    private final DoubleComparator comparator;

    private JamRandom random = null;

    private static final long SEED = 19410711;

    public static final double DEFAULT_TOLERANCE = 1.0e-12;

    protected NumericTestBase() {
        this(DEFAULT_TOLERANCE);
    }

    protected NumericTestBase(double tolerance) {
        this.tolerance = tolerance;
        this.comparator = new DoubleComparator(tolerance);
    }

    public void assertDouble(double actual, double expected) {
        assertEquals(actual, expected, tolerance);
    }

    public void assertDouble(double[] actual, double[] expected) {
        assertTrue(comparator.equals(actual, expected));
    }

    public void assertDouble(List<Double> actual, List<Double> expected) {
        assertEquals(actual.size(), expected.size());

        for (int index = 0; index < expected.size(); ++index)
            assertDouble(actual.get(index), expected.get(index));
    }

    public JamRandom random() {
        if (random == null)
            random = JamRandom.generator(SEED);

        return random;
    }
}
