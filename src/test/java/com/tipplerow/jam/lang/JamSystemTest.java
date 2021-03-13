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

import com.tipplerow.jam.junit.JamTestBase;

import org.junit.*;
import static org.junit.Assert.*;

public class JamSystemTest extends JamTestBase {
    @Test public void testCoreCount() {
        //
        // The core count will vary by system, so just make sure that
        // the method call does not throw an exception and returns a
        // positive value...
        //
        if (JamSystem.isMacOS())
            assertTrue(JamSystem.getCoreCount() > 0);
    }

    @Test public void testCPUCount() {
        //
        // The core count will vary by system, so just make sure that
        // the method call does not throw an exception and returns a
        // positive value...
        //
        if (JamSystem.isMacOS())
            assertTrue(JamSystem.getCPUCount() > 0);
    }

    @Test public void testIsLinux() {
        System.out.println("Is Linux? " + JamSystem.isLinux());
    }

    @Test public void testIsMacOS() {
        System.out.println("Is MacOS? " + JamSystem.isMacOS());
    }

    @Test public void testUname() {
        System.out.println("uname = " + JamSystem.uname());
    }
}
