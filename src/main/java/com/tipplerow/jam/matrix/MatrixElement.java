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

import com.tipplerow.jam.math.DoubleElement;

import lombok.Getter;

/**
 * Encapsulates the indexes and value from a single matrix element.
 *
 * @author Scott Shaffer
 */
public final class MatrixElement extends DoubleElement {
    /**
     * The zero-based row index of this element.
     */
    @Getter
    private final int row;

    /**
     * The zero-based column index of this element.
     */
    @Getter
    private final int col;

    private MatrixElement(int row, int col, double value) {
        super(value);

        this.row = row;
        this.col = col;

        validateIndex(row);
        validateIndex(col);
    }

    /**
     * Creates a new immutable matrix element.
     *
     * @param row   the zero-offset row index.
     * @param col   the zero-offset column index.
     * @param value the element value.
     *
     * @return a new matrix element with the specified contents.
     */
    public static MatrixElement of(int row, int col, double value) {
        return new MatrixElement(row, col, value);
    }
}
