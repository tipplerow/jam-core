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
package com.tipplerow.jam.matrix;

import com.tipplerow.jam.math.DoubleComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * @author Scott Shaffer
 */
public interface MatrixView {
    /**
     * Returns the number of rows in this matrix.
     *
     * @return the number of rows in this matrix.
     */
    int nrow();

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the number of columns in this matrix.
     */
    int ncol();

    /**
     * Returns the value in the element at a particular position.
     *
     * @param row the zero-based row index.
     * @param col the zero-based column index.
     *
     * @return the value in the element at the specified position.
     *
     * @throws IndexOutOfBoundsException unless the indexes are valid.
     */
    double get(int row, int col);

    /**
     * Performs an equality test between this matrix and a bare array
     * while allowing for a finite tolerance.
     *
     * @param array the array to test for equality.
     *
     * @return {@code true} the input array has the same shape as this
     * matrix and every corresponding element is equal according to the
     * default comparator.
     */
    default boolean equalsArray(double[][] array) {
        return equalsArray(array, DoubleComparator.DEFAULT);
    }

    /**
     * Performs an equality test between this matrix and a bare array
     * while allowing for a finite tolerance.
     *
     * @param array      the array to test for equality.
     * @param comparator the element comparator.
     *
     * @return {@code true} the input array has the same shape as this
     * matrix and every corresponding element is equal according to the
     * specified comparator.
     */
    default boolean equalsArray(double[][] array, DoubleComparator comparator) {
        return equalsMatrix(of(array), comparator);
    }

    /**
     * Performs an equality test between this matrix and another matrix
     * while allowing for a finite tolerance.
     *
     * @param that the matrix to test for equality.
     *
     * @return {@code true} the input matrix has the same shape as this
     * matrix and every corresponding element is equal according to the
     * default comparator.
     */
    default boolean equalsMatrix(MatrixView that) {
        return equalsMatrix(that, DoubleComparator.DEFAULT);
    }

    /**
     * Performs an equality test between this matrix and another matrix
     * while allowing for a finite tolerance.
     *
     * @param that       the matrix to test for equality.
     * @param comparator the element comparator.
     *
     * @return {@code true} the input matrix has the same shape as this
     * matrix and every corresponding element is equal according to the
     * specified comparator.
     */
    default boolean equalsMatrix(MatrixView that, DoubleComparator comparator) {
        if (this.nrow() != that.nrow())
            return false;

        if (this.ncol() != that.ncol())
            return false;

        for (int row = 0; row < nrow(); ++row)
            for (int col = 0; col < ncol(); ++col)
                if (!comparator.equals(this.get(row, col), that.get(row, col)))
                    return false;

        return true;
    }

    /**
     * Returns the number of elements in this matrix.
     * @return the number of elements in this matrix.
     */
    default int size() {
        return nrow() * ncol();
    }

    /**
     * Returns a sequential stream of the values contained in this matrix
     * (in row-major order).
     *
     * @return a sequential stream of the values contained in this matrix.
     */
    default DoubleStream streamValues() {
        return streamElements().mapToDouble(MatrixElement::getValue);
    }

    /**
     * Returns a stream of the elements in this matrix (with no guarantee
     * on the order).
     *
     * @return a stream of the elements in this matrix.
     */
    default Stream<MatrixElement> streamElements() {
        List<MatrixElement> elements = new ArrayList<>(size());

        for (int row = 0; row < nrow(); ++row)
            for (int col = 0; col < ncol(); ++col)
                elements.add(MatrixElement.of(row, col, get(row, col)));

        return elements.stream();
    }

    /**
     * Returns a stream of the <em>non-zero</em> elements in this matrix
     * (with no guarantee on the order).
     *
     * @return a stream of the <em>non-zero</em> elements in this matrix.
     */
    default Stream<MatrixElement> streamNonZero() {
        return streamElements().filter(MatrixElement::isNonZero);
    }

    /**
     * Returns the elements of this matrix in new bare array.
     *
     * @return the elements of this matrix in new bare array.
     */
    default double[][] toArray() {
        double[][] rows = new double[nrow()][];

        for (int rowIndex = 0; rowIndex < nrow(); ++rowIndex) {
            double[] rowArray = new double[ncol()];

            for (int colIndex = 0; colIndex < ncol(); ++colIndex)
                rowArray[colIndex] = get(rowIndex, colIndex);

            rows[rowIndex] = rowArray;
        }

        return rows;
    }

    /**
     * Wraps a bare two-dimensional array in a MatrixView.
     *
     * @param array the array to wrap.
     *
     * @return a MatrixView of the specified array.
     */
    static MatrixView of(double[][] array) {
        return ArrayWrapper.wrap(array);
    }
}
