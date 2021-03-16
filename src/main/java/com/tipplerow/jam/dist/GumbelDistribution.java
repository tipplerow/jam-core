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
import com.tipplerow.jam.math.DoubleUtil;

/**
 * Represents the Gumbel distribution, the extreme value distribution
 * of type I.
 *
 * @author Scott Shaffer
 */
public final class GumbelDistribution extends GEVDistribution {
    private static final double LOGLOG2 = Math.log(Math.log(2.0));

    /**
     * Creates a Gumbel distribution
     *
     * @param location the location parameter.
     *
     * @param scale the scale parameter.
     *
     * @throws IllegalArgumentException unless the scale parameter is
     * positive.
     */
    public GumbelDistribution(double location, double scale) {
        super(location, scale, 0.0, DoubleRange.INFINITE);
    }

    @Override protected double kernel(double x) {
        return Math.exp(-(x - location) / scale);
    }

    @Override public double pdf(double x) {
        double tx = kernel(x);
        return tx * Math.exp(-tx) / scale;
    }

    @Override public double quantile(double F) {
        return location - scale * Math.log(-Math.log(F));
    }

    @Override public double mean() {
	return location + scale * DoubleUtil.EULER;
    }

    @Override public double median() {
	return location - scale * LOGLOG2;
    }

    @Override public double variance() {
	double sigma_pi = scale * Math.PI;
	return sigma_pi * sigma_pi / 6.0;
    }
}
