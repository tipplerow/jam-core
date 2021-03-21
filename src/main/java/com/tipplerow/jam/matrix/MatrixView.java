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

import com.tipplerow.jam.lang.JamException;
import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

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
     * Identifies square matrices.
     *
     * @return {@code true} iff this is a square matrix.
     */
    default boolean isSquare() {
        return nrow() == ncol();
    }

    /**
     * Identifies symmetric matrices (allowing for the default
     * floating-point tolerance).
     *
     * @return {@code true} iff this is a symmetric matrix.
     */
    default boolean isSymmetric() {
        if (!isSquare())
            return false;

        for (int row = 0; row < nrow(); ++row)
            for (int col = row + 1; col < ncol(); ++col)
                if (!DoubleComparator.DEFAULT.equals(get(row, col), get(col, row)))
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
     * Computes the product of this matrix and a vector and returns the
     * result in a new JamVector.
     *
     * @param vector the (right-hand) vector factor.
     *
     * @return the product of this matrix and the specified vector in a
     * new JamVector.
     *
     * @throws RuntimeException unless the length of the input vector
     * matches the number of columns in this matrix.
     */
    default JamVector times(VectorView vector) {
        if (vector.length() != ncol())
            throw JamException.runtime("Incongruent vector factor.");

        JamVector result = JamVector.dense(nrow());

        for (int row = 0; row < nrow(); ++row)
            result.set(row, viewRow(row).dot(vector));

        return result;
    }

    /**
     * Computes the product of this matrix and another matrix and returns
     * the result in a new JamMatrix.
     *
     * @param matrix the (right-hand) matrix factor.
     *
     * @return the product of this matrix and the specified matrix in a
     * new JamMatrix.
     *
     * @throws RuntimeException unless the number or rows in the input
     * matrix matches the number of columns in this matrix.
     */
    default JamMatrix times(MatrixView matrix) {
        if (matrix.nrow() != ncol())
            throw JamException.runtime("Incongruent matrix factor.");

        JamMatrix result = JamMatrix.dense(nrow(), matrix.ncol());

        for (int row = 0; row < result.nrow(); ++row)
            for (int col = 0; col < result.ncol(); ++col)
                result.set(row, col, this.viewRow(row).dot(matrix.viewColumn(col)));

        return result;
    }

    /**
     * Returns the sum of the diagonal values of this square matrix.
     * @return the sum of the diagonal values of this square matrix.
     * @throws RuntimeException unless this is a square matrix.
     */
    default double trace() {
        if (!isSquare())
            throw JamException.runtime("Matrix trace requires a square matrix.");

        double result = 0.0;

        for (int index = 0; index < nrow(); ++index)
            result += get(index, index);

        return result;
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
     * Ensures that a matrix addend has the same shape as this matrix.
     *
     * @param addend the matrix addend to validate.
     *
     * @throws RuntimeException unless the addend has the same shape as
     * this matrix.
     */
    default void validateAddend(MatrixView addend) {
        if (addend.nrow() != this.nrow() || addend.ncol() != this.ncol())
            throw JamException.runtime("Matrix addend shape mismatch.");
    }

    /**
     * Ensures that this matrix contains a particular column.
     *
     * @param col the column index to validate.
     *
     * @throws RuntimeException unless the specified index is valid.
     */
    default void validateColumn(int col) {
        if (col < 0 || col >= ncol())
            throw JamException.runtime("Invalid column index: [%d].", col);
    }

    /**
     * Ensures that this matrix contains a particular row.
     *
     * @param row the row index to validate.
     *
     * @throws RuntimeException unless the specified index is valid.
     */
    default void validateRow(int row) {
        if (row < 0 || row >= nrow())
            throw JamException.runtime("Invalid row index: [%d].", row);
    }

    /**
     * Returns a VectorView of a column in this matrix.
     *
     * @param col the zero-offset index of the column to view.
     *
     * @return a VectorView of the specified column.
     *
     * @throws RuntimeException unless the column index is valid.
     */
    default VectorView viewColumn(int col) {
        return ColumnView.of(this, col);
    }

    /**
     * Returns a VectorView of the diagonal elements in this square matrix.
     *
     * @return a VectorView of the diagonal elements in this square matrix.
     *
     * @throws RuntimeException unless this is a square matrix.
     */
    default VectorView viewDiagonal() {
        return DiagonalView.of(this);
    }

    /**
     * Returns a VectorView of a row in this matrix.
     *
     * @param row the zero-offset index of the row to view.
     *
     * @return a VectorView of the specified row.
     *
     * @throws RuntimeException unless the row index is valid.
     */
    default VectorView viewRow(int row) {
        return RowView.of(this, row);
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
