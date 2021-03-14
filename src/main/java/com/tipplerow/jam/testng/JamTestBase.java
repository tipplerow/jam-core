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
package com.tipplerow.jam.testng;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

/**
 * Provides a base class for all JUnit tests in the {@code jam} project.
 *
 * @author Scott Shaffer
 */
public abstract class JamTestBase {
    private static final String FILE_PREFIX = "src/test/resources/";

    /**
     * Asserts that two collections contain identical objects, ignoring
     * their iteration order.
     *
     * @param <V>      the runtime type to compare.
     * @param actual   the actual collection.
     * @param expected the expected collection.
     */
    public <V> void assertCollection(Collection<V> actual, Collection<V> expected) {
        Set<V> actualSet = new HashSet<>(actual);
        Set<V> expectedSet = new HashSet<>(expected);

        assertEquals(actualSet, expectedSet);
    }

    /**
     * Resolves the full path of a file containing test data.
     *
     * @param path the partial path to the test data.
     *
     * @return a new File for the desired test data.
     */
    public File resolveFile(String path) {
        return new File(FILE_PREFIX + path);
    }
}
