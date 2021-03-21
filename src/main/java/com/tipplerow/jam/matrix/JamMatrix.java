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

import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a two-dimensional matrix, provides basic operations from
 * linear algebra, and optimizes the underlying physical storage.
 *
 * @author Scott Shaffer
 */
public final class JamMatrix implements MatrixView {
    private MatrixImpl impl;

    private JamMatrix(MatrixImpl impl) {
        this.impl = impl;
    }

    /**
     * Creates a new matrix and populates its elements in row-major order.
     *
     * @param nrow     the number of rows.
     * @param ncol     the number of columns.
     * @param elements the elements in row-major order.
     *
     * @return the new matrix.
     *
     * @throws IllegalArgumentException unless the number of elements
     * supplied is equal to the number required ({@code nrow * ncol}).
     */
    public static JamMatrix byrow(int nrow, int ncol, double... elements) {
        if (elements.length != nrow * ncol)
            throw new IllegalArgumentException("Inconsistent dimensions.");

        JamMatrix result = dense(nrow, ncol);

        for (int i = 0; i < nrow; i++)
            for (int j = 0; j < ncol; j++)
                result.set(i, j, elements[j + i * ncol]);

        return result;
    }

    /**
     * Creates a new matrix by stacking column vectors of equal length from
     * left to right.
     *
     * @param cols the columns to stack.
     *
     * @return a new matrix where column {@code k} is equal to {@code cols[k]}.
     *
     * @throws IllegalArgumentException unless there is at least one column
     * and all columns have the same (positive) length.
     */
    public static JamMatrix cbind(VectorView... cols) {
        if (cols.length < 1)
            throw new IllegalArgumentException("At least one column is required.");

        if (cols[0].length() < 1)
            throw new IllegalArgumentException("Columns must have positive length.");

        int nrow = cols[0].length();
        int ncol = cols.length;

        JamMatrix result = dense(nrow, ncol);

        for (int col = 0; col < ncol; col++)
            result.setColumn(col, cols[col]);

        return result;
    }

    /**
     * Creates a new matrix by stacking column vectors of equal length from
     * left to right.
     *
     * @param cols the columns to stack.
     *
     * @return a new matrix where column {@code k} is equal to the {@code k}th
     * vector returned by the collection iterator.
     *
     * @throws IllegalArgumentException unless there is at least one column
     * and all columns have the same (positive) length.
     */
    public static JamMatrix cbind(Collection<VectorView> cols) {
        return cbind(cols.toArray(new VectorView[0]));
    }

    /**
     * Creates a new dense matrix by copying elements from a bare array.
     * Subsequent changes to the array will not be reflected in the new
     * matrix, and subsequent changes to the returned matrix will not be
     * reflected in the array.
     *
     * @param array the array to copy.
     *
     * @return a new dense matrix with elements initialized to the values
     * of the input array.
     */
    public static JamMatrix copyOf(double[][] array) {
        return new JamMatrix(new DenseApacheImpl(array, true));
    }

    /**
     * Creates a new matrix with a fixed shape and dense physical storage.
     *
     * @param nrow the number of matrix rows.
     * @param ncol the number of matrix columns.
     *
     * @return a new matrix with the specified shape, dense storage, and
     * all elements initialized to zero.
     *
     * @throws RuntimeException if either dimension is negative.
     */
    public static JamMatrix dense(int nrow, int ncol) {
        return new JamMatrix(new DenseApacheImpl(nrow, ncol));
    }

    /**
     * Creates a new diagonal matrix with a fixed shape.
     *
     * @param values the values of the diagonal elements.
     *
     * @return a new diagonal matrix with shape corresponding to the input
     * vector of diagonal elements.
     */
    public static JamMatrix diag(VectorView values) {
        return new JamMatrix(new DiagonalApacheImpl(values.toArray(), false));
    }

