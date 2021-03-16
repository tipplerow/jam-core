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

import com.tipplerow.jam.junit.NumericTestBase;
import com.tipplerow.jam.math.StatSummary;

import org.junit.*;
import static org.junit.Assert.*;

public abstract class RealDistributionTestBase extends NumericTestBase {
    protected RealDistributionTestBase() {
        super();
    }

    protected RealDistributionTestBase(double tolerance) {
        super(tolerance);
    }

    public void momentTest(RealDistribution distribution, 
                           int sampleCount, 
                           double meanTolerance,
                           double medianTolerance,
                           double varianceTolerance,
                           boolean verbose) {

	StatSummary summary = StatSummary.compute(distribution.sample(random(), sampleCount));

	double meanError     = Math.abs(distribution.mean()     - summary.getMean());
	double medianError   = Math.abs(distribution.median()   - summary.getMedian());
	double varianceError = Math.abs(distribution.variance() - summary.getVariance());

	if (verbose) {
	    System.out.println(String.format("Mean error:     %12.8f", meanError));
	    System.out.println(String.format("Median error:   %12.8f", medianError));
	    System.out.println(String.format("Variance error: %12.8f", varianceError));
	}

	assertTrue(meanError     <= meanTolerance);
	assertTrue(medianError   <= medianTolerance);
	assertTrue(varianceError <= varianceTolerance);
    }
}
