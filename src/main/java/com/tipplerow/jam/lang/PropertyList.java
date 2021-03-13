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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides a specialized map to manage system properties.
 *
 * <p>This list maintains the property names and values <em>in
 * the order of addition</em> so that property values may refer
 * to previously added values.
 */ 
public final class PropertyList {
    private final Map<String, String> properties =
            new LinkedHashMap<>();

    /**
     * Creates an empty property list.
     */
    public PropertyList() {
    }

    /**
     * Appends all properties from another list onto this list.
     *
     * @param that the list to append.
     */
    public void append(PropertyList that) {
        properties.putAll(that.properties);
    }

    /**
     * Returns the property associated with a given name.
     *
     * @param name the property name of interest.
     *
     * @return the property associated with the specified name, or
     * {@code null} if there is no matching property.
     */
    public String get(String name) {
        return properties.get(name);
    }

    /**
     * Identifies property names in this list.
     *
     * @param name the property name of interest.
     *
     * @return {@code true} iff this list contains a property with the
     * specified name.
     */
    public boolean isSet(String name) {
        return properties.containsKey(name);
    }

    /**
     * Returns the property names in this list <em>in the order that
     * they were added</em>.
     *
     * @return the property names in this list <em>in the order that
     * they were added</em>.
     */
    public Set<String> names() {
        return properties.keySet();
    }

    /**
     * Returns the property associated with a given name.
     *
     * @param name the property name of interest.
     *
     * @return the property associated with the specified name.
     *
     * @throws RuntimeException unless this list contains a property
     * with the specified name.
     */
    public String require(String name) {
        String value = get(name);

        if (value != null)
            return value;
        else
            throw JamException.runtime("Unset property: [%s].", name);
    }

    /**
     * Adds a property name and value to this list (overwriting any
     * previous value associated with the name).
     *
     * @param name the name of the property.
     *
     * @param value the value of the property.
     */
    public void set(String name, String value) {
        properties.put(name, value);
    }

    /**
     * Returns the number of properties in this list.
     *
     * @return the number of properties in this list.
     */
    public int size() {
        return properties.size();
    }
}
