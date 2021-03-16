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
package com.tipplerow.jam.dist;

import com.tipplerow.jam.math.DoubleRange;

/**
 * Represents the Frechet distribution, the extreme value distribution
 * of type II.
 *
 * @author Scott Shaffer
 */
public final class FrechetDistribution extends GEVDistribution {
    /**
     * Creates a Frechet distribution
     *
     * @param location the location parameter.
     *
     * @param scale the scale parameter.
     *
     * @param shape the (positive) shape parameter.
     *
     * @throws IllegalArgumentException unless the scale and shape
     * parameters are positive.
     */
    public FrechetDistribution(double location, double scale, double shape) {
        super(location, scale, shape, computeRange(location, scale, shape));

        if (shape <= 0.0)
            throw new IllegalArgumentException("Frechet shape parameter must be positive.");
    }

    private static DoubleRange computeRange(double location, double scale, double shape) {
        double lower = location - scale / shape;
        double upper = Double.POSITIVE_INFINITY;

        return DoubleRange.leftClosed(lower, upper);
    }
}
