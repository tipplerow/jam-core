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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class OrdinalIndexTest {
    @Test public void testAll() {
        OrdinalIndex index1 = OrdinalIndex.create();
        OrdinalIndex index2 = OrdinalIndex.create();

        assertEquals(index2, index1);

        assertEquals(0, index1.peek());
        assertEquals(0, index1.peek());
        assertEquals(0, index1.next());

        assertEquals(1, index1.peek());
        assertEquals(1, index1.peek());
        assertEquals(1, index1.next());

        assertEquals(2, index1.peek());
        assertEquals(2, index1.next());

        assertNotEquals(index2, index1);

        assertEquals(0, index2.peek());
        assertEquals(0, index2.peek());
        assertEquals(0, index2.next());

        assertEquals(1, index2.peek());
        assertEquals(1, index2.peek());
        assertEquals(1, index2.next());

        assertEquals(2, index2.peek());
        assertEquals(2, index2.peek());
        assertEquals(2, index2.next());

        assertEquals(index2, index1);
    }
}
