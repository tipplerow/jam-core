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

import java.util.Arrays;

/**
 * Finds the median value in a data vector.
 *
 * @author Scott Shaffer
 */
final class VectorMedian extends VectorStat {
    private VectorMedian() {
    }

    static Stat INSTANCE = new VectorMedian();

    @Override
    public double compute(VectorView data) {
        double[] sorted = data.toArray();
        Arrays.sort(sorted);

        // The Double comparator considers NaN values to be larger
        // than any other value, including Double.POSITIVE_INFINITY,
        // so all NaNs will be at the end of the sorted array. Find
        // them and exclude them from the calculation.
        int upper = sorted.length - 1;

        while (upper >= 0 && Double.isNaN(sorted[upper]))
            upper--;

        if (upper < 0)
            return Double.NaN;

        int mid = upper / 2;

        if (upper % 2 == 0)
            return sorted[mid];
        else
            return 0.5 * (sorted[mid] + sorted[mid + 1]);
    }
}
