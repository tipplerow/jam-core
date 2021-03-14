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

/**
 * @author Scott Shaffer
 */
class DenseApacheImpl extends ApacheImpl {
    DenseApacheImpl(Array2DRowRealMatrix matrix) {
        super(matrix);
    }

    DenseApacheImpl(BlockRealMatrix matrix) {
        super(matrix);
    }

    @Override
    boolean isDense() {
        return true;
    }

    @Override
    MatrixImpl set(int row, int col, double value) {
        matrix.setEntry(row, col, value);
        return this;
    }
}
