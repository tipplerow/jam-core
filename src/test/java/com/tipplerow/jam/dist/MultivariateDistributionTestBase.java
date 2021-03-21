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
import com.tipplerow.jam.math.VectorMoment;
import com.tipplerow.jam.vector.JamVector;
import com.tipplerow.jam.testng.NumericTestBase;

import static org.testng.Assert.*;

public abstract class MultivariateDistributionTestBase extends NumericTestBase {
    protected final MultivariateDistribution dist;
    protected final JamVector[] samples;

    public final static int SAMPLE_COUNT = 1000000;

    protected MultivariateDistributionTestBase(MultivariateDistribution dist) {
        this(dist, DEFAULT_TOLERANCE);
    }

    protected MultivariateDistributionTestBase(MultivariateDistribution dist, double tolerance) {
        super(tolerance);
        this.dist = dist;
        this.samples = dist.sample(random(), SAMPLE_COUNT);
    }

    public void momentTest(double tolCM, double tolRG, boolean verbose) {
        VectorMoment moment = VectorMoment.compute(samples);

        if (verbose) {
            //System.out.println(JamVector.minus(moment.getCM(), dist.mean()));
            //System.out.println(JamMatrix.minus(moment.getRG(), dist.covar()));
            System.out.println(moment.getCM());
            System.out.println(dist.mean());
            System.out.println(moment.getRG());
            System.out.println(dist.covar());
        }

        assertTrue(moment.getCM().equalsVector(dist.mean(), new DoubleComparator(tolCM)));
        assertTrue(moment.getRG().equalsMatrix(dist.covar(), new DoubleComparator(tolRG)));
    }
}
