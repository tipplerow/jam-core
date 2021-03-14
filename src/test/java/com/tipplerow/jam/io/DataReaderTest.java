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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class DataReaderTest extends IOTestBase {
    private static final Pattern comment = Pattern.compile("#");

    private void assertLines(File file, List<String> expected) {
        assertEquals(DataReader.read(file, comment), expected);
    }

    @Test
    public void testContinuation() {
        assertLines(continuation, Arrays.asList("line 1", "line 2", "line 3", "abc def ghi"));
    }

    @Test
    public void testIterator() {
        assertLines(comments123, Arrays.asList("line 1", "line 2", "line 3"));
    }

    @Test
    public void testNext() {
        DataReader reader = DataReader.open(comments123, comment);

        assertEquals(reader.next(), "line 1");
        assertEquals(reader.next(), "line 2");
        assertEquals(reader.next(), "line 3");
        assertFalse(reader.hasNext());

        reader.close();
    }
}
