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

import com.google.common.collect.Multiset;

/**
 * Provides static utility methods operating on Multisets.
 *
 * @author Scott Shaffer
 */
public class JamMultisets {
    /**
     * Computes the frequency at which an items appears in a multiset.
     *
     * @param set the multiset to examine.
     * @param key the key to search for.
     *
     * @return the frequency at which the specified item appears in
     * the multiset: {@code set.count(key) / ((double) set.size())}.
     */
    public static <T> double frequency(Multiset<T> set, T key) {
        return set.count(key) / ((double) set.size());
    }
}
