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

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public final class DelimiterTest extends IOTestBase {
    private void runOne(Delimiter delim, String line, String... fields) {
        assertEquals(delim.join(fields), line);
        assertEquals(delim.split(line), fields);
    }

    private void testSplit(Delimiter delim, String line, String... expected) {
        assertEquals(delim.split(line), expected);
    }

    @Test public void testComma() {
        runOne(Delimiter.COMMA, "A,B,C,D", "A", "B", "C", "D");
        runOne(Delimiter.COMMA, "A,B\\,C,D", "A", "B,C", "D");
    }

    @Test public void testHyphen() {
        runOne(Delimiter.HYPHEN, "A-B-C", "A", "B", "C");
    }

    @Test public void testPipe() {
        runOne(Delimiter.PIPE, "A|", "A", "");
        runOne(Delimiter.PIPE, "A||", "A", "", "");
        runOne(Delimiter.PIPE, "A||B|||C", "A", "", "B", "", "", "C");
        runOne(Delimiter.PIPE, "A|B|C|D", "A", "B", "C", "D");
        runOne(Delimiter.PIPE, "A|B\\|C|D", "A", "B|C", "D");
    }

    @Test public void testTab() {
        runOne(Delimiter.TAB, "A\tB\tC\tD", "A", "B", "C", "D");
    }

    @Test public void testWhiteSpace() {
        assertEquals(Delimiter.WHITE_SPACE.split("A\tB    C D"), new String[] { "A", "B", "C", "D" });
        assertEquals("A B C D", Delimiter.WHITE_SPACE.join("A", "B", "C", "D"));
    }
}
