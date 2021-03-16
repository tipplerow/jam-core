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

import com.tipplerow.jam.math.JamRandom;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

/**
 * Provides a skeleton implementation of the {@code MultivariateDistribution}
 * interface.
 *
 * @author Scott Shaffer
 */
public abstract class AbstractMultivariateDistribution implements MultivariateDistribution {
    /**
     * Validates a point in the sample space (at which the PDF might
     * be evaluated).
     *
     * @param x a point in the sample space.
     *
     * @throws IllegalArgumentException unless the point {@code x} has
     * the correct dimensionality and lies within the sample space of
     * this distribution.
     */
    protected void validatePoint(VectorView x) {
        if (x.length() != dim())
            throw new IllegalArgumentException("Incompatible dimensionality.");
    }

    @Override public JamVector sample() {
        return sample(JamRandom.global());
    }

    @Override public JamVector[] sample(int count) {
        return sample(JamRandom.global(), count);
    }

    @Override public JamVector[] sample(JamRandom source, int count) {
        JamVector[] samples = new JamVector[count];

        for (int index = 0; index < samples.length; index++)
            samples[index] = sample(source);

        return samples;
    }
}
