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

/**
 * Represents the principal moments of a gyration tensor.
 *
 * @author Scott Shaffer
 */
public final class PrincipalMoments {
    /**
     * The first (smallest) principal moment.
     */
    public final double pmX;

    /**
     * The second (middle) principal moment.
     */
    public final double pmY;

    /**
     * The third (greatest) principal moment.
     */
    public final double pmZ;

    private PrincipalMoments(double pmX, double pmY, double pmZ) {
        validate(pmX, pmY, pmZ);

        this.pmX = pmX;
        this.pmY = pmY;
        this.pmZ = pmZ;
    }

    /**
     * Returns a principal moment with fixed dimensions.
     *
     * @param pmX the first (smallest) principal moment.
     * @param pmY the second (middle) principal moment.
     * @param pmZ the third (greatest) principal moment.
     *
     * @return the principle moment with the specified dimensions.
     *
     * @throws IllegalArgumentException unless the input arguments are
     * valid principal moments: {@code 0.0 <= pmX <= pmY <= pmZ}.
     */
    public static PrincipalMoments of(double pmX, double pmY, double pmZ) {
        return new PrincipalMoments(pmX, pmY, pmZ);
    }

    /**
     * Validates principal moment components.
     *
     * @param pmX the first (smallest) principal moment.
     * @param pmY the second (middle) principal moment.
     * @param pmZ the third (greatest) principal moment.
     *
     * @throws IllegalArgumentException unless the input arguments are
     * valid principal moments: {@code 0.0 <= pmX <= pmY <= pmZ}.
     */
    public static void validate(double pmX, double pmY, double pmZ) {
        DoubleComparator comparator = DoubleComparator.DEFAULT;

        if (comparator.isNegative(pmX))
            throw new IllegalArgumentException("First principal moment is negative.");

        if (comparator.LT(pmY, pmX))
            throw new IllegalArgumentException("Second principal moment is less than the first.");

        if (comparator.LT(pmZ, pmY))
            throw new IllegalArgumentException("Third principal moment is less than the second.");
    }

    /**
     * Returns the asphericity of these principal moments.
     *
     * @return the asphericity of these principal moments.
     */
    public double asphericity() {
        return pmZ - 0.5 * (pmX + pmY);
    }

    /**
     * Returns the acylindricity of these principal moments.
     *
     * @return the acylindricity of these principal moments.
     */
    public double acylindricity() {
        return pmY - pmX;
    }

    /**
     * Returns the anisotropy of these principal moments.
     *
     * @return the anisotropy of these principal moments.
     */
    public double anisotropy() {
        double x2 = pmX;
        double y2 = pmY;
        double z2 = pmZ;

        double x4 = x2 * x2;
        double y4 = y2 * y2;
        double z4 = z2 * z2;

        return 1.5 * (x4 + y4 + z4) / Math.pow(x2 + y2 + z2, 2) - 0.5;
    }

    /**
     * Returns the scalar radius of gyration for these principal
     * moments.
     *
     * @return the scalar radius of gyration.
     */
    public double RG() {
        return Math.sqrt(pmX + pmY + pmZ);
    }
}
