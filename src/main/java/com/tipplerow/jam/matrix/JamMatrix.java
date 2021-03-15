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

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockRealMatrix;

import java.util.Arrays;

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
        return new JamMatrix(new DenseApacheImpl(new Array2DRowRealMatrix(array, true)));
    }

    /**
     * Creates a new matrix with a fixed length and dense physical storage.
     *
     * @param nrow the number of matrix rows.
     * @param ncol the number of matrix columns.
     *
     * @return a new matrix with the specified length, dense storage, and
     * all elements initialized to zero.
     *
     * @throws RuntimeException if the length is negative.
     */
    public static JamMatrix dense(int nrow, int ncol) {
        return new JamMatrix(new DenseApacheImpl(new BlockRealMatrix(nrow, ncol)));
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
        return new JamMatrix(new DenseApacheImpl(new Array2DRowRealMatrix(array, false)));
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
        return Arrays.toString(toArray());
    }
}
