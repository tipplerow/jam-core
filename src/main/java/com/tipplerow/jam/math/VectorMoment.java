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

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.tipplerow.jam.collect.JamCollections;
import com.tipplerow.jam.linalg.JamEigen;
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

import lombok.Getter;

/**
 * Computes the generalized center of mass and gyration tensor for
 * a collection of vector coordinates.
 *
 * @author Scott Shaffer
 */
public final class VectorMoment {
    /**
     * The center of mass vector.
     */
    @Getter
    private final JamVector CM;

    /**
     * The radius of gyration tensor.
     */
    @Getter
    private final JamMatrix RG;

    /**
     * The eigenvalue decomposition of the radius of gyration tensor.
     */
    @Getter(lazy = true)
    private final JamEigen eigen = JamEigen.compute(RG);

    /**
     * The principal moments of the radius of gyration tensor.
     */
    @Getter(lazy = true)
    private final PrincipalMoments PM = computePrincipalMoments();

    private VectorMoment(JamVector CM, JamMatrix RG) {
        this.CM = CM;
        this.RG = RG;
    }

    private PrincipalMoments computePrincipalMoments() {
        VectorView eigval = getEigen().viewValues();

        if (eigval.length() != 3)
            throw new IllegalStateException("Principal moments are defined only for 3 x 3 matrices.");

        return PrincipalMoments.of(eigval.get(2), eigval.get(1), eigval.get(0));
    }

    /**
     * Computes the generalized center of mass and gyration tensor for
     * a collection of vector coordinates.
     *
     * @param vectors the vectors on which to operate.
     *
     * @return a {@code VectorMoment} instance containing the computed
     * center of mass and gyration tensor.
     *
     * @throws IllegalArgumentException unless there is at least one
     * vector and all vectors have the same dimensionality.
     */
    public static VectorMoment compute(Collection<? extends VectorView> vectors) {
        if (vectors.isEmpty())
            throw new IllegalArgumentException("At least one vector is required.");

        JamVector CM = computeCM(vectors);
        JamMatrix RG = computeRG(vectors, CM);

        return new VectorMoment(CM, RG);
    }

    private static JamVector computeCM(Collection<? extends VectorView> vectors) {
        int D = JamCollections.peek(vectors).length();
        JamVector CM = JamVector.dense(D);

        for (VectorView vector : vectors)
            CM.add(vector);

        CM.divide(vectors.size());
        return CM;
    }

    private static JamMatrix computeRG(Collection<? extends VectorView> vectors, VectorView CM) {
        int D = CM.length();
        JamMatrix RG = JamMatrix.dense(D, D);

        for (VectorView vector : vectors)
            RG.add(JamMatrix.dyad(centered(vector, CM)));

        return RG.divide(vectors.size());
    }

    private static JamVector centered(VectorView vector, VectorView CM) {
        return vector.minus(CM);
    }

    /**
     * Computes the generalized center of mass and gyration tensor for
     * a collection of objects that can be mapped to vector coordinates.
     *
     * @param <T> the runtime object type.
     *
     * @param collection the collection of objects on which to operate.
     *
     * @param mapper the function that extracts a vector from each
     * object in the collection.
     *
     * @return a {@code VectorMoment} instance containing the computed
     * center of mass and gyration tensor.
     *
     * @throws IllegalArgumentException unless there is at least one
     * object and all vectors have the same dimensionality.
     */
    public static <T> VectorMoment compute(Collection<T> collection, Function<? super T, ? extends VectorView> mapper) {
        return compute(collection.stream().map(mapper).collect(Collectors.toList()));
    }

    /**
     * Computes the generalized center of mass and gyration tensor for
     * a collection of vector coordinates.
     *
     * @param vectors the vectors on which to operate.
     *
     * @return a {@code VectorMoment} instance containing the computed
     * center of mass and gyration tensor.
     *
     * @throws IllegalArgumentException unless there is at least one
     * vector and all vectors have the same dimensionality.
     */
    public static VectorMoment compute(VectorView... vectors) {
        return VectorMoment.compute(List.of(vectors));
    }

    /**
     * Returns the asphericity of the (3D) gyration tensor.
     *
     * @return the asphericity of the (3D) gyration tensor.
     *
     * @throws IllegalStateException unless the gyration tensor has
     * dimensions of {@code 3 x 3}.
     */
    public double asphericity() {
        return getPM().asphericity();
    }

    /**
     * Returns the acylindricity of the (3D) gyration tensor.
     *
     * @return the acylindricity of the (3D) gyration tensor.
     *
     * @throws IllegalStateException unless the gyration tensor has
     * dimensions of {@code 3 x 3}.
     */
    public double acylindricity() {
        return getPM().acylindricity();
    }

    /**
     * Returns the anisotropy of the (3D) gyration tensor.
     *
     * @return the anisotropy of the (3D) gyration tensor.
     *
     * @throws IllegalStateException unless the gyration tensor has
     * dimensions of {@code 3 x 3}.
     */
    public double anisotropy() {
        return getPM().anisotropy();
    }

    /**
     * Computes a <em>normalized radial distance</em> for a point:
     * the Euclidean distance of the point from the center of mass
     * divided by the scalar radius of gyration.
     *
     * @param point the point of interest.
     *
     * @return the normalized radial distance for the given point.
     *
     * @throws IllegalArgumentException unless the dimensions of the
     * point match those of this vector moment.
     */
    public double normR(VectorView point) {
        return Distance.EUCLIDEAN.compute(point, CM) / scalar();
    }

    /**
     * Returns the scalar radius of gyration: the square root of the
     * sum of squares of the diagonal values.
     *
     * @return the scalar radius of gyration.
     */
    public double scalar() {
        //
        // The RG tensor already has the squared values...
        //
        return Math.sqrt(RG.trace());
    }
}
