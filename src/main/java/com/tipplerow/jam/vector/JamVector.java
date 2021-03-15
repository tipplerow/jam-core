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

import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.Arrays;

/**
 * Represents a one-dimensional vector, provides basic operations from
 * linear algebra, and optimizes the underlying physical storage.
 *
 * @author Scott Shaffer
 */
public final class JamVector implements VectorView {
    private VectorImpl impl;

    private JamVector(VectorImpl impl) {
        this.impl = impl;
    }

    /**
     * Creates a new dense vector by copying elements from a bare array.
     * Subsequent changes to the array will not be reflected in the new
     * vector, and subsequent changes to the returned vector will not be
     * reflected in the array.
     *
     * @param array the array to copy.
     *
     * @return a new dense vector with elements initialized to the values
     * of the input array.
     */
    public static JamVector copyOf(double... array) {
        return new JamVector(new DenseApacheImpl(array, true));
    }

    /**
     * Creates a new dense vector by copying elements from a vector view.
     * Subsequent changes to the view will not be reflected in the new
     * vector, and subsequent changes to the returned vector will not be
     * reflected in the view.
     *
     * @param view the vector view to copy.
     *
     * @return a new dense vector with elements initialized to the values
     * of the input view.
     */
    public static JamVector copyOf(VectorView view) {
        return new JamVector(new DenseApacheImpl(view.toArray(), false));
    }

    /**
     * Creates a new vector with a fixed length and dense physical storage.
     *
     * @param length the fixed vector length.
     *
     * @return a new vector with the specified length, dense storage, and
     * all elements initialized to zero.
     *
     * @throws RuntimeException if the length is negative.
     */
    public static JamVector dense(int length) {
        return rep(0.0, length);
    }

    /**
     * Creates a new dense vector with a fixed length having all elements
     * initialized to the same value, as in the R function {@code rep(x, n)}.
     *
     * @param value the value to replicate.
     * @param length the fixed vector length.
     *
     * @return a new dense vector with the specified length and element value.
     *
     * @throws RuntimeException if the length is negative.
     */
    public static JamVector rep(double value, int length) {
        return new JamVector(new DenseApacheImpl(new ArrayRealVector(length, value)));
    }

    /**
     * Creates a new vector using an existing array as the underlying storage.
     * Subsequent changes to the bare array will be reflected in the vector,
     * and subsequent changes to the vector will be reflected in the array.
     *
     * @param array the bare array to wrap in a JamVector.
     *
     * @return a new vector using the specified array as the underlying storage.
     */
    public static JamVector wrap(double[] array) {
        return new JamVector(new DenseApacheImpl(new ArrayRealVector(array, false)));
    }

    /**
     * Adds a scalar value to every element in this vector.
     *
     * @param scalar the scalar value to add.
     *
     * @return this vector, for operator chaining.
     */
    public JamVector add(double scalar) {
        for (int index = 0; index < length(); ++index)
            set(index, get(index) + scalar);

        return this;
    }

    /**
     * Updates this vector with the linear combination {@code a * this + b * y}.
     *
     * @param a the scalar factor to apply to this vector.
     * @param b the scalar factor to apply to the input vector.
     * @param y the other vector in the linear combination.
     *
     * @return this vector, for operator chaining.
     *
     * @throws RuntimeException unless the input vector has the same length as this.
     */
    public JamVector combineInPlace(double a, double b, JamVector y) {
        validateOperand(y);

        for (int index = 0; index < length(); ++index)
            set(index, a * this.get(index) + b * y.get(index));

        return this;
    }

    /**
     * Creates a deep copy of this vector.
     * @return a deep copy of this vector.
     */
    public JamVector copy() {
        return new JamVector(impl.copy());
    }

    /**
     * Assigns the value of the element at a particular position.
     *
     * @param index the zero-based element index.
     * @param value the value to assign.
     *
     * @throws IndexOutOfBoundsException unless the index is valid.
     */
    public void set(int index, double value) {
        impl = impl.set(index, value);
    }

    /**
     * Subtracts a scalar value from every element in this vector.
     *
     * @param scalar the scalar value to subtract.
     *
     * @return this vector, for operator chaining.
     */
    public JamVector subtract(double scalar) {
        return add(-scalar);
    }

    @Override
    public double get(int index) {
        return impl.get(index);
    }

    @Override
    public int length() {
        return impl.length();
    }

    @Override
    public double[] toArray() {
        return impl.toArray();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VectorView) && equalsVector((VectorView) obj);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("JamVectors may not be hash keys.");
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
