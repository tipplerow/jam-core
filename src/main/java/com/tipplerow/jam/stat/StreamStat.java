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
package com.tipplerow.jam.stat;

import com.tipplerow.jam.vector.VectorView;

import java.util.stream.DoubleStream;

/**
 * Provides a base class for univariate statistics that are conveniently
 * computed from a data stream.
 *
 * @author Scott Shaffer
 */
abstract class StreamStat implements Stat {
    /**
     * Computes the statistic from a stream of data, which has been
     * filtered to remove missing and infinite values.
     *
     * @param data the finite data stream to process.
     *
     * @return the value of this statistic for the specified data.
     */
    protected abstract double computeFinite(DoubleStream data);

    @Override
    public double compute(VectorView data) {
        return compute(data.streamValues());
    }

    @Override
    public double compute(DoubleStream data) {
        return computeFinite(data.filter(Double::isFinite));
    }
}
