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

import com.tipplerow.jam.testng.JamTestBase;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class JamEnvTest extends JamTestBase {
    @Test public void testIsSet() {
        assertTrue(JamEnv.isSet("USER"));
        assertFalse(JamEnv.isSet("no such variable"));
    }

    @Test public void testIsUnset() {
        assertTrue(JamEnv.isUnset("no such variable"));
        assertFalse(JamEnv.isUnset("USER"));
    }

    @Test public void testReplace() {
        String user = System.getenv("USER");
        String shell = System.getenv("SHELL");

        assertEquals(user, JamEnv.replaceVariable("${USER}"));
        assertEquals("foo/" + user + "/xyz/" + shell, JamEnv.replaceVariable("foo/${USER}/xyz/${SHELL}"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testReplaceUnset() {
        JamEnv.replaceVariable("${__NOSUCHVAR__}");
    }
}
