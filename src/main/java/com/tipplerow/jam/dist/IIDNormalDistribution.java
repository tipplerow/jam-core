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
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.matrix.MatrixView;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents an identically and independently distributed
 * multivariate normal (Gaussian) distribution.
 *
 * @author Scott Shaffer
 */
public final class IIDNormalDistribution extends AbstractMultivariateDistribution {
    private final int nvar;
    private final VectorView mean;
    private final MatrixView covar;
    private final NormalDistribution dist;

    /**
     * Creates an identically and independently distributed
     * multivariate normal (Gaussian) distribution.
     *
     * @param nvar the number of IID variables.
     *
     * @param mean the mean of each variable.
     *
     * @param sdev the standard deviation of each variable.
     *
     * @throws IllegalArgumentException unless the number of variables
     * and standard deviation are positive.
     */
    public IIDNormalDistribution(int nvar, double mean, double sdev) {
        validateNumber(nvar);

        this.nvar  = nvar;
        this.dist  = new NormalDistribution(mean, sdev);
        this.mean  = computeMean(nvar, mean);
        this.covar = computeCovar(nvar, sdev);
    }

    private static void validateNumber(int nvar) {
        if (nvar < 1)
            throw new IllegalArgumentException("Non-positive variable count.");
    }

    private static VectorView computeMean(int nvar, double mean) {
        return JamVector.rep(mean, nvar);
    }

    private static MatrixView computeCovar(int nvar, double sdev) {
        return JamMatrix.diag(JamVector.rep(sdev * sdev, nvar));
    }

    @Override public int dim() {
        return nvar;
    }

    @Override public VectorView mean() {
        return mean;
    }

    @Override public MatrixView covar() {
        return covar;
    }

    @Override public double pdf(VectorView x) {
        validatePoint(x);
        double result = 1.0;

        for (int index = 0; index < x.length(); ++index)
            result *= dist.pdf(x.get(index));

        return result;
    }

    @Override public JamVector sample(JamRandom source) {
        JamVector result = JamVector.dense(nvar);

        for (int index = 0; index < nvar; index++)
            result.set(index, dist.sample(source));

        return result;
    }
}
