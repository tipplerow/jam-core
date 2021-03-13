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
package com.tipplerow.jam.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class LineReaderTest extends IOTestBase {
    @Test public void testIterator() {
        ArrayList<String> lines = new ArrayList<>();
        LineReader reader = LineReader.open(lines123);

        for (String line : reader)
            lines.add(line);

        reader.close();
        assertEquals(Arrays.asList("line 1", "line 2", "line 3"), lines);
    }

    @Test public void testNext() {
        LineReader reader = LineReader.open(lines123);

        assertEquals("line 1", reader.next());
        assertEquals("line 2", reader.next());
        assertEquals("line 3", reader.next());
        assertFalse(reader.hasNext());

        reader.close();
    }
}
