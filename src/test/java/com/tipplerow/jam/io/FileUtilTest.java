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

import org.junit.*;
import static org.junit.Assert.*;

public class FileUtilTest extends IOTestBase {
    @Test public void testEnsureDir() {
        FileUtil.ensureDir(".");
        FileUtil.ensureDir(System.getProperty("user.home"));

        File dir1 = new File("dir1");
        File dir2 = new File(dir1, "dir2");

        assertFalse(dir1.exists());
        assertFalse(dir2.exists());

        FileUtil.ensureDir(dir2);

        assertTrue(dir1.exists());
        assertTrue(dir2.exists());

        assertTrue(dir2.delete());
        assertTrue(dir1.delete());
    }

    @Test public void testEnsureParentDirs() {
        File dir  = new File("foodir");
        File file = new File(dir, "foo.txt");

        assertFalse(dir.exists());

        FileUtil.ensureParentDirs(file);

        assertTrue(dir.exists());
        assertTrue(dir.delete());
    }

    @Test public void testGetBaseName() {
        assertEquals("foo.txt", FileUtil.getBaseName(new File("foo.txt")));
        assertEquals("foo.txt", FileUtil.getBaseName(new File("/var/tmp/foo.txt")));
    }

    @Test public void testGetBaseNamePrefix() {
        assertEquals("foo", FileUtil.getBaseNamePrefix(new File("foo.txt")));
        assertEquals("foo", FileUtil.getBaseNamePrefix(new File("/var/tmp/foo.txt.gz")));
    }

    @Test public void testGetCanonicalFile() {
        File localFile = new File("foo.txt");
        File canonicalFile = FileUtil.getCanonicalFile(localFile);

        assertEquals("foo.txt", localFile.getPath());
        //assertEquals(FileUtil.join(JamHome.NAME, "foo.txt"), canonicalFile.getPath());
    }

    @Test public void testGetCanonicalPath() {
        //assertEquals(FileUtil.join(JamHome.NAME, "foo.txt"), FileUtil.getCanonicalPath(new File("foo.txt")));
    }

    @Test public void testGetCanonicalPrefix() {
        //assertEquals(FileUtil.join(JamHome.NAME, "foo"), FileUtil.getCanonicalPrefix(new File("foo.xml.gz")));
    }

    @Test public void testGetDirName() {
        assertEquals(".", FileUtil.getDirName(new File("foo.txt")));
        assertEquals("foo", FileUtil.getDirName(new File("foo/bar.txt")));
    }

    @Test public void testIsCanonicalFile() {
        assertFalse(FileUtil.isCanonicalFile(new File("foo.txt")));
        assertFalse(FileUtil.isCanonicalFile(new File("data/test/foo.txt")));
        assertTrue(FileUtil.isCanonicalFile(new File("/usr/bin/syslog")));
        assertTrue(FileUtil.isCanonicalFile(new File("/usr/bin/syslog/")));
    }

    @Test public void testRequireFound() {
        FileUtil.requireFile(System.getProperty("user.home"));
    }

    @Test(expected = RuntimeException.class)
    public void testRequireNotFound() {
        FileUtil.requireFile("no such file");
    }
}
