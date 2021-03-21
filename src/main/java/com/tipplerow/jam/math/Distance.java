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
package com.tipplerow.jam.math;

import com.tipplerow.jam.vector.VectorView;

/**
 * Implements standard distance metrics.
 *
 * @author Scott Shaffer
 */
public enum Distance {
    /**
     * The Chebyshev (infinity-norm) distance.
     */
    CHEBYSHEV {
        @Override protected double computeChecked(VectorView v1, VectorView v2) {
            double maxabs = 0.0;

            for (int k = 0; k < v1.length(); ++k)
                maxabs = Math.max(maxabs, Math.abs(v1.get(k) - v2.get(k)));

            return maxabs;
        }
    },

    /**
     * The Euclidean (L2-norm) distance.
     */
    EUCLIDEAN {
        @Override protected double computeChecked(VectorView v1, VectorView v2) {
            double sumsqr = 0.0;

            for (int k = 0; k < v1.length(); ++k) {
                double dxk = v1.get(k) - v2.get(k);
                sumsqr += dxk * dxk;
            }

            return Math.sqrt(sumsqr);
        }
    },

    /**
     * The Manhattan (L1-norm) distance.
     */
    MANHATTAN {
        @Override protected double computeChecked(VectorView v1, VectorView v2) {
            double sumabs = 0.0;

            for (int k = 0; k < v1.length(); ++k)
                sumabs += Math.abs(v1.get(k) - v2.get(k));

            return sumabs;
        }
    };

    /**
     * Computes the metric distance between two points.
     *
     * @param v1 the coordinates of the first point.
     * @param v2 the coordinates of the second point.
     *
     * @return the distance between the points.
     *
     * @throws IllegalArgumentException unless the points have the
     * same dimensionality.
     */
    public double compute(VectorView v1, VectorView v2) {
        if (v1.length() != v2.length())
            throw new IllegalArgumentException("Incongruent vectors.");

        return computeChecked(v1, v2);
    }

    /**
     * Computes the metric distance between two points (with assured
     * congruent dimensionality).
     *
     * @param v1 the coordinates of the first point.
     *
     * @param v2 the coordinates of the second point.
     *
     * @return the distance between the points.
     */
    protected abstract double computeChecked(VectorView v1, VectorView v2);
}
