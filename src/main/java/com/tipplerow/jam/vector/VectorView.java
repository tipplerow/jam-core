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

import com.tipplerow.jam.lang.JamException;
import com.tipplerow.jam.math.DoubleComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Defines an interface for immutable numeric vectors that is independent
 * of the underlying physical storage (dense or sparse).
 *
 * @author Scott Shaffer
 */
public interface VectorView {
    /**
     * Returns the length of this vector.
     *
     * @return the length of this vector.
     */
    int length();

    /**
     * Returns the value in the element at a particular position.
     *
     * @param index the zero-based element index.
     *
     * @return the value in the element at the specified position.
     *
     * @throws IndexOutOfBoundsException unless the index is valid.
     */
    double get(int index);

    /**
     * Performs an equality test between this vector and a bare array
     * while allowing for a finite tolerance.
     *
     * @param array the array to test for equality.
     *
     * @return {@code true} the input array has the same length as this
     * vector and every corresponding element is equal according to the
     * default comparator.
     */
    default boolean equalsArray(double[] array) {
        return equalsArray(array, DoubleComparator.DEFAULT);
    }

    /**
     * Performs an equality test between this vector and a bare array
     * while allowing for a finite tolerance.
     *
     * @param array      the array to test for equality.
     * @param comparator the element comparator.
     *
     * @return {@code true} the input array has the same length as this
     * vector and every corresponding element is equal according to the
     * specified comparator.
     */
    default boolean equalsArray(double[] array, DoubleComparator comparator) {
        return equalsVector(of(array), comparator);
    }

    /**
     * Performs an equality test between this vector and another vector
     * while allowing for a finite tolerance.
     *
     * @param that the vector to test for equality.
     *
     * @return {@code true} the input vector has the same length as this
     * vector and every corresponding element is equal according to the
     * default comparator.
     */
    default boolean equalsVector(VectorView that) {
        return equalsVector(that, DoubleComparator.DEFAULT);
    }

    /**
     * Performs an equality test between this vector and another vector
     * while allowing for a finite tolerance.
     *
     * @param that       the vector to test for equality.
     * @param comparator the element comparator.
     *
     * @return {@code true} the input vector has the same length as this
     * vector and every corresponding element is equal according to the
     * specified comparator.
     */
    default boolean equalsVector(VectorView that, DoubleComparator comparator) {
        if (this.length() != that.length())
            return false;

        for (int index = 0; index < length(); ++index)
            if (!comparator.equals(this.get(index), that.get(index)))
                return false;

        return true;
    }

    /**
     * Computes the difference between this vector and a scalar quantity and
     * returns the result in a new vector; this vector is unchanged.
     *
     * @param scalar the scalar quantity to subtract.
     *
     * @return the difference between this vector and the scalar quantity in
     * a new vector.
     */
    default JamVector minus(double scalar) {
        return plus(-scalar);
    }

    /**
     * Computes the difference between this vector and another vector and
     * returns the result in a new vector; this vector is unchanged.
     *
     * @param operand the vector quantity to subtract.
     *
     * @return the difference between this vector and the input vector in
     * a new vector.
     *
     * @throws RuntimeException unless the input vector has the same length
     * as this vector.
     */
    default JamVector minus(VectorView operand) {
        return JamVector.copyOf(this).subtract(operand);
    }

    /**
     * Computes the sum of this vector and a scalar quantity and returns
     * the result in a new vector; this vector is unchanged.
     *
     * @param scalar the scalar quantity to add.
     *
     * @return the sum of this vector and the scalar quantity in a new vector.
     */
    default JamVector plus(double scalar) {
        return JamVector.copyOf(this).add(scalar);
    }

    /**
     * Computes the sum of this vector and another vector and returns the
     * result in a new vector; this vector is unchanged.
     *
     * @param operand the vector quantity to add.
     *
     * @return the sum of this vector and the input vector in a new vector.
     *
     * @throws RuntimeException unless the input vector has the same length
     * as this vector.
     */
    default JamVector plus(VectorView operand) {
        return JamVector.copyOf(this).add(operand);
    }

    /**
     * Returns a sequential stream of the values contained in this vector
     * (ordered by their index).
     *
     * @return a sequential stream of the values contained in this vector.
     */
    default DoubleStream streamValues() {
        return Arrays.stream(toArray());
    }

    /**
     * Returns a stream of the elements in this vector (with no guarantee
     * on the order).
     *
     * @return a stream of the elements in this vector.
     */
    default Stream<VectorElement> streamElements() {
        List<VectorElement> elements = new ArrayList<>(length());

        for (int index = 0; index < length(); ++index)
            elements.add(VectorElement.of(index, get(index)));

        return elements.stream();
    }

    /**
     * Returns a stream of the <em>non-zero</em> elements in this vector
     * (with no guarantee on the order).
     *
     * @return a stream of the <em>non-zero</em> elements in this vector.
     */
    default Stream<VectorElement> streamNonZero() {
        return streamElements().filter(VectorElement::isNonZero);
    }

    /**
     * Returns the elements of this vector in new bare array.
     *
     * @return the elements of this vector in new bare array.
     */
    default double[] toArray() {
        double[] array = new double[length()];

        for (int index = 0; index < length(); ++index)
            array[index] = get(index);

        return array;
    }

    /**
     * Ensures that a vector operand has the same length as this vector.
     *
     * @param operand the vector operand to validate.
     *
     * @throws RuntimeException unless the operand has the same length as
     * this vector.
     */
    default void validateOperand(VectorView operand) {
        if (operand.length() != this.length())
            throw JamException.runtime("Vector length mismatch: [%d] != [%d].", operand.length(), this.length());
    }

    /**
     * Wraps a bare array in a vector view.
     *
     * @param array the array to convert to a VectorView.
     *
     * @return a vector view of the specified bare array.
     */
    static VectorView of(double... array) {
        return ArrayWrapper.wrap(array);
    }

    /**
     * Captures a DoubleStream into a vector view.
     *
     * @param stream the DoubleStream to capture.
     *
     * @return a vector view of the specified stream.
     */
    static VectorView of(DoubleStream stream) {
        return StreamCapture.capture(stream);
    }
}
