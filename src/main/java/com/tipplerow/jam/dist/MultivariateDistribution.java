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
import com.tipplerow.jam.matrix.MatrixView;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents a univariate probability distribution over the real
 * numbers.
 *
 * @author Scott Shaffer
 */
public interface MultivariateDistribution {
    /**
     * Returns the dimensionality of the sample space.
     *
     * @return the dimensionality of the sample space.
     */
    public abstract int dim();

    /**
     * Returns the mean vector for this distribution.
     *
     * @return the mean vector for this distribution.
     */
    public abstract VectorView mean();

    /**
     * Returns the covariance matrix for this distribution.
     *
     * @return the covariance matrix for this distribution.
     */
    public abstract MatrixView covar();

    /**
     * Computes the probability density function at a point.
     *
     * @param x the point at which the PDF is evaluated.
     *
     * @return the probability density at {@code x}.
     *
     * @throws IllegalArgumentException unless the length of the input
     * vector matches the dimensionality of the sample space.
     */
    public abstract double pdf(VectorView x);

    /**
     * Samples from this distribution using the globally shared random
     * number source.
     *
     * @return the next multivariate value from this distribution.
     */
    public abstract JamVector sample();

    /**
     * Samples from this distribution using a specified random number
     * source.
     *
     * @param source the source of uniform random deviates.
     *
     * @return the next multivariate value from this distribution.
     */
    public abstract JamVector sample(JamRandom source);

    /**
     * Samples from this distribution using the globally shared random
     * number source.
     *
     * @param count the number of samples to generate.
     *
     * @return the next {@code count} values from this distribution.
     */
    public abstract JamVector[] sample(int count);

    /**
     * Samples from this distribution using a specified random number
     * source.
     *
     * @param source the source of uniform random deviates.
     *
     * @param count the number of samples to generate.
     *
     * @return the next {@code count} values from this distribution.
     */
    public abstract JamVector[] sample(JamRandom source, int count);
}
