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

/**
 * Represents a probability distribution in which the logarithm of the
 * random variable is normally distributed.
 *
 * @author Scott Shaffer
 */
public final class LogNormalDistribution extends LogDerivedDistribution {
    private final NormalDistribution normal;

    /**
     * Creates a log-normal probability distribution.
     *
     * @param meanLog the mean of the logarithm of the random
     * variable.
     *
     * @param sdevLog the standard deviation of the logarithm of the
     * random variable.
     *
     * @throws IllegalArgumentException unless the standard deviation
     * is positive.
     */
    public LogNormalDistribution(double meanLog, double sdevLog) {
        this.normal = new NormalDistribution(meanLog, sdevLog);
    }

    @Override public double mean() {
        double meanLog = getMeanLog();
        double sdevLog = getStDevLog();
        double varLog  = sdevLog * sdevLog;

        return Math.exp(meanLog + 0.5 * varLog);
    }

    @Override public double variance() {
        double meanLog = getMeanLog();
        double sdevLog = getStDevLog();
        double varLog  = sdevLog * sdevLog;

        return Math.exp(varLog - 1.0) * Math.exp(2.0 * meanLog + varLog);
    }

    /**
     * Returns the mean of the logarithm of this distribution.
     *
     * @return the mean of the logarithm of this distribution.
     */
    public double getMeanLog() {
        return normal.mean();
    }

    /**
     * Returns the standard deviation of the logarithm of this distribution.
     *
     * @return the standard deviation of the logarithm of this distribution.
     */
    public double getStDevLog() {
        return normal.sdev();
    }

    @Override protected RealDistribution getLogDistribution() {
        return normal;
    }
}
