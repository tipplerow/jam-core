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

import com.tipplerow.jam.vector.VectorView;

import lombok.NonNull;

final class RowView implements VectorView {
    @NonNull
    private final MatrixView matrix;

    private final int rowind;

    private RowView(MatrixView matrix, int rowind) {
        this.matrix = matrix;
        this.rowind = rowind;
        matrix.validateRow(rowind);
    }

    static RowView of(MatrixView matrix, int rowind) {
        return new RowView(matrix, rowind);
    }

    @Override
    public double get(int colind) {
        return matrix.get(rowind, colind);
    }

    @Override
    public int length() {
        return matrix.ncol();
    }
}