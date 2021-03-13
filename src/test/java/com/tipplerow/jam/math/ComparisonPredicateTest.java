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

import java.util.function.DoublePredicate;

import com.tipplerow.jam.junit.NumericTestBase;

import org.junit.*;
import static org.junit.Assert.*;

public class ComparisonPredicateTest extends NumericTestBase {
    private static final double BOUND = 10.0;
    private static final double SMALL = 1.0E-08;
    private static final double TINY  = 1.0E-15;

    @Test public void testLT() {
        DoublePredicate predicate = ComparisonPredicate.LT(BOUND);

		assertTrue(predicate.test(BOUND - SMALL));
		assertFalse(predicate.test(BOUND - TINY));
		assertFalse(predicate.test(BOUND));
		assertFalse(predicate.test(BOUND + TINY));
		assertFalse(predicate.test(BOUND + SMALL));
    }

    @Test public void testLE() {
        DoublePredicate predicate = ComparisonPredicate.LE(BOUND);

		assertTrue(predicate.test(BOUND - SMALL));
		assertTrue(predicate.test(BOUND - TINY));
		assertTrue(predicate.test(BOUND));
		assertTrue(predicate.test(BOUND + TINY));
		assertFalse(predicate.test(BOUND + SMALL));
    }

    @Test public void testGE() {
        DoublePredicate predicate = ComparisonPredicate.GE(BOUND);

		assertFalse(predicate.test(BOUND - SMALL));
		assertTrue(predicate.test(BOUND - TINY));
		assertTrue(predicate.test(BOUND));
		assertTrue(predicate.test(BOUND + TINY));
		assertTrue(predicate.test(BOUND + SMALL));
    }

    @Test public void testGT() {
        DoublePredicate predicate = ComparisonPredicate.GT(BOUND);

		assertFalse(predicate.test(BOUND - SMALL));
		assertFalse(predicate.test(BOUND - TINY));
		assertFalse(predicate.test(BOUND));
		assertFalse(predicate.test(BOUND + TINY));
		assertTrue(predicate.test(BOUND + SMALL));
    }
}
