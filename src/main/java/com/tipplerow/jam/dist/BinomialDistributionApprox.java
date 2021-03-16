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

import com.tipplerow.jam.math.JamRandom;
import com.tipplerow.jam.math.Probability;

/**
 * Provides an approximate calculation of the binomial distribution.
 *
 * @author Scott Shaffer
 */
final class BinomialDistributionApprox extends BinomialDistribution {
    private final NormalDistribution normal;

    BinomialDistributionApprox(int trialCount, Probability successProb) {
        super(trialCount, successProb);

        double mean  = mean(trialCount, successProb);
        double stdev = Math.sqrt(variance(trialCount, successProb));

        this.normal = new NormalDistribution(mean, stdev);
    }

    @Override public double median() {
        return mean();
    }

    @Override public int sample(JamRandom source) {
        int result = (int) Math.round(normal.sample(source));

        result = Math.max(result, 0);
        result = Math.min(result, getTrialCount());

        return result;
    }
}
