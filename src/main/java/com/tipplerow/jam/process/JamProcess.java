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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.tipplerow.jam.app.JamLogger;
import com.tipplerow.jam.io.IOUtil;
import com.tipplerow.jam.lang.JamException;

/**
 * Executes a command-line process and stores the results.
 *
 * @author Scott Shaffer
 */
public final class JamProcess {
    private final ProcessBuilder builder;

    private Process process;
    private List<String> stdout;
    private List<String> stderr;

    private JamProcess(List<String> command) {
        this.builder = new ProcessBuilder(command);
    }

    /**
     * Creates a command-line process (but does not yet execute it).
     *
     * <p>To execute the process, call the {@code run()} command on
     * the returned object.  This two-stage approach is required when
     * the runtime environment must be customized before the command
     * is executed.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the staged process object.
     */
    public static JamProcess create(String... command) {
        return create(List.of(command));
    }

    /**
     * Creates a command-line process (but does not yet execute it).
     *
     * <p>To execute the process, call the {@code run()} command on
     * the returned object.  This two-stage approach is required when
     * the runtime environment must be customized before the command
     * is executed.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the staged process object.
     */
    public static JamProcess create(List<String> command) {
        return new JamProcess(command);
    }

    /**
     * Runs a command-line process and logs console error messages.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the process object which holds the standard output
     * standard error lines.
     */
    public static JamProcess run(String... command) {
        return run(List.of(command));
    }

    /**
     * Runs a command-line process and logs console error messages.
     *
     * @param command the command-line program and its arguments.
     *
     * @return the process object which holds the standard output
     * standard error lines.
     */
    public static JamProcess run(List<String> command) {
        JamProcess process = new JamProcess(command);

        process.run();
        return process;
    }

    /**
     * Determines whether this process has been executed yet.
     *
     * @return {@code true} iff this process has been executed.
     */
    public boolean executed() {
        return process != null;
    }

    /**
     * Runs the process and logs console error messages.  Console
     * output to the {@code stdout} stream is available by calling
     * the {@code stdout()} method.
     *
     * @return the best guess as to the success or failure of the
     * process (whether the process wrote any error messages to the
     * {@code stderr} stream).
     */
    public boolean run() {
        JamLogger.info("Running system command: %s", builder.command());

        try {
            process = builder.start();

            BufferedReader stdoutReader = IOUtil.openReader(process.getInputStream());
            BufferedReader stderrReader = IOUtil.openReader(process.getErrorStream());

            stdout = IOUtil.readLines(stdoutReader);
            stderr = IOUtil.readLines(stderrReader);

            for (String error : stderr)
                JamLogger.error(error);
        }
        catch (IOException ex) {
            throw JamException.runtime(ex);
        }

        return success();
    }

    /**
     * Assigns an environment variable for the executing shell.
     *
     * @param name the name of the environment variable to assign.
     *
     * @param value the value of the environment variable to assign.
     */
    public void setenv(String name, String value) {
        builder.environment().put(name, value);
    }

    /**
     * Returns the console output written to the {@code stderr} stream.
     *
     * @return the console output written to the {@code stderr} stream.
     *
     * @throws IllegalStateException unless the process has been executed.
     */
    public List<String> stderr() {
        ensureExecuted();
        return Collections.unmodifiableList(stderr);
    }

    private void ensureExecuted() {
        if (!executed())
            throw new IllegalStateException("The process has not been executed.");
    }

    /**
     * Returns the console output written to the {@code stdout} stream.
     *
     * @return the console output written to the {@code stdout} stream.
     *
     * @throws IllegalStateException unless the process has been executed.
     */
    public List<String> stdout() {
        ensureExecuted();
        return Collections.unmodifiableList(stdout);
    }

    /**
     * Infers the success or failure of the command by checking for
     * data on the {@code stderr} stream.
     *
     * @return {@code true} iff the {@code stderr} output stream is
     * empty.
     *
     * @throws IllegalStateException unless the process has been executed.
     */
    public boolean success() {
        ensureExecuted();
        return stderr.isEmpty();
    }
}
