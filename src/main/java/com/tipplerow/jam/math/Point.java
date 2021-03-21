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

import java.text.DecimalFormat;

/**
 * Represents an immutable point in space.
 *
 * @author Scott Shaffer
 */
public interface Point extends VectorView {
    /**
     * Returns a one-dimensional point at a given location.
     *
     * @param x the {@code x}-coordinate of the location.
     *
     * @return the one-dimensional point at the specified
     * location.
     */
    static Point at(double x) {
        return Point1D.at(x);
    }

    /**
     * Returns a two-dimensional point at a given location.
     *
     * @param x the {@code x}-coordinate of the location.
     *
     * @param y the {@code y}-coordinate of the location.
     *
     * @return the two-dimensional point at the specified
     * location.
     */
    static Point at(double x, double y) {
        return Point2D.at(x, y);
    }

    /**
     * Returns a three-dimensional point at a given location.
     *
     * @param x the {@code x}-coordinate of the location.
     *
     * @param y the {@code y}-coordinate of the location.
     *
     * @param z the {@code z}-coordinate of the location.
     *
     * @return the one-dimensional point at the specified
     * location.
     */
    static Point at(double x, double y, double z) {
        return Point3D.at(x, y, z);
    }

    /**
     * Returns the coordinate value along a given dimension.
     *
     * @param dim the (zero-based) index for the desired dimension.
     *
     * @return the coordinate value along the given dimension.
     *
     * @throws IndexOutOfBoundsException unless the dimension index is
     * valid.
     */
    double coord(int dim);

    /**
     * Returns the dimensionality of this point.
     *
     * @return the dimensionality of this point.
     */
    int dimensionality();

    /**
     * Computes the distance between this point and another.
     *
     * @param that the reference point.
     *
     * @return the distance between this point and the input point.
     *
     * @throws RuntimeException unless this point and the input point
     * have the same dimensionality.
     */
    default double distance(Point that) {
        validateDimensionality(that);
        double dsqr = 0.0;

        for (int dim = 0; dim < dimensionality(); ++dim)
            dsqr += Math.pow(this.coord(dim) - that.coord(dim), 2);

        return Math.sqrt(dsqr);
    }

    /**
     * Returns a comma-separated string containing the coordinates of
     * this point.
     *
     * @param format the decimal formatter for the coordinate values.
     *
     * @return a comma-separated string containing the coordinates of
     * this point.
     */
    String formatCSV(DecimalFormat format);

    /**
     * Returns a comma-separated header string identifying the
     * coordinates of this point.
     *
     * @return a comma-separated header string identifying the
     * coordinates of this point.
     */
    default String headerCSV() {
        return headerCSV(dimensionality());
    }

    /**
     * Returns a comma-separated header string identifying the
     * coordinates of points with a given dimensionality.
     *
     * @param dimensionality the dimensionality of the points of
     * interest.
     *
     * @return a comma-separated header string identifying the
     * coordinates of points with the specified dimensionality.
     */
    static String headerCSV(int dimensionality) {
        switch (dimensionality) {
        case 1:
            return "x";

        case 2:
            return "x,y";

        case 3:
            return "x,y,z";

        default:
            throw new IllegalArgumentException("Unsupported dimensionality.");
        }
    }

    /**
     * Ensures that this point and another point have the same dimensionality.
     *
     * @param that the reference point.
     *
     * @throws RuntimeException unless this point and the input point
     * have the same dimensionality.
     */
    default void validateDimensionality(Point that) {
        if (this.dimensionality() != that.dimensionality())
            throw new IllegalArgumentException("Inconsistent dimensionality.");
    }

    /**
     * Returns the {@code x}-component of this point.
     *
     * @return the {@code x}-component of this point.
     *
     * @throws RuntimeException unless this point has
     * dimension 1 or greater.
     */
    default double x() {
        return coord(0);
    }

    /**
     * Returns the {@code y}-component of this point.
     *
     * @return the {@code y}-component of this point.
     *
     * @throws RuntimeException unless this point has
     * dimension 2 or greater.
     */
    default double y() {
        return coord(1);
    }

    /**
     * Returns the {@code z}-component of this point.
     *
     * @return the {@code z}-component of this point.
     *
     * @throws RuntimeException unless this point has
     * dimension 3 or greater.
     */
    default double z() {
        return coord(2);
    }

    /**
     * Returns a new bare array containing the components of this
     * point.
     *
     * @return a new bare array containing the components of this
     * point.
     */
    double[] toArray();

    @Override
    default int length() {
        return dimensionality();
    }

    @Override
    default double get(int index) {
        return coord(index);
    }
}
