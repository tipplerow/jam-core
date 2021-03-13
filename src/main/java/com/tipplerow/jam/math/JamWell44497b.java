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
package com.tipplerow.jam.math;

import org.apache.commons.math3.random.Well44497b;

final class JamWell44497b extends JamRandom {
    private final Well44497b random;

    // Number of iterations for the "warmup" phase...
    private static final int WARMUP_PERIOD = 100000;

    JamWell44497b(long seed) {
        this.random = new Well44497b(seed);
        warmup();
    }

    private void warmup() {
	for (int k = 0; k < WARMUP_PERIOD; k++)
	    nextInt();
    }

    @Override public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override public void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }

    @Override public double nextDouble() {
        return random.nextDouble();
    }

    @Override public float nextFloat() {
        return random.nextFloat();
    }

    @Override public int nextInt() {
        return random.nextInt();
    }

    @Override public int nextInt(int upper) {
        return random.nextInt(upper);
    }

    @Override public double nextGaussian() {
        return random.nextGaussian();
    }

    @Override public long nextLong() {
        return random.nextLong();
    }

    @Override public void setSeed(int seed) {
        random.setSeed(seed);
    }

    @Override public void setSeed(int[] seed) {
        random.setSeed(seed);
    }

    @Override public void setSeed(long seed) {
        random.setSeed(seed);
    }
}
