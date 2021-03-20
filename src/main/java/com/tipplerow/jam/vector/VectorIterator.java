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
package com.tipplerow.jam.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class VectorIterator implements Iterator<Double> {
    private int index;
    private final VectorView vector;

    VectorIterator(VectorView vector) {
        this.index  = 0;
        this.vector = vector;
    }

    @Override public boolean hasNext() {
        return index < vector.length();
    }

    @Override public Double next() {
        if (!hasNext())
            throw new NoSuchElementException();

        return vector.get(index++);
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
