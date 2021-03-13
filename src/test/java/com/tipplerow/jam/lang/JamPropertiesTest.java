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

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.tipplerow.jam.junit.JamTestBase;
import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.math.IntRange;

import org.junit.*;
import static org.junit.Assert.*;

public class JamPropertiesTest extends JamTestBase {
    private final File testProp = resolveFile("lang/test.properties");

    public enum FOO { ABC, DEF, GHI }

    static {
        System.setProperty("jam.int.bad", "foo");
        System.setProperty("jam.int.good", "123");
        System.setProperty("jam.double.bad", "bar");
        System.setProperty("jam.double.good", "123.456");
        System.setProperty("jam.boolean.true", "true");
        System.setProperty("jam.boolean.false", "false");
        System.setProperty("jam.boolean.bogus", "123");
        System.setProperty("jam.enum.ghi", "GHI");
        System.setProperty("jam.enum.xyz", "XYZ");
        System.setProperty("jam.enumSet", "ABC, GHI");
    }

    @Test public void testAll() {
        Map<String, String> properties = JamProperties.all();

        assertTrue(properties.containsKey("file.separator"));
        assertTrue(properties.containsKey("user.name"));

        assertTrue(properties.containsKey("jam.int.good"));
    }

    @Test public void testFilter() {
        Map<String, String> properties = JamProperties.filter("jam.");

        assertFalse(properties.containsKey("file.separator"));
        assertFalse(properties.containsKey("user.name"));

        assertTrue(properties.containsKey("jam.int.good"));
    }

    @Test public void testLoadFile() {
        //
        // Temporary override of a property defined in the
        // "conf/jam.properties" file...
        //
        String propName      = "jam.prop1";
        String propFileValue = "test_property_1";
        String propOverride  = "foo";

        System.setProperty(propName, propOverride);
        
        // Ensure that the original system property was not
        // overwritten by the value from the properties file...
        JamProperties.loadFile(testProp, false);
        assertEquals(propOverride, JamProperties.getRequired(propName));

        // Now reload with a true override flag and check that the
        // property was overwritten...
        JamProperties.loadFile(testProp, true);
        assertEquals(propFileValue, JamProperties.getRequired(propName));
    }

    @Test public void testParseFile() {
        PropertyList fileProps = JamProperties.parseFile(testProp);

        assertEquals(2, fileProps.size());
        assertEquals("test_property_1", fileProps.get("jam.prop1"));
        assertTrue(fileProps.isSet("jam.prop2"));
    }

    @Test public void testRequiredPresent() {
        //
        // Just needs not to throw an exception...
        //
        JamProperties.getRequired("jam.int.good");
    }

    @Test(expected = RuntimeException.class)
    public void testRequiredMissing() {
        JamProperties.getRequired("no_such_property");
    }

    @Test public void testIsSet() {
        assertTrue(JamProperties.isSet("jam.int.good"));
        assertFalse(JamProperties.isSet("no_such_property"));
    }

    @Test public void testIsUnset() {
        assertFalse(JamProperties.isUnset("jam.int.good"));
        assertTrue(JamProperties.isUnset("no_such_property"));
    }

    @Test public void testBoolean() {
        assertTrue(JamProperties.getRequiredBoolean("jam.boolean.true"));
        assertFalse(JamProperties.getRequiredBoolean("jam.boolean.false"));
        assertFalse(JamProperties.getRequiredBoolean("jam.boolean.bogus"));

        assertTrue(JamProperties.getOptionalBoolean("no_such_property", true));
        assertFalse(JamProperties.getOptionalBoolean("no_such_property", false));
    }

    @Test public void testDouble() {
        assertEquals(123.456, JamProperties.getRequiredDouble("jam.double.good"), 0.001);
        assertEquals(888.888, JamProperties.getOptionalDouble("no_such_property", 888.888), 0.001);
        assertEquals(123.456, JamProperties.getRequiredDouble("jam.double.good", DoubleRange.closed(123.0, 124.0)), 0.001);
    }

    @Test(expected = RuntimeException.class)
    public void testDoubleInvalidFormat() {
        JamProperties.getRequiredDouble("jam.double.bad");
    }

    @Test(expected = RuntimeException.class)
    public void testDoubleOutOfRange() {
        JamProperties.getRequiredDouble("jam.double.good", DoubleRange.closed(0.0, 1.0));
    }

    @Test public void testEnum() {
        assertEquals(FOO.GHI, JamProperties.getRequiredEnum("jam.enum.ghi", FOO.class));
        assertEquals(FOO.ABC, JamProperties.getOptionalEnum("no_such_prop", FOO.ABC));
    }

    @Test(expected = RuntimeException.class)
    public void testEnumMismatch() {
        assertEquals(FOO.GHI, JamProperties.getRequiredEnum("jam.enum.xyz", FOO.class));
    }

    @Test public void testEnumSet() {
        assertEquals(Set.of(FOO.ABC, FOO.GHI), JamProperties.getRequiredEnumSet("jam.enumSet", FOO.class));
    }

    @Test public void testInt() {
        assertEquals(123, JamProperties.getRequiredInt("jam.int.good"));
        assertEquals(888, JamProperties.getOptionalInt("no_such_property", 888));
        assertEquals(123, JamProperties.getRequiredInt("jam.int.good", IntRange.POSITIVE));
    }

    @Test(expected = RuntimeException.class)
    public void testIntInvalidFormat() {
        JamProperties.getRequiredInt("jam.int.bad");
    }

    @Test(expected = RuntimeException.class)
    public void testIntOutOfRange() {
        JamProperties.getRequiredInt("jam.int.good", IntRange.NEGATIVE);
    }

    @Test public void testPropertyReference() {
        JamProperties.setProperty("base.prop", "123", true);
        JamProperties.setProperty("test.prop", "<base.prop>", true);

        assertEquals("123", JamProperties.getRequired("test.prop"));
    }

    @Test public void testReplace() {
        assertEquals("abc/GHI/foo/XYZ/bar",
                     JamProperties.replaceProperty("abc/<jam.enum.ghi>/foo/<jam.enum.xyz>/bar"));
    }
}
