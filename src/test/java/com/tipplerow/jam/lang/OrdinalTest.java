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

public class OrdinalTest {
    private static final class OrdinalClass extends Ordinal {
        public static final OrdinalIndex index = OrdinalIndex.create();

        public OrdinalClass() {
            super(index);
        }
    }

    @Test public void testIndex() {
        OrdinalClass obj0 = new OrdinalClass();
        OrdinalClass obj1 = new OrdinalClass();
        OrdinalClass obj2 = new OrdinalClass();

        assertEquals(0, obj0.getIndex());
        assertEquals(1, obj1.getIndex());
        assertEquals(2, obj2.getIndex());
        assertEquals(3, OrdinalClass.index.peek());
    }
}
