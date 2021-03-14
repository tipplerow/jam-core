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

import com.tipplerow.jam.math.DoubleComparator;

import lombok.Value;

/**
 * Encapsulates the index and value from a single vector element.
 *
 * @author Scott Shaffer
 */
@Value(staticConstructor = "of")
public class VectorElement {
    /**
     * The zero-based index of this element.
     */
    int index;

    /**
     * The value contained in this element.
     */
    double value;

    /**
     * Identifies elements with finite values.
     *
     * @return {@code true} iff the value in this element is finite
     * (not NaN or infinite).
     */
    public boolean isFinite() {
        return Double.isFinite(value);
    }

    /**
     * Identifies elements with negative values.
     *
     * @return {@code true} iff the value in this element is negative
     * (as classified by the default DoubleComparator).
     */
    public boolean isNegative() {
        return DoubleComparator.DEFAULT.isNegative(value);
    }

    /**
     * Identifies elements with zero values.
     *
     * @return {@code true} iff the value in this element is zero
     * (as classified by the default DoubleComparator).
     */
    public boolean isZero() {
        return DoubleComparator.DEFAULT.isZero(value);
    }
    /**
     * Identifies elements with non-zero values.
     *
     * @return {@code true} iff the value in this element is non-zero
     * (as classified by the default DoubleComparator).
     */
    public boolean isNonZero() {
        return DoubleComparator.DEFAULT.isNonZero(value);
    }

    /**
     * Identifies elements with positive values.
     *
     * @return {@code true} iff the value in this element is positive
     * (as classified by the default DoubleComparator).
     */
    public boolean isPositive() {
        return DoubleComparator.DEFAULT.isPositive(value);
    }
}