    /**
     * Creates the dyadic (outer or tensor) product of a vector with itself.
     *
     * @param vector any vector.
     *
     * @return the dyadic (outer or tensor) product of the input vector with itself.
     */
    public static JamMatrix dyad(VectorView vector) {
        return dyad(vector, vector);
    }

    /**
     * Creates the dyadic (outer or tensor) product of two vectors.
     *
     * @param x the first vector in the dyadic product.
     * @param y the second vector in the dyadic product.
     *
     * @return the dyadic (outer or tensor) product of the input vectors:
     * a matrix {@code A} with {@code N} rows and columns (where {@code N}
     * is the vector length) having {@code A[i, j] = x[i] * y[j]}.
     *
     * @throws IllegalArgumentException unless the input vectors have the
     * same length.
     */
    public static JamMatrix dyad(VectorView x, VectorView y) {
        int D = x.length();

        if (y.length() != D)
            throw new IllegalArgumentException("Vector length mismatch.");

        JamMatrix result = dense(D, D);

        for (int row = 0; row < D; row++)
            for (int col = 0; col < D; col++)
                result.set(row, col, x.get(row) * y.get(col));

        return result;
    }

    /**
     * Creates a new identity matrix with a given dimension.
     *
     * @param dimension the dimension of the identity matrix.
     *
     * @return a new identity matrix with the specified dimension.
     */
    public static JamMatrix identity(int dimension) {
        return diag(JamVector.ones(dimension));
    }

    /**
     * Creates a new matrix by stacking row vectors of equal length from
     * top to bottom.
     *
     * @param rows the rows to stack.
     *
     * @return a new matrix where row {@code k} is equal to {@code rows[k]}.
     *
     * @throws IllegalArgumentException unless there is at least one row
     * and all rows have the same (positive) length.
     */
    public static JamMatrix rbind(VectorView... rows) {
        if (rows.length < 1)
            throw new IllegalArgumentException("At least one row is required.");

        if (rows[0].length() < 1)
            throw new IllegalArgumentException("Rows must have positive length.");

        int nrow = rows.length;
        int ncol = rows[0].length();

        JamMatrix result = JamMatrix.dense(nrow, ncol);

        for (int row = 0; row < nrow; row++)
            result.setRow(row, rows[row]);

        return result;
    }

    /**
     * Creates a new matrix by stacking row vectors of equal length from
     * top to bottom.
     *
     * @param rows the rows to stack.
     *
     * @return a new matrix where row {@code k} is equal to the {@code k}th
     * vector returned by the collection iterator.
     *
     * @throws IllegalArgumentException unless there is at least one row
     * and all rows have the same (positive) length.
     */
    public static JamMatrix rbind(Collection<? extends VectorView> rows) {
        return rbind(rows.toArray(new VectorView[0]));
    }

    /**
     * Creates a new matrix using an existing array as the underlying storage.
     * Subsequent changes to the bare array will be reflected in the matrix,
     * and subsequent changes to the matrix will be reflected in the array.
     *
     * @param array the bare array to wrap in a JamMatrix.
     *
     * @return a new matrix using the specified array as the underlying storage.
     */
    public static JamMatrix wrap(double[][] array) {
        return new JamMatrix(new DenseApacheImpl(array, false));
    }

    /**
     * Adds a scalar value to every element in this matrix (in place).
     *
     * @param scalar the scalar value to add.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix add(double scalar) {
        for (int row = 0; row < nrow(); ++row)
            for (int col = 0; col < ncol(); ++col)
                add(row, col, scalar);

        return this;
    }

    /**
     * Adds a scalar value to an element in this matrix (in place).
     *
     * @param row    the row index of the element to alter.
     * @param col    the column index of the element to alter.
     * @param scalar the scalar value to add.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix add(int row, int col, double scalar) {
        set(row, col, get(row, col) + scalar);
        return this;
    }

    /**
     * Adds another matrix to this matrix (in place).
     *
     * @param operand the matrix to add.
     *
     * @return this matrix, for operator chaining.
     *
     * @throws RuntimeException unless the operand has the same length
     * as this matrix.
     */
    public JamMatrix add(MatrixView operand) {
        return daxpy(1.0, operand);
    }

