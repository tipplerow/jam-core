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
 * Encapsulates the management of ordinal indexes.
 *
 * @author Scott Shaffer
 */
public final class OrdinalIndex {
    private long count = 0;

    private OrdinalIndex() {}

    /**
     * Returns a new ordinal index initialized to zero.
     *
     * @return a new ordinal index initialized to zero.
     */
    public static OrdinalIndex create() {
        return new OrdinalIndex();
    }

    /**
     * Returns the index of the next object to be indexed (and
     * increments the underlying counter).
     *
     * @return the index of the next object to be indexed.
     */
    public long next() {
        return count++;
    }

    /**
     * Returns the index of the next object to be indexed (but leaves
     * the underlying counter unchanged).
     *
     * @return the index of the next object to be indexed.
     */
    public long peek() {
        return count;
    }

    @Override public boolean equals(Object that) {
        return (that instanceof OrdinalIndex) && equalsOrdinalIndex((OrdinalIndex) that);
    }

    private boolean equalsOrdinalIndex(OrdinalIndex that) {
        return this.count == that.count;
    }

    @Override public int hashCode() {
        return (int) count;
    }

    @Override public String toString() {
        return String.format("OrdinalIndex(%d)", count);
    }
}
