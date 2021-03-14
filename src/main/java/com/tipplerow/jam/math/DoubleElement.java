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

import com.tipplerow.jam.lang.JamException;
import lombok.Getter;

/**
 * Provides a base class for immutable vector and matrix elements.
 *
 * @author Scott Shaffer
 */
public abstract class DoubleElement {
    /**
     * The value contained in this element.
     */
    @Getter
    private final double value;

    /**
     * Creates a new element with a fixed value.
     *
     * @param value the value in the element.
     */
    protected DoubleElement(double value) {
        this.value = value;
    }

    /**
     * Ensures that an element index is non-negative.
     *
     * @param index the index to validate.
     *
     * @throws RuntimeException if the index is negative.
     */
    public static void validateIndex(int index) {
        if (index < 0)
            throw JamException.runtime("Negative element index: [%d].", index);
    }

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
