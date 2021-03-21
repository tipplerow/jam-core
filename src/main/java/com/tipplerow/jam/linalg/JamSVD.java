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

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.matrix.JamMatrix;
import com.tipplerow.jam.matrix.MatrixView;
import com.tipplerow.jam.stat.Stat;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.vector.VectorView;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import lombok.Getter;

/**
 * Computes and stores the singular value decomposition of a real matrix:
 * {@code A = UDV'}, where {@code A} is the original {@code M x N} matrix,
 * {@code U} is an {@code M x N} column-orthogonal matrix, {@code D} is an
 * {@code N x N} diagonal matrix of the singular values, and {@code V} is
 * an {@code N x N} orthogonal matrix.
 *
 * @author Scott Shaffer
 */
public final class JamSVD {
    private final SingularValueDecomposition decomposition;

    /**
     * The original matrix {@code A}.
     */
    @Getter
    private final MatrixView A;

    /**
     * The column-orthogonal matrix {@code U}.
     */
    @Getter(lazy = true)
    private final MatrixView U = JamMatrix.wrap(decomposition.getU().getData());

    /**
     * The transpose column-orthogonal matrix {@code U}.
     */
    @Getter(lazy = true)
    private final MatrixView UT = JamMatrix.wrap(decomposition.getUT().getData());

    /**
     * The orthogonal matrix {@code V}.
     */
    @Getter(lazy = true)
    private final MatrixView V = JamMatrix.wrap(decomposition.getV().getData());

    /**
     * The transpose of the orthogonal matrix {@code V}.
     */
    @Getter(lazy = true)
    private final MatrixView VT = JamMatrix.wrap(decomposition.getVT().getData());

    /**
     * The vector of singular values, in non-increasing order.
     */
    @Getter(lazy = true)
    private final VectorView singularValues = JamVector.wrap(decomposition.getSingularValues());

    private JamSVD(MatrixView matrix) {
        this.A = matrix;
        this.decomposition = new SingularValueDecomposition(new BlockRealMatrix(matrix.toArray()));
    }

    /**
     * Computes the singular value decomposition of a real matrix.
     *
     * @param matrix the matrix on which to operate.
     *
     * @return the singular value decomposition of the specified matrix.
     */
    public static JamSVD compute(MatrixView matrix) {
        return new JamSVD(matrix);
    }

    /**
     * Returns the default singular value threshold for this decomposition: the
     * singular values below this threshold are within the estimated numerical
     * precision of zero and should be treated as if they are exactly zero when
     * they are used to solve linear systems or determine the rank of a matrix.
     *
     * <p>We use the expression implemented in the {@code SVD::solve} method from
     * Section 2.6 of <em>Numerical Recipes, 3rd Edition</em>, which is a function
     * of the dimensions of the linear system, the maximum singular value, and the
     * machine tolerance.
     *
     * @return the default singular value threshold for this decomposition.
     */
    public double getSingularValueThreshold() {
        //
        // See the SVD::solve method in Section 2.6 of Numerical Recipes, 3rd Edition...
        //
        int M = A.nrow();
        int N = A.ncol();
        double wmax = Stat.max(getSingularValues());

        return 0.5 * Math.sqrt(M + N + 1.0) * wmax * DoubleComparator.epsilon();
    }

    /**
     * Computes the inverse of the original matrix.
     *
     * @return the inverse of the original matrix (or a generalized inverse if the
     * matrix is not square).
     */
    public JamMatrix invert() {
        RealMatrix apacheUT   = decomposition.getUT();
        RealMatrix apacheV    = decomposition.getV();
        RealMatrix apacheDinv = getApacheDinv();

        return JamMatrix.wrap(apacheV.multiply(apacheDinv.multiply(apacheUT)).getData());
    }

    private RealMatrix getApacheDinv() {
        double thresh = getSingularValueThreshold();
        VectorView D = getSingularValues();
        RealMatrix Dinv = new DiagonalMatrix(D.length());

        for (int index = 0; index < D.length(); ++index) {
            double value = D.get(index);

            if (value > thresh)
                Dinv.setEntry(index, index, 1.0 / value);
        }

        return Dinv;
    }
}
