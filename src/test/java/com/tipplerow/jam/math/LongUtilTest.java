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

public class LongUtilTest extends NumericTestBase {
    @Test public void testIntValue() {
        assertEquals(-99887766, LongUtil.intValue(-99887766L));
        assertEquals( 12345678, LongUtil.intValue( 12345678L));

        assertEquals(-2147483648, LongUtil.intValue(-(1L << 31)));
        assertEquals( 2147483647, LongUtil.intValue( (1L << 31) - 1L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIntValueInvalid1() {
        LongUtil.intValue(-(1L << 33L));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIntValueInvalid2() {
        LongUtil.intValue(1L << 33L);
    }

    @Test public void testParseLong() {
        assertEquals(-234L, LongUtil.parseLong("-234"));
        assertEquals(1234L, LongUtil.parseLong("1234"));

        assertEquals(1234L, LongUtil.parseLong("1.234E3"));
        assertEquals(1000000000000L, LongUtil.parseLong("1E12"));
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void testParseLongInvalid1() {
        LongUtil.parseLong("1.234");
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void testParseLongInvalid2() {
        LongUtil.parseLong("1.234E2");
    }
}
