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
package com.tipplerow.jam.linalg;

import java.util.ArrayList;

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.math.IntUtil;
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.matrix.MatrixView;
import com.tipplerow.jam.stat.Stat;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Computes and stores the eigenvalue decomposition of a matrix with
 * real eigenvalues.
 *
 * @author Scott Shaffer
 */
public final class JamEigen {
    private final JamVector values;
    private final JamMatrix vectors;

    private JamEigen(JamMatrix matrix) {
        EigenDecomposition decomp = new EigenDecomposition(new BlockRealMatrix(matrix.toArray()));

        if (decomp.hasComplexEigenvalues())
            throw new IllegalArgumentException("Matrix has complex eigenvalues.");

        this.values  = extractValues(decomp);
        this.vectors = extractVectors(decomp);

        assert isValid(matrix, values, vectors);
    }

    private static JamVector extractValues(EigenDecomposition decomp) {
        RealMatrix D = decomp.getD();
        JamVector  v = JamVector.dense(D.getRowDimension());

        for (int k = 0; k < v.length(); k++)
            v.set(k, D.getEntry(k, k));

        return v;
    }

    private static JamMatrix extractVectors(EigenDecomposition decomp) {
        return JamMatrix.wrap(decomp.getV().getData());
    }

    private static boolean isValid(JamMatrix matrix, JamVector values, JamMatrix vectors) {
        //
        // The fundamental definition must be satisfied for all
        // matrices...
        //
        for (int k = 0; k < values.length(); k++) {
            double     value  = values.get(k);
            VectorView vector = vectors.viewColumn(k);

            JamVector actual   = matrix.times(vector);
            JamVector expected = vector.times(value);

            if (!actual.equalsVector(expected))
                return false;
        }

        if (matrix.isSymmetric())
            return isDecreasing(values) && isNormalized(vectors); // Additional validation for symmetric matrices...
        else
            return true;
    }

    private static boolean isDecreasing(JamVector values) {
        //
        // Eigenvalues for symmetric matrices should be non-increasing...
        //
        for (int k = 1; k < values.length(); k++)
            if (DoubleComparator.DEFAULT.LT(values.get(k - 1), values.get(k)))
                return false;

        return true;
    }

    private static boolean isNormalized(JamMatrix vectors) {
        //
        // Eigenvectors for symmetric matrices should have unit norm...
        //
        for (int k = 0; k < vectors.ncol(); k++)
            if (DoubleComparator.DEFAULT.NE(1.0, Stat.norm2(vectors.viewColumn(k))))
                return false;

        return true;
    }

    /**
     * Computes the eigenvalue decomposition of a square matrix with
     * real eigenvalues.
     *
     * @param matrix the target matrix.
     *
     * @return the eigenvalue decomposition of the specified matrix.
     *
     * @throws IllegalArgumentException unless the matrix is square
     * and has all real eigenvalues.
     */
    public static JamEigen compute(JamMatrix matrix) {
        return new JamEigen(matrix);
    }

    /**
     * Computes the determinant of the original matrix as the product
     * of the eigenvalues.
     *
     * <p>Note that the computation is subject to overflow for large
     * matrices; use {@code logdet()} and {@code sgndet()} to ensure
     * finite results.
     *
     * @return the determinant of the original matrix.
     */
    public double det() {
        double result = 1.0;

        for (double value : values)
            result *= value;

        return result;
    }

    /**
     * Computes the logarithm of the absolute value of the determinant
     * (as the sum of the logarithms of the absolute values of the
     * eigenvalues).
     *
     * @return the logarithm of the absolute value of the determinant.
     */
    public double logdet() {
        double result = 0.0;

        for (double value : values)
            result += Math.log(Math.abs(value));

        return result;
    }

    /**
     * Computes the sign of the determinant of the original matrix (as
     * the product of the signs of the eigenvalues).
     *
     * @return the sign of the determinant of the original matrix.
     */
    public double sgndet() {
        double result = 1.0;

        for (double value : values)
            result *= Math.signum(value);

        return result;
    }

