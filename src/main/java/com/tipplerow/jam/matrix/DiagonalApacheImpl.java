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
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.OpenMapRealMatrix;

/**
 * @author Scott Shaffer
 */
class DiagonalApacheImpl extends ApacheImpl {
    DiagonalApacheImpl(DiagonalMatrix matrix) {
        super(matrix);
    }

    DiagonalApacheImpl(int length) {
        this(new DiagonalMatrix(length));
    }

    DiagonalApacheImpl(double[] values, boolean copy) {
        this(new DiagonalMatrix(values, copy));
    }

    @Override
    boolean isDense() {
        return false;
    }

    @Override
    MatrixImpl set(int row, int col, double value) {
        if (row == col)
            return setDiagonal(row, value);
        else if (DoubleComparator.DEFAULT.isNonZero(value))
            return setOffDiagonal(row, col, value);
        else
            return this;
    }

    private MatrixImpl setDiagonal(int index, double value) {
        matrix.setEntry(index, index, value);
        return this;
    }

    private MatrixImpl setOffDiagonal(int row, int col, double value) {
        MatrixImpl impl = new SparseApacheImpl(new OpenMapRealMatrix(nrow(), ncol()));

        for (int index = 0; index < nrow(); ++index)
            impl.set(index, index, get(index, index));

        return impl.set(row, col, value);
    }
}
