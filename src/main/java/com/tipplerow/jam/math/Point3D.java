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
 * Represents an immutable point in a three-dimensional space.
 *
 * @author Scott Shaffer
 */
public final class Point3D implements Point {
    /** The immutable x-coordinate. */
    public final double x;

    /** The immutable y-coordinate. */
    public final double y;

    /** The immutable z-coordinate. */
    public final double z;

    private Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * The point at the origin.
     */
    public static final Point3D ORIGIN = at(0.0, 0.0, 0.0);

    /**
     * Returns the three-dimensional point at a fixed location.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param z the z-coordinate.
     *
     * @return the three-dimensional point at {@code (x, y, z)}.
     */
    public static Point3D at(double x, double y, double z) {
        return new Point3D(x, y, z);
    }

    @Override public double coord(int dim) {
        switch (dim) {
        case 0:
            return x;

        case 1:
            return y;

        case 2:
            return z;

        default:
            throw new IndexOutOfBoundsException();
        }
    }

    @Override public int dimensionality() {
        return 3;
    }

    @Override public double distance(Point that) {
        return distance3D((Point3D) that);
    }

    private double distance3D(Point3D that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        double dz = this.z - that.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override public String formatCSV(DecimalFormat format) {
        return format.format(x) + "," + format.format(y) + "," + format.format(z);
    }

    @Override public double x() {
        return x;
    }

    @Override public double y() {
        return y;
    }

    @Override public double z() {
        return z;
    }

    @Override public double[] toArray() {
        return new double[] { x, y, z };
    }

    @Override public boolean equals(Object that) {
        return (that instanceof Point3D) && equalsPoint((Point3D) that);
    }

    private boolean equalsPoint(Point3D that) {
        return DoubleComparator.DEFAULT.EQ(this.x, that.x) 
            && DoubleComparator.DEFAULT.EQ(this.y, that.y)
            && DoubleComparator.DEFAULT.EQ(this.z, that.z);
    }

    @Override public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
