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
package com.tipplerow.jam.vector;

import org.apache.commons.math3.linear.RealVector;

/**
 * @author Scott Shaffer
 */
abstract class ApacheImpl extends VectorImpl {
    protected final RealVector vector;

    ApacheImpl(RealVector vector) {
        this.vector = vector;
    }

    @Override
    public double get(int index) {
        return vector.getEntry(index);
    }

    @Override
    public int length() {
        return vector.getDimension();
    }

    @Override
    public double[] toArray() {
        return vector.toArray();
    }

    @Override
    VectorImpl set(int index, double value) {
        vector.setEntry(index, value);
        return this;
    }
}
