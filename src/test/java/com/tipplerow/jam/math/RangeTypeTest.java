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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RangeTypeTest extends NumericTestBase {
    @Test public void testFormat() {
        assertEquals("(1.0, 2.0)", RangeType.OPEN.format(1.0, 2.0));
        assertEquals("[1.0, 2.0)", RangeType.LEFT_CLOSED.format(1.0, 2.0));
        assertEquals("(1.0, 2.0]", RangeType.LEFT_OPEN.format(1.0, 2.0));
        assertEquals("[1.0, 2.0]", RangeType.CLOSED.format(1.0, 2.0));
    }

    @Test public void testParse() {
        assertEquals(RangeType.OPEN,        RangeType.parse("(1.0, 2.0)"));
        assertEquals(RangeType.LEFT_CLOSED, RangeType.parse("[1.0, 2.0)"));
        assertEquals(RangeType.LEFT_OPEN,   RangeType.parse("(1.0, 2.0]"));
        assertEquals(RangeType.CLOSED,      RangeType.parse("[1.0, 2.0]"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBad1() {
        RangeType.parse("{1, 2)");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseBad2() {
        RangeType.parse("[1, 2}");
    }
}
