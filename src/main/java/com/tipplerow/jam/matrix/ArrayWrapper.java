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

import lombok.NonNull;

/**
 * Provides an implementation of the JamVector interface using an existing
 * bare array as the underlying storage.
 *
 * @author Scott Shaffer
 */
final class ArrayWrapper implements MatrixView {
    @NonNull
    private final double[][] array;

    private ArrayWrapper(double[][] array) {
        this.array = array;
    }

    /**
     * Creates a new wrapper for an existing bare array.
     *
     * @param array the array to serve as the vector storage.
     *
     * @return a new wrapper for the specified array.
     */
    public static ArrayWrapper wrap(double[][] array) {
        return new ArrayWrapper(array);
    }

    @Override
    public double get(int row, int col) {
        return array[row][col];
    }

    @Override
    public int ncol() {
        return array.length > 0 ? array[0].length : 0;
    }

    @Override
    public int nrow() {
        return array.length;
    }
}
