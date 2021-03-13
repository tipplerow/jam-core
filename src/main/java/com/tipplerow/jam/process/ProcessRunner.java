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
package com.tipplerow.jam.process;

import com.tipplerow.jam.io.IOUtil;
import com.tipplerow.jam.lang.JamException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Runs command-line processes.
 *
 * @author Scott Shaffer
 */
public final class ProcessRunner {
    /**
     * Runs a command-line process.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the console output from the process.
     */
    public static List<String> run(String... command) {
        return run(List.of(command));
    }

    /**
     * Runs a command-line process.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the console output from the process.
     */
    public static List<String> run(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);

        try {
            Process process = builder.start();
            BufferedReader reader = IOUtil.openReader(process.getInputStream());

            return IOUtil.readLines(reader);
        }
        catch (IOException ex) {
            throw JamException.runtime(ex);
        }
    }
}
