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

import com.tipplerow.jam.math.IntRange;
import com.tipplerow.jam.math.JamRandom;

/**
 * Represents a univariate probability distribution taking integer
 * values over a compact (finite) range with prescribed (explicitly
 * pre-calculated) probabilities.
 *
 * @author Scott Shaffer
 */
public class CompactDiscreteDistribution extends AbstractDiscreteDistribution {
    private final DiscretePDF pdf;
    private final DiscreteCDF cdf;

    /**
     * Creates a new discrete distribution with a pre-computed density
     * function.
     *
     * @param pdf the pre-computed density function.
     */
    public CompactDiscreteDistribution(DiscretePDF pdf) {
        this.pdf = pdf;
        this.cdf = DiscreteCDF.compute(pdf);
    }

    /**
     * Creates a <em>nearly equivalent</em> pre-computed probability
     * distribution over the effective range of another distribution.
     *
     * <p>The CDF and PDF of the cached distribution may differ from
     * those in the true distribution by one part per billion.
     *
     * @param dist the distribution to cache.
     *
     * @return a new distribution with the PDF and CDF pre-computed
     * over the effective range of the input distribution.
     */
    public static CompactDiscreteDistribution cache(DiscreteDistribution dist) {
        return new CompactDiscreteDistribution(DiscretePDF.cache(dist));
    }

    /**
     * Summarizes this distribution in a string suitable for writing
     * to the console or a file.
     *
     * @return a summary description suitable for writing to the
     * console or a file.
     */
    public String display() {
        return super.display(support());
    }

    @Override public double cdf(int k) {
        return cdf.evaluate(k);
    }

    @Override public double cdf(int j, int k) {
        return cdf.evaluate(j, k);
    }

    @Override public double pdf(int k) {
        return pdf.evaluate(k);
    }

    @Override public double mean() {
        return pdf.mean();
    }

    @Override public double median() {
        return cdf.median();
    }

    @Override public double variance() {
	return pdf.variance();
    }

    @Override public int sample(JamRandom source) {
        return cdf.sample(source);
    }

    @Override public IntRange support() {
        return pdf.support();
    }

    @Override public String toString() {
        return display();
    }
}
