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

import com.tipplerow.jam.vector.JamVector;

/**
 * Enumerates real probability distributions and provides factory
 * methods for them.
 *
 * @author Scott Shaffer
 */
public enum RealDistributionType {
    /**
     * The Dirac delta distribution.
     */
    DIRAC_DELTA {
        /**
         * Creates a new Dirac delta distribution.
         *
         * @param param the location of the impulse.
         *
         * @return the new Dirac delta distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has length one.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 1)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new DiracDeltaDistribution(param[0]);
        }
    },

    /**
     * The exponential distribution.
     */
    EXPONENTIAL {
        /**
         * Creates a new exponential distribution.
         *
         * @param param the rate parameter for the distribution.
         *
         * @return the new exponential distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has exactly one element.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 1)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new ExponentialDistribution(param[0]);
        }
    },

    /**
     * The Frechet distribution.
     */
    FRECHET {
        /**
         * Creates a new Frechet distribution.
         *
         * @param param the location, scale, and shape parameters in
         * that order.
         *
         * @return the new Frechet distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has length three.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 3)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new FrechetDistribution(param[0], param[1], param[2]);
        }
    },

    /**
     * The generalized extreme value distribution.
     */
    GEV {
        /**
         * Creates a new generalized extreme value distribution.
         *
         * @param param the location, scale, and shape parameters in
         * that order.
         *
         * @return the new generalized extreme value distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has length three.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 3)
                throw new IllegalArgumentException("Invalid parameter set.");

            return GEVDistribution.instance(param[0], param[1], param[2]);
        }
    },

    /**
     * The Gumbel distribution.
     */
    GUMBEL {
        /**
         * Creates a new Gumbel distribution.
         *
         * @param param the location and scale parameters in that
         * order.
         *
         * @return the new Gumbel distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has length two.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new GumbelDistribution(param[0], param[1]);
        }
    },

    /**
     * The log-normal distribution.
     */
    LOG_NORMAL {
        /**
         * Creates a new log-normal distribution.
         *
         * @param param the mean and standard deviation of the
         * logarithm of the distribution, in that order.
         *
         * @return the new log-normal distribution.
         *
         * @throws IllegalArgumentException unless the parameters
         * define a valid log-normal distribution.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new LogNormalDistribution(param[0], param[1]);
        }
    },

    /**
     * The log-uniform distribution.
     */
    LOG_UNIFORM {
        /**
         * Creates a new log-uniform distribution.
         *
         * @param param the logarithmic lower and upper bounds for the
         * distribution, in that order.
         *
         * @return the new log-uniform distribution.
         *
         * @throws IllegalArgumentException unless the parameters
         * define a valid log-uniform distribution.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new LogUniformDistribution(param[0], param[1]);
        }
    },

    /**
     * The normal (Gaussian) distribution.
     */
    NORMAL {
        /**
         * Creates a new normal distribution.
         *
         * @param param the mean and standard deviation of the
         * distribution, in that order.
         *
         * @return the new normal distribution.
         *
         * @throws IllegalArgumentException unless the parameters
         * define a valid normal distribution.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new NormalDistribution(param[0], param[1]);
        }
    },

    /**
     * The reverse Weibull distribution.
     */
    REVERSE_WEIBULL {
        /**
         * Creates a new reverse Weibull distribution.
         *
         * @param param the location, scale, and shape parameters in
         * that order.
         *
         * @return the new reverse Weibull distribution.
         *
         * @throws IllegalArgumentException unless the parameter
         * vector has length three.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 3)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new ReverseWeibullDistribution(param[0], param[1], param[2]);
        }
    },

    /**
     * The uniform real distribution.
     */
    UNIFORM {
        /**
         * Creates a new uniform real distribution.
         *
         * @param param the lower and upper bounds for the
         * distribution, in that order.
         *
         * @return the new uniform distribution.
         *
         * @throws IllegalArgumentException unless the parameters
         * define a valid uniform distribution.
         */
        @Override public RealDistribution create(double... param) {
            if (param.length != 2)
                throw new IllegalArgumentException("Invalid parameter set.");

            return new UniformRealDistribution(param[0], param[1]);
        }
    };

    /**
     * Creates a probability distribution of this type.
     *
     * @param param the parameters required by the constructor for
     * this type of distribution.
     *
     * @return the new distribution instance.
     *
     * @throws IllegalArgumentException unless the parameters are
     * valid for this distribution type.
     */
    public abstract RealDistribution create(double... param);

    /**
     * Creates a probability distribution of this type.
     *
     * @param param parameters required by the constructor for this
     * type of distribution, formatted as a comma-delimited string.
     *
     * @return the new distribution instance.
     *
     * @throws IllegalArgumentException unless the parameters are
     * valid for this distribution type.
     */
    public RealDistribution create(String param) {
        return create(JamVector.parseCSV(param).toNumeric());
    }

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
    public static RealDistribution parse(String def) {
        String[] fields = def.split(";");

        if (fields.length != 2)
            throw new IllegalArgumentException(String.format("Invalid format: [%s].", def));

        String typeField  = fields[0].trim();
        String paramField = fields[1].trim();

        return valueOf(typeField).create(paramField);
    }
}
