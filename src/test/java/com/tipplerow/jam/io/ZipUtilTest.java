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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.*;
import static org.junit.Assert.*;

public final class ZipUtilTest extends IOTestBase {
    @Test public void testReaderWriter() throws IOException {
        File file = new File("test.gz");
        file.deleteOnExit();
        
        PrintWriter writer = ZipUtil.openGZipWriter(file);

        String line1 = "abcdefg";
        String line2 = "hijklmn";

        writer.println(line1);
        writer.println(line2);
        writer.close();

        BufferedReader reader = ZipUtil.openGZipReader(file);

        assertEquals(line1, reader.readLine());
        assertEquals(line2, reader.readLine());
        assertNull(reader.readLine());

        reader.close();
    }
}
