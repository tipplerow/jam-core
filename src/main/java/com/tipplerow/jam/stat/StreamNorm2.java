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

import com.tipplerow.jam.math.DoubleUtil;

import java.util.stream.DoubleStream;

/**
 * Computes the square root of the sum of the squares of the values in a
 * finite data stream: the 2-norm.
 *
 * @author Scott Shaffer
 */
final class StreamNorm2 extends StreamStat {
    private StreamNorm2() {}

    static Stat INSTANCE = new StreamNorm2();

    @Override
    public double computeFinite(DoubleStream data) {
        return Math.sqrt(data.map(DoubleUtil::square).sum());
    }
}
