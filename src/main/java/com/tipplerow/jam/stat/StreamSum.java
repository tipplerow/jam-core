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

import java.util.stream.DoubleStream;

/**
 * Computes the sum of the values in a finite data stream.
 *
 * @author Scott Shaffer
 */
final class StreamSum extends StreamStat {
    private StreamSum() {}

    static Stat INSTANCE = new StreamSum();

    @Override
    protected double computeFinite(DoubleStream data) {
        return data.sum();
    }
}

