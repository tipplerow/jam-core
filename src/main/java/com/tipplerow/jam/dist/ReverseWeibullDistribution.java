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
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents the reverse Weibull distribution, the extreme value
 * distribution of type III.
 *
 * @author Scott Shaffer
 */
public final class ReverseWeibullDistribution extends GEVDistribution {
    /**
     * Creates a reverse Weibull distribution
     *
     * @param location the location parameter.
     *
     * @param scale the scale parameter.
     *
     * @param shape the (negative) shape parameter.
     *
     * @throws IllegalArgumentException unless the scale parameter is
     * positive and the shape parameter is negative.
     */
    public ReverseWeibullDistribution(double location, double scale, double shape) {
        super(location, scale, shape, computeRange(location, scale, shape));

        if (shape >= 0.0)
            throw new IllegalArgumentException("Reverse Weibull shape parameter must be negative.");
    }

    private static DoubleRange computeRange(double location, double scale, double shape) {
        double lower = Double.NEGATIVE_INFINITY;
        double upper = location - scale / shape;

        return DoubleRange.leftOpen(lower, upper);
    }
}
