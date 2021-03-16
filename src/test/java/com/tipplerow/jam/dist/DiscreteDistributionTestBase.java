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

import org.apache.commons.math3.distribution.IntegerDistribution;

import com.tipplerow.jam.junit.NumericTestBase;
import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.IntUtil;
import com.tipplerow.jam.math.StatSummary;

import org.junit.*;
import static org.junit.Assert.*;

public abstract class DiscreteDistributionTestBase extends NumericTestBase {
    protected DiscreteDistributionTestBase() {
        super();
    }

    protected DiscreteDistributionTestBase(double tolerance) {
        super(tolerance);
    }

    public void cacheTest(DiscreteDistribution dist, double tolerance, boolean verbose) {
        DiscreteDistribution cache = dist.cache();

        double maxErrCDF = 0.0;
        double maxErrPDF = 0.0;

        for (int k : dist.effectiveRange()) {
            maxErrCDF = Math.max(maxErrCDF, Math.abs(cache.cdf(k) - dist.cdf(k)));
            maxErrPDF = Math.max(maxErrPDF, Math.abs(cache.pdf(k) - dist.pdf(k)));
        }

        if (verbose) {
            System.out.println(String.format("Maximum CDF error: %.2g", maxErrCDF));
            System.out.println(String.format("Maximum PDF error: %.2g", maxErrPDF));
        }

        assertTrue(maxErrCDF <= tolerance);
        assertTrue(maxErrPDF <= tolerance);
    }

    public void effectiveRangeTest(DiscreteDistribution distribution, double tolerance, boolean verbose) {
        IntRange effectiveRange = distribution.effectiveRange();
        double   omittedMass    = 1.0 - distribution.cdf(effectiveRange);

        if (verbose) {
            System.out.println(String.format("Effective range: %s", effectiveRange));
            System.out.println(String.format("Omitted mass:    %g", omittedMass));
        }

        assertTrue(omittedMass <= tolerance);
    }

    public void momentTest(DiscreteDistribution distribution, 
                           int sampleCount, 
                           double meanTolerance,
                           double varianceTolerance,
                           boolean verbose) {
	StatSummary summary = StatSummary.compute(distribution.sample(random(), sampleCount));

	double meanError     = Math.abs(distribution.mean()     - summary.getMean());
	double varianceError = Math.abs(distribution.variance() - summary.getVariance());

	if (verbose) {
            System.out.println(summary);
	    System.out.println(String.format("Mean error:     %12.8f", meanError));
	    System.out.println(String.format("Variance error: %12.8f", varianceError));
	}

	assertTrue(meanError     <= meanTolerance);
	assertTrue(varianceError <= varianceTolerance);
    }

    public void sampleTest(int sampleCount, 
                           double meanTolerance,
                           double varianceTolerance,
                           DiscreteDistribution jam,
                           IntegerDistribution apache) {
        int[] sample1 = new int[sampleCount];
        int[] sample2 = new int[sampleCount];

        for (int index = 0; index < sampleCount; index++) {
            sample1[index] = jam.sample(random());
            sample2[index] = apache.sample();
        }

        StatSummary summary1 = StatSummary.compute(IntUtil.toDouble(sample1));
        StatSummary summary2 = StatSummary.compute(IntUtil.toDouble(sample2));

        assertEquals(summary1.getMean(), summary2.getMean(), meanTolerance);
        assertEquals(summary1.getVariance(), summary2.getVariance(), varianceTolerance);
    }
}
