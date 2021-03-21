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

import java.util.Collection;

/**
 * Provides static utility methods operating on collections.
 *
 * @author Scott Shaffer
 */
public final class JamCollections {
    /**
     * Returns the first element of a collection (or {@code null} if the
     * collection is empty).
     *
     * @param collection the collection to peek at.
     *
     * @return the first element of a collection (or {@code null} if the
     * collection is empty).
     */
    public static <T> T peek(Collection<T> collection) {
        if (collection.isEmpty())
            return null;
        else
            return collection.iterator().next();
    }
}
