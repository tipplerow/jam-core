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

import com.tipplerow.jam.regex.RegexUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class IOUtilTest extends IOTestBase {
    @Test public void testGZip() throws IOException {
        File file = new File("test.gz");
        file.deleteOnExit();

        PrintWriter writer = IOUtil.openWriter(file);

        String line1 = "abcdefg";
        String line2 = "hijklmn";

        writer.println(line1);
        writer.println(line2);
        writer.close();

        BufferedReader reader = IOUtil.openReader(file);

        assertEquals(line1, reader.readLine());
        assertEquals(line2, reader.readLine());
        assertNull(reader.readLine());

        reader.close();
    }

    @Test public void testNextDataLine() {
        BufferedReader reader = IOUtil.openReader(comments123);

        assertEquals("line 1", IOUtil.nextDataLine(reader, RegexUtil.PYTHON_COMMENT));
        assertEquals("line 2", IOUtil.nextDataLine(reader, RegexUtil.PYTHON_COMMENT));
        assertEquals("line 3", IOUtil.nextDataLine(reader, RegexUtil.PYTHON_COMMENT));
        assertNull(IOUtil.nextDataLine(reader, RegexUtil.PYTHON_COMMENT));
    }

    @Test public void testOpenReader() throws IOException {
        BufferedReader reader = IOUtil.openReader(lines123);

        assertEquals("line 1", reader.readLine());
        assertEquals("line 2", reader.readLine());
        assertEquals("line 3", reader.readLine());
        assertNull(reader.readLine());

        IOUtil.close(reader);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testOpenReaderNotFound() {
        IOUtil.openReader("no such file");
    }

    @Test public void testOpenWriter() {
        File tmpFile = new File("tmp1.txt");
        tmpFile.deleteOnExit();

        PrintWriter writer = IOUtil.openWriter(tmpFile, false);
        writer.println("line 1");
        writer.close();

        writer = IOUtil.openWriter(tmpFile, false);
        writer.println("line 2");
        writer.close();

        writer = IOUtil.openWriter(tmpFile, true);
        writer.println("line 3");
        writer.close();

        assertEquals(Arrays.asList("line 2", "line 3"), IOUtil.readLines(tmpFile));
    }

    @Test public void testReadLines() {
        assertEquals(Arrays.asList("line 1", "line 2", "line 3"), IOUtil.readLines(lines123));
    }

    @Test public void testWriteLines() {
        File tmpFile = new File("tmp2.txt");
        tmpFile.deleteOnExit();

        IOUtil.writeLines(tmpFile, false, "foo", "bar");
        IOUtil.writeLines(tmpFile, false, "abc", "def");
        IOUtil.writeLines(tmpFile, true,  "ghi", "jkl");

        assertEquals(Arrays.asList("abc", "def", "ghi", "jkl"), IOUtil.readLines(tmpFile));
    }

    @Test public void testWriteObjects() {
        File tmpFile = new File("tmp3.txt");
        tmpFile.deleteOnExit();

        List<Double> list1 = List.of(0.246, 8.88);
        List<Double> list2 = List.of(1.234, 4.56);
        List<Double> list3 = List.of(2.888, 3.33);

        IOUtil.writeObjects(tmpFile, false, list1, x -> String.format("%.1f", x));
        IOUtil.writeObjects(tmpFile, false, list2, x -> String.format("%.1f", x));
        IOUtil.writeObjects(tmpFile, true,  list3, x -> String.format("%.1f", x));

        assertEquals(List.of("1.2", "4.6", "2.9", "3.3"), IOUtil.readLines(tmpFile));
    }
}
