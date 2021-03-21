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
package com.tipplerow.jam.collect;

import java.util.List;

import com.tipplerow.jam.math.JamRandom;

/**
 * Provides static utility methods operating on lists.
 *
 * @author Scott Shaffer
 */
public final class JamLists {
    /**
     * Determines whether a list is sorted by the natural ordering of
     * its elements.
     *
     * @param <V>  the element type.
     * @param list the list to examine.
     *
     * @return {@code true} iff the elements in the input list are in
     * non-decreasing natural order.
     */
    public static <V extends Comparable<? super V>> boolean isSorted(List<V> list) {
        //
        // Do this with an iterator rather than "get()" to avoid the
        // O(n^2) scaling for linked lists...
        //
        V prev = null;

        for (V curr : list)
            if (prev != null && prev.compareTo(curr) > 0)
                return false;
            else
                prev = curr;

        return true;
    }

    /**
     * Selects one list element at random using the global random
     * number generator.
     *
     * @param <V>  the element type.
     * @param list the list to select from.
     *
     * @return an element selected at random.
     *
     * @throws IllegalArgumentException if the list is empty.
     */
    public static <V> V select(List<V> list) {
        return select(list, JamRandom.global());
    }

    /**
     * Selects one list element at random.
     *
     * @param <V>  the element type.
     * @param list the list to select from.
     *
     * @param random the random number source.
     *
     * @return an element selected at random.
     *
     * @throws IllegalArgumentException if the list is empty.
     */
    public static <V> V select(List<V> list, JamRandom random) {
        if (list.isEmpty())
            throw new IllegalArgumentException("Empty list.");

        if (list.size() == 1)
            return list.get(0);

        return list.get(random.nextInt(list.size()));
    }
}
