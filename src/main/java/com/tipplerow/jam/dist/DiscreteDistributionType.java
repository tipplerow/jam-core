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

import com.tipplerow.jam.math.IntUtil;
import com.tipplerow.jam.math.Probability;
import com.tipplerow.jam.regex.RegexUtil;

/**
 * Enumerates discrete probability distributions and provides factory
 * methods for them.
 *
 * @author Scott Shaffer
 */
public enum DiscreteDistributionType {
    BINOMIAL {
        /**
         * Creates a new binomial distribution.
         *
         * @param params (1) the number of trials and (2) the
         * probability that a single trial succeeds.
         *
         * @return the new binomial distribution.
         *
         * @throws IllegalArgumentException unless the parameter array
         * contains a valid number of trials and success probability.
         */
        @Override public DiscreteDistribution create(String... params) {
            if (params.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return BinomialDistribution.create(IntUtil.parseInt(params[0]), Probability.parse(params[1]));
        }
    },

    OCCURRENCE {
        /**
         * Creates a new occurrence distribution.
         *
         * @param params (1) the probability that an event occurs on a
         * single trial, and (2) the number of trials.
         *
         * @return the new occurrence distribution.
         *
         * @throws IllegalArgumentException unless the parameter array
         * contains a valid event probability and non-negative number
         * of trials.
         */
        @Override public DiscreteDistribution create(String... params) {
            if (params.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new OccurrenceDistribution(Probability.parse(params[0]), IntUtil.parseInt(params[1]));
        }
    },

    POISSON {
        /**
         * Creates a new Poisson distribution.
         *
         * @param params the mean value.
         *
         * @return the new Poisson distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * array has length one and contains a positive mean value.
         */
        @Override public DiscreteDistribution create(String... params) {
            if (params.length != 1)
                throw new IllegalArgumentException("Invalid parameter set.");

            return PoissonDistribution.create(Double.parseDouble(params[0]));
        }
    },

    UNIFORM {
        /**
         * Creates a new uniform discrete distribution.
         *
         * @param params (1) the lower bound (inclusive) of the support
         *               range, and (2) the upper bound (exclusive) of
         *               the support range.
         *
         * @return the new uniform discrete distribution.
         *
         * @throws IllegalArgumentException unless the parameter array
         * contains a valid range.
         */
        @Override public DiscreteDistribution create(String... params) {
            if (params.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new UniformDiscreteDistribution(IntUtil.parseInt(params[0]), IntUtil.parseInt(params[1]));
        }
    };

    /**
     * Creates a probability distribution of this type.
     *
     * @param params the string representations of the parameters
     * required by the constructor for this type of distribution.
     *
     * @return the new distribution instance.
     *
     * @throws IllegalArgumentException unless the parameters are
     * valid for this distribution type.
     */
    public abstract DiscreteDistribution create(String... params);

    /**
     * Parses a single string that defines a unique probability
     * distribution.
     *
     * @param def a string defining a probability distribution, given
     * in the format: {@code TYPE; param1, param2, ...}, where {@code
     * TYPE} is the enumerated type code and {@code param1, param2,
     * ...} are the comma-separated parameters required to define a
     * distribution of the specified type.
     *
     * @return the probability distribution defined by the input
     * string.
     *
     * @throws IllegalArgumentException unless the input string
     * defines a valid probability distribution.
     */
    public static DiscreteDistribution parse(String def) {
        String[] fields = RegexUtil.SEMICOLON.split(def);

        if (fields.length != 2)
            throw new IllegalArgumentException(String.format("Invalid format: [%s].", def));

        String typeField  = fields[0].trim();
        String paramField = fields[1].trim();

        return valueOf(typeField).create(RegexUtil.split(RegexUtil.COMMA, paramField));
    }
}