    /**
     * Updates this matrix in place with the sum of this matrix and the
     * scalar multiple of another matrix {@code (this + scalar * that)}.
     *
     * <p>The method name comes from the BLAS library function which
     * performs the same operation.</p>
     *
     * @param scalar the scalar factor to multiply the input matrix.
     *
     * @param that the matrix operand to add to this matrix.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix daxpy(double scalar, MatrixView that) {
        impl = impl.daxpy(scalar, that);
        return this;
    }

    /**
     * Divides every element in this matrix by a scalar factor (in place).
     *
     * @param scalar the scalar divisor.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix divide(double scalar) {
        return multiply(1.0 / scalar);
    }

    /**
     * Multiplies every element in this matrix by a scalar factor (in place).
     *
     * @param scalar the scalar multiplier.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix multiply(double scalar) {
        for (int row = 0; row < nrow(); ++row)
            for (int col = 0; col < ncol(); ++col)
                multiply(row, col, scalar);

        return this;
    }

    /**
     * Multiplies an element in this matrix by a scalar factor (in place).
     *
     * @param row    the row index of the element to alter.
     * @param col    the column index of the element to alter.
     * @param scalar the scalar multiplier.
     *
     * @return this matrix, for operator chaining.
     */
    public JamMatrix multiply(int row, int col, double scalar) {
        set(row, col, get(row, col) * scalar);
        return this;
    }

    /**
     * Assigns the value of the element at a particular position.
     *
     * @param row   the zero-based row index.
     * @param col   the zero-based column index.
     * @param value the value to assign.
     *
     * @throws IndexOutOfBoundsException unless the index is valid.
     */
    public void set(int row, int col, double value) {
        impl = impl.set(row, col, value);
    }

    /**
     * Assigns values to a column in this matrix.
     *
     * @param colIndex  the index of the column to assign.
     * @param colValues the column vector to assign.
     *
     * @throws IllegalArgumentException unless the length of the
     * column vector matches the number of rows in this matrix.
     */
    public void setColumn(int colIndex, VectorView colValues) {
        if (colValues.length() != nrow())
            throw new IllegalArgumentException("Column vector length mismatch.");

        for (int rowIndex = 0; rowIndex < nrow(); rowIndex++)
            set(rowIndex, colIndex, colValues.get(rowIndex));
    }

    /**
     * Assigns values to a row in this matrix.
     *
     * @param rowIndex  the index of the row to assign.
     * @param rowValues the row vector to assign.
     *
     * @throws IllegalArgumentException unless the length of the
     * row vector matches the number of columns in this matrix.
     */
    public void setRow(int rowIndex, VectorView rowValues) {
        if (rowValues.length() != ncol())
            throw new IllegalArgumentException("Row vector length mismatch.");

        for (int colIndex = 0; colIndex < ncol(); colIndex++)
            set(rowIndex, colIndex, rowValues.get(colIndex));
    }

    @Override
    public double get(int row, int col) {
        return impl.get(row, col);
    }

    @Override
    public int ncol() {
        return impl.ncol();
    }

    @Override
    public int nrow() {
        return impl.nrow();
    }

    @Override
    public JamVector times(VectorView vector) {
        return impl.times(vector);
    }

    @Override
    public double[][] toArray() {
        return impl.toArray();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MatrixView) && equalsMatrix((MatrixView) obj);
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("JamMatrix objects may not be hash keys.");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        if (nrow() > 0)
            builder.append(Arrays.toString(toArray()));

        for (int row = 1; row < nrow(); ++row) {
            builder.append(", ");
            builder.append(Arrays.toString(toArray()));
        }

        builder.append("}");

        return builder.toString();
    }
}
