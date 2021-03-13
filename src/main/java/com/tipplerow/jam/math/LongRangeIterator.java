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
package com.tipplerow.jam.math;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class LongRangeIterator implements Iterator<Long> {
    private long nextk;
    private final LongRange range;

    LongRangeIterator(LongRange range) {
        this.range = range;
        this.nextk = range.lower();
    }

    @Override public boolean hasNext() {
        return nextk <= range.upper();
    }

    @Override public Long next() {
        if (!hasNext())
            throw new NoSuchElementException();

        Long nextI = nextk;
        ++nextk;

        return nextI;
    }

    @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
