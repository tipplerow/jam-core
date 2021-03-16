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

/**
 * Denotes objects that have a well-defined string representation.
 *
 * <p>Classes that implement this interface typically need to be
 * created by parsing string representations found in text files,
 * and the {@link Formatted#format()} method provides a contract 
 * for the structural form of that representation.
 *
 * @author Scott Shaffer
 */
public interface Formatted {
    /**
     * Generates the standard string representation of this object.
     *
     * <p>It should be possible to create an identical object by
     * parsing the string returned by this method.
     *
     * @return the standard string representation of this object.
     */
    String format();

    /**
     * Returns a string representation of this object that should be
     * informative for debugging or other log messages.
     *
     * @return the simple class name of this object followed by the
     * format string enclosed in parentheses.
     */
    default String debug() {
        return getClass().getSimpleName() + "(" + format() + ")";
    }
}
