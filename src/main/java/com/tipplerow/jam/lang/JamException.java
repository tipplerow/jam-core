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

import com.tipplerow.jam.io.IOUtil;

import java.io.PrintWriter;

/**
 * Encapsulates exception management for the JAM library.
 */
public final class JamException {
    private static PrintWriter logWriter = null;

    /**
     * Name of the system property that defines a log file where
     * exceptions will be recorded.
     */
    public static final String EXCEPTION_LOG_PROPERTY = "jam.lang.exceptionLog";

    /**
     * Default value for the exceptions log.
     */
    public static final String EXCEPTION_LOG_DEFAULT = "jam-exceptions.log";

    /**
     * Safely closes the exception log.
     *
     * <p>This method is typically called at the end of a command-line
     * application that is logging error messages.
     */
    public static void closeLog() {
        IOUtil.close(logWriter);
        logWriter = null;
    }

    /**
     * Writes an error message to the exception log file, whose
     * name is specified by the {@code EXCEPTION_LOG_PROPERTY}
     * system property.
     *
     * @param message the error message.
     *
     * @return a new exception containing the error message.
     */
    public static RuntimeException log(String message) {
        PrintWriter writer = getLogWriter();

        writer.println(message);
        writer.flush();

        return new RuntimeException(message);
    }

    /**
     * Writes an error message to the exception log file, whose
     * name is specified by the {@code EXCEPTION_LOG_PROPERTY}
     * system property.
     *
     * @param message the error message.
     *
     * @param cause the underlying error condition.
     *
     * @return a new exception containing the formatted error message
     * and underlying cause.
     */
    public static RuntimeException log(String message, Throwable cause) {
        PrintWriter writer = getLogWriter();

        writer.println(message + "[" + cause.getMessage() + "]");
        writer.flush();

        return new RuntimeException(message, cause);
    }

    private static PrintWriter getLogWriter() {
        if (logWriter == null)
            logWriter = IOUtil.openWriter(resolveExceptionFile());

        return logWriter;
    }

    private static String resolveExceptionFile() {
        return JamProperties.getOptional(EXCEPTION_LOG_PROPERTY, EXCEPTION_LOG_DEFAULT);
    }

    /**
     * Wraps another exception in a new runtime exception.
     *
     * @param ex the exeception to wrap.
     *
     * @return the new exception.
     */
    public static RuntimeException runtime(Exception ex) {
        return new RuntimeException(ex);
    }

    /**
     * Creates a new runtime exception with a formatted message.
     *
     * @param format message format string.
     *
     * @param args message format arguments.
     *
     * @return the new exception.
     */
    public static RuntimeException runtime(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }
}
