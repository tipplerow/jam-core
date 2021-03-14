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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * Captures a DoubleStream into a vector view.
 *
 * @author Scott Shaffer
 */
final class StreamCapture implements VectorView {
    private final double[] array;

    private StreamCapture(double[] array) {
        this.array = array;
    }

    static StreamCapture capture(DoubleStream stream) {
        return new StreamCapture(toArray(stream));
    }

    private static double[] toArray(DoubleStream stream) {
        return toArray(toList(stream));
    }

    private static List<Double> toList(DoubleStream stream) {
        return stream.boxed().collect(Collectors.toList());
    }

    private static double[] toArray(List<Double> list) {
        int index = 0;
        double[] array = new double[list.size()];

        for (double value : list)
            array[index++] = value;

        return array;
    }

    @Override
    public int length() {
        return array.length;
    }

    @Override
    public double get(int index) {
        return array[index];
    }
}
