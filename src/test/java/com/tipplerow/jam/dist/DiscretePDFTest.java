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

import com.tipplerow.jam.testng.NumericTestBase;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class DiscretePDFTest extends NumericTestBase {
    private final DiscretePDF dist =
        DiscretePDF.compute(1, 2, 3, 5, 2, 3, 5, 3, 5, 5);

    @Test public void testEvaluate() {
        assertDouble(0.0, dist.evaluate(0));
        assertDouble(0.1, dist.evaluate(1));
        assertDouble(0.2, dist.evaluate(2));
        assertDouble(0.3, dist.evaluate(3));
        assertDouble(0.0, dist.evaluate(4));
        assertDouble(0.4, dist.evaluate(5));
        assertDouble(0.0, dist.evaluate(6));
    }

    @Test public void testMean() {
        assertDouble(3.4, dist.mean());
    }

    @Test public void testVariance() {
        assertDouble(2.04, dist.variance());
    }

    @Test public void testSupport() {
        assertEquals(1, dist.support().lower());
        assertEquals(5, dist.support().upper());
    }

    @Test public void testExplicit() {
        DiscretePDF pdf = DiscretePDF.create(10, new double[] { 0.2, 0.3, 0.5 });

        assertEquals(10, pdf.support().lower());
        assertEquals(12, pdf.support().upper());

        assertDouble(0.0, pdf.evaluate(9));
        assertDouble(0.2, pdf.evaluate(10));
        assertDouble(0.3, pdf.evaluate(11));
        assertDouble(0.5, pdf.evaluate(12));
        assertDouble(0.0, pdf.evaluate(13));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testExplicitInvalid() {
        DiscretePDF pdf = DiscretePDF.create(10, new double[] { 0.2, 0.3, 0.5, 0.1 });
    }
}
