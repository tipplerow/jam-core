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
package com.tipplerow.jam.lang;

import com.tipplerow.jam.process.ProcessRunner;

import java.util.List;

/**
 * Provides information about the underlying operating system.
 *
 * @author Scott Shaffer
 */
public final class JamSystem {
    private static final String SYSCTL_CORE_COUNT = "machdep.cpu.core_count";
    private static final String SYSCTL_CPU_COUNT  = "hw.ncpu";

    /**
     * Environment variable that defines the number of CPU cores on
     * the machine.
     */
    public static final String CORE_COUNT_ENV = "JAM_CPU_CORE_COUNT";

    /**
     * Environment variable that defines the number of virtual CPUs
     * on the machine.
     */
    public static final String CPU_COUNT_ENV = "JAM_NCPU";

    /**
     * Returns the number of CPU cores on the machine.
     *
     * @return the number of CPU cores on the machine.
     */
    public static int getCoreCount() {
        if (JamEnv.isSet(CORE_COUNT_ENV))
            return Integer.parseInt(JamEnv.getRequired(CORE_COUNT_ENV));

        List<String> output = ProcessRunner.run("sysctl", "-n", SYSCTL_CORE_COUNT);

        if (output.size() != 1)
            throw JamException.runtime("Unexpected result from `sysctl`: [%s].", output);

        return Integer.parseInt(output.get(0));
    }

    /**
     * Returns the number of CPU cores on the machine.
     *
     * @return the number of CPU cores on the machine.
     */
    public static int getCPUCount() {
        if (JamEnv.isSet(CPU_COUNT_ENV))
            return Integer.parseInt(JamEnv.getRequired(CPU_COUNT_ENV));

        List<String> output = ProcessRunner.run("sysctl", "-n", SYSCTL_CPU_COUNT);

        if (output.size() != 1)
            throw JamException.runtime("Unexpected result from `sysctl`: [%s].", output);

        return Integer.parseInt(output.get(0));
    }

    /**
     * Determines whether the JVM is running on a Linux operating
     * system.
     *
     * @return {@code true} iff the JVM is running on a Linux
     * operating system.
     */
    public static boolean isLinux() {
        return uname().equals("Linux");
    }

    /**
     * Determines whether the JVM is running on a Mac OSX operating
     * system.
     *
     * @return {@code true} iff the JVM is running on a Mac OSX
     * operating system.
     */
    public static boolean isMacOS() {
        return uname().equals("Darwin");
    }

    /**
     * Returns the operating system name.
     *
     * @return the operating system name.
     */
    public static String uname() {
        return ProcessRunner.run("uname").get(0);
    }
}
