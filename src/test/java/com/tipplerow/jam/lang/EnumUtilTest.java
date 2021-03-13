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
package com.tipplerow.jam.lang;

import java.util.List;
import java.util.Set;

import com.tipplerow.jam.junit.JamTestBase;

import org.junit.*;
import static org.junit.Assert.*;

public class EnumUtilTest extends JamTestBase {
    public enum ABC { A, B, C }

    @Test public void testCount() {
        assertEquals(3, EnumUtil.count(ABC.class));
    }

    @Test public void testList() {
        assertEquals(List.of(ABC.A, ABC.B, ABC.C), EnumUtil.list(ABC.class));
    }

    @Test public void testNames() {
        Set<String> names = EnumUtil.names(ABC.class);

        assertEquals(3, names.size());
        assertTrue(names.contains("A"));
        assertTrue(names.contains("B"));
        assertTrue(names.contains("C"));
    }

    @Test public void testValueOf() {
        assertEquals(ABC.A, EnumUtil.valueOf(ABC.class, "A"));
        assertEquals(ABC.B, EnumUtil.valueOf(ABC.class, "B"));
        assertEquals(ABC.C, EnumUtil.valueOf(ABC.class, "C"));
    }

    @Test(expected = RuntimeException.class)
    public void testValueOfInvalid() {
        EnumUtil.valueOf(ABC.class, "FOO");
    }

    @Test public void testValues() {
        assertArrayEquals(ABC.values(), EnumUtil.values(ABC.class));
    }
}