    /**
     * Returns the number of <em>unit</em> (exactly equal to 1.0)
     * eigenvalues in this decomposition.
     *
     * @return the number of unit eigenvalues in this decomposition.
     */
    public int countUnitEigenvalues() {
        int result = 0;

        for (double value : values)
            if (isUnitEigenvalue(value))
                ++result;

        return result;
    }

    private boolean isUnitEigenvalue(double value) {
        return DoubleComparator.DEFAULT.EQ(value, 1.0);
    }

    /**
     * Returns the eigenvector corresponding to the unique <em>unit</em>
     * (exactly equal to 1.0) eigenvalue in this decomposition.
     *
     * @param normalize whether to scale the eigenvector so that its
     * elements have unit sum.
     *
     * @return the eigenvector corresponding to the unique unit
     * eigenvalue in this decomposition.
     *
     * @throws IllegalStateException unless this decomposition has a
     * unique unit eigenvalue.
     */
    public JamVector findUniqueUnitEigenvector(boolean normalize) {
        int[] indexes = getUnitEigenvalues();

        if (indexes.length != 1)
            throw new IllegalStateException("Eigenvector decomposition does not have a unique unit eigenvalue.");

        JamVector result = JamVector.copyOf(viewVector(indexes[0]));

        if (normalize)
            result.normalize();

        return result;
    }

    /**
     * Returns the indexes of all <em>unit</em> (exactly equal to 1.0)
     * eigenvalues in this decomposition (an empty array if there are
     * none).
     *
     * @return the indexes of all unit eigenvalues in this decomposition.
     */
    public int[] getUnitEigenvalues() {
        ArrayList<Integer> result = new ArrayList<>();

        for (int index = 0; index < values.length(); index++)
            if (isUnitEigenvalueIndex(index))
                result.add(index);

        return IntUtil.toArray(result);
    }

    private boolean isUnitEigenvalueIndex(int index) {
        return isUnitEigenvalue(values.get(index));
    }

    /**
     * Returns a (real) eigenvalue.
     *
     * <p>Eigenvalues are returned in descending order <em>for
     * symmetric matrices only</em>; the order is arbitrary for
     * non-symmetric matrices.
     *
     * @param index the eigenvalue index (which identifies the
     * corresponding eigenvector).
     *
     * @return the (real) eigenvalue corresponding to the given index.
     *
     * @throws IllegalArgumentException unless the eigenvalue index is
     * valid.
     */
    public double getValue(int index) {
        return values.get(index);
    }

    /**
     * Identifies decompositions with a single <em>unit</em> (exactly
     * equal to 1.0) eigenvalue.
     *
     * @return {@code true} iff this decomposition has exactly one
     * unit eigenvalue.
     */
    public boolean hasUniqueUnitEigenvalue() {
        return countUnitEigenvalues() == 1;
    }

    /**
     * Returns the (real) eigenvalues.
     *
     * <p>Eigenvalues are returned in descending order <em>for
     * symmetric matrices only</em>; the order is arbitrary for
     * non-symmetric matrices.
     *
     * @return the (real) eigenvalues.
     */
    public VectorView viewValues() {
        return values;
    }

    /**
     * Returns a specific eigenvector.
     *
     * @param index the eigenvector index (which identifies the
     * corresponding eigenvalue).
     *
     * @return the specified eigenvector, whose eigenvalue is equal to
     * {@code viewValues.getDouble(k)}.
     *
     * @throws IllegalArgumentException unless the eigenvector index
     * is valid.
     */
    public VectorView viewVector(int index) {
        return vectors.viewColumn(index);
    }

    /**
     * Returns the eigenvectors as matrix columns, ordered from left to
     * right by eigenvalue.
     *
     * @return the eigenvectors as matrix columns, ordered from left to
     * right by eigenvalue: {@code viewVectors.getColumn(k)} has the
     * corresponding eigenvalue {@code viewValues.getDouble(k)}.
     */
    public MatrixView viewVectors() {
        return vectors;
    }
}
