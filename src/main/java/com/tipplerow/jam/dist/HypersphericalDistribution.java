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

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.math.JamRandom;
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.matrix.MatrixView;
import com.tipplerow.jam.stat.Stat;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents points distributed uniformly on the surface of a hypersphere.
 *
 * <p>The algorithm is described <a href="http://mathworld.wolfram.com/HyperspherePointPicking.html">here</a>.
 *
 * @author Scott Shaffer
 */
public final class HypersphericalDistribution extends AbstractMultivariateDistribution {
    private final double radius;
    private final VectorView center;
    private final MatrixView covar;

    /**
     * Creates a new hyperspherical distribution with a specified
     * radius centered at the origin of the Euclidean coordinate
     * system.
     *
     * @param dim the dimensionality of the sample space.
     *
     * @param radius the radius of the hypersphere on which the points
     * are distributed.
     *
     * @throws IllegalArgumentException unless the radius is positive
     * and the dimensionality is two or greater.
     */
    public HypersphericalDistribution(int dim, double radius) {
        this(radius, JamVector.dense(dim));
    }

    /**
     * Creates a new hyperspherical distribution with a specified
     * radius and center.
     *
     * @param radius the radius of the hypersphere on which the points
     * are distributed.
     *
     * @param center the center of the hypersphere on which the points
     * are distributed.
     *
     * @throws IllegalArgumentException unless the radius is positive
     * and the length of the center vector is two or greater.
     */
    public HypersphericalDistribution(double radius, VectorView center) {
        validateRadius(radius);
        validateCenter(center);

        this.radius = radius;
        this.center = JamVector.copyOf(center); // Defensive copy
        this.covar  = computeCovar(center.length(), radius);
    }

    private static void validateRadius(double radius) {
        if (radius <= 0.0)
            throw new IllegalArgumentException("Non-positive radius.");
    }

    private static void validateCenter(VectorView center) {
        if (center.length() < 2)
            throw new IllegalArgumentException("Must have at least two dimensions.");
    }

    private static JamMatrix computeCovar(int dim, double radius) {
        //
        // The trace of the radius of gyration matrix should be equal
        // to the square of the radius, so each diagonal element must
        // be the following:
        //
        double rgdiag = radius * radius / dim;
        return JamMatrix.diag(JamVector.rep(rgdiag, dim));
    }

    /**
     * Returns the radius of the hypersphere on which the points are
     * distributed.
     *
     * @return the radius of the hypersphere on which the points are
     * distributed.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the center of the hypersphere on which the points are
     * distributed.
     *
     * @return the center of the hypersphere on which the points are
     * distributed.
     */
    public VectorView getCenter() {
        return center;
    }

    @Override public int dim() {
        return center.length();
    }

    @Override public VectorView mean() {
        return center;
    }

    @Override public MatrixView covar() {
        return covar;
    }

    @Override public double pdf(VectorView x) {
        //
        // Not numerically friendly, but what else can we do...
        //
        double norm = Stat.norm2(x.minus(center));

        if (DoubleComparator.DEFAULT.EQ(norm, radius))
            return Double.POSITIVE_INFINITY;
        else
            return 0.0;
    }

    /**
     * Generates a random point on a hypersphere centered at the
     * origin.
     *
     * @param source the random number source.
     *
     * @return a vector of length {@code dim} representing a point
     * selected at random from a hypersphere with the given radius
     * centered at the origin.
     *
     * @throws IllegalArgumentException unless the dimensionality and
     * radius are positive.
     */
    @Override public JamVector sample(JamRandom source) {
        JamVector result = JamVector.dense(dim());

        for (int index = 0; index < dim(); index++)
            result.set(index, source.nextGaussian(0.0, 1.0));

        return result.unitize().multiply(radius).add(center);
    }
}
