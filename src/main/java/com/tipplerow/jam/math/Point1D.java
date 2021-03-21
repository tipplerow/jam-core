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

import java.text.DecimalFormat;

/**
 * Represents an immutable point on a line.
 *
 * @author Scott Shaffer
 */
public final class Point1D implements Point {
    /** The immutable x-coordinate. */
    public final double x;

    private Point1D(double x) {
        this.x = x;
    }

    /**
     * The point at the origin.
     */
    public static final Point1D ORIGIN = at(0.0);

    /**
     * Returns the one-dimensional point at a fixed location.
     *
     * @param x the x-coordinate.
     *
     * @return the one-dimensional point at {@code (x)}.
     */
    public static Point1D at(double x) {
        return new Point1D(x);
    }

    @Override public double coord(int dim) {
        if (dim == 0)
            return x;
        else
            throw new IndexOutOfBoundsException();
    }

    @Override public int dimensionality() {
        return 1;
    }

    @Override public double distance(Point that) {
        return distance1D((Point1D) that);
    }

    private double distance1D(Point1D that) {
        return Math.abs(this.x - that.x);
    }

    @Override public String formatCSV(DecimalFormat format) {
        return format.format(x);
    }

    @Override public double x() {
        return x;
    }

    @Override public double[] toArray() {
        return new double[] { x };
    }

    @Override public boolean equals(Object that) {
        return (that instanceof Point1D) && equalsPoint((Point1D) that);
    }

    private boolean equalsPoint(Point1D that) {
        return DoubleComparator.DEFAULT.EQ(this.x, that.x);
    }

    @Override public String toString() {
        return "(" + x + ")";
    }
}
