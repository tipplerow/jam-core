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
 * Provides an exact calculation of the binomial distribution.
 *
 * @author Scott Shaffer
 */
final class BinomialDistributionExact extends BinomialDistribution {
    BinomialDistributionExact(int trialCount, Probability successProb) {
        super(trialCount, successProb);
    }

    @Override public int sample(JamRandom source) {
        //
        // Brute force explicit sampling...
        //
        int result = 0;

        for (int trial = 0; trial < getTrialCount(); ++trial)
            if (getSuccessProb().accept(source))
                ++result;

        return result;
    }
}
