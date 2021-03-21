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
import com.tipplerow.jam.vector.VectorView;

import lombok.NonNull;

/**
 * Provides a shallow view of the diagonal entries in a matrix.
 *
 * @author Scott Shaffer
 */
final class DiagonalView implements VectorView {
    @NonNull
    private final MatrixView matrix;

    private DiagonalView(MatrixView matrix) {
        if (!matrix.isSquare())
            throw JamException.runtime("Matrix is not square.");

        this.matrix = matrix;
    }

    static DiagonalView of(MatrixView matrix) {
        return new DiagonalView(matrix);
    }

    @Override
    public double get(int index) {
        return matrix.get(index, index);
    }

    @Override
    public int length() {
        return matrix.nrow();
    }
}
