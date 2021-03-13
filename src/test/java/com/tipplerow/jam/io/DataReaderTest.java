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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.tipplerow.jam.junit.JamTestBase;

import org.junit.*;
import static org.junit.Assert.*;

public final class DataReaderTest extends JamTestBase {
    private static final Pattern comment = Pattern.compile("#");

    private void assertLines(String fileName, List<String> expected) {
        assertEquals(expected, DataReader.read(resolveFile(fileName), comment));
    }

    @Test
    public void testContinuation() {
        assertLines("io/continuation.txt", Arrays.asList("line 1", "line 2", "line 3", "abc def ghi"));
    }

    @Test
    public void testIterator() {
        assertLines("io/comments123.txt", Arrays.asList("line 1", "line 2", "line 3"));
    }

    @Test
    public void testNext() {
        DataReader reader = DataReader.open(resolveFile("io/comments123.txt"), comment);

        assertEquals("line 1", reader.next());
        assertEquals("line 2", reader.next());
        assertEquals("line 3", reader.next());
        assertFalse(reader.hasNext());

        reader.close();
    }
}
