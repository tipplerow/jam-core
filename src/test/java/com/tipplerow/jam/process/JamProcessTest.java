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
package com.tipplerow.jam.process;

import java.util.List;

import com.tipplerow.jam.testng.JamTestBase;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class JamProcessTest extends JamTestBase {
    @Test public void testRun() {
        JamProcess process = JamProcess.run("ls");

        assertTrue(process.success());
        assertTrue(process.stdout().contains("README.md"));
        assertTrue(process.stderr().isEmpty());

        process = JamProcess.run("cat", "fooxyz");
        
        assertFalse(process.success());
        assertTrue(process.stdout().isEmpty());
        assertEquals(List.of("cat: fooxyz: No such file or directory"), process.stderr());
        
        process = JamProcess.run("cat", "LICENSE");

        assertEquals(201, process.stdout().size());
    }

    @Test public void testSetEnv() {
        JamProcess process = JamProcess.create("env");

        assertFalse(process.executed());

        process.run();

        assertTrue(process.executed());
        assertFalse(process.stdout().contains("MYTESTVAR=foo"));

        process.setenv("MYTESTVAR", "foo");
        process.run();

        assertTrue(process.executed());
        assertTrue(process.stdout().contains("MYTESTVAR=foo"));
    }
}
