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
package com.tipplerow.jam.io;

import com.tipplerow.jam.lang.StringUtil;

import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Provides for {@link java.lang.Iterable} file reading, line by line,
 * with all single-line comment text and blank lines excluded and
 * continuation lines joined together.
 *
 * <p>A continuation line ends with a backslash character {@code '\'}.
 * All continuation lines will be appended to the previous line (after
 * adding a leading space character).
 */
public final class DataReader implements Closeable, Iterable<String> {
    private final LineReader reader;
    private final Pattern comment;
    private String nextLine;

    private static final String CONTINUATION = "\\";

    private DataReader(LineReader reader, Pattern comment) {
        this.reader = reader;
        this.comment = comment;
        advance();
    }

    private void advance() {
        nextLine = StringUtil.EMPTY;

        while (reader.hasNext()) {
            String line = reader.next();

            line = StringUtil.removeCommentText(line, comment);
            line = line.trim();

            if (line.isEmpty())
                continue;

            if (isContinuationLine(line)) {
                nextLine = nextLine + formatContinuation(line);
                continue;
            }

            nextLine = nextLine + line;
            break;
        }
    }

    private static boolean isContinuationLine(String line) {
        return line.endsWith(CONTINUATION);
    }

    private static String formatContinuation(String line) {
        //
        // Just need to remove the continuation character...
        //
        return StringUtil.chop(line);
    }

    /**
     * Creates a new reader for a specified file.
     *
     * @param file the file to read.
     *
     * @param comment the pattern that identifies the start of a
     * single-line or inline comment.
     *
     * @return the data reader for the specified file.
     *
     * @throws RuntimeException if the file cannot be opened for
     * reading.
     */
    public static DataReader open(File file, Pattern comment) {
        return new DataReader(LineReader.open(file), comment);
    }

    /**
     * Creates a new reader for a specified file.
     *
     * @param fileName the name of the file to read.
     *
     * @param comment the pattern that identifies the start of a
     * single-line or inline comment.
     *
     * @return the data reader for the specified file.
     *
     * @throws RuntimeException if the file cannot be opened for
     * reading.
     */
    public static DataReader open(String fileName, Pattern comment) {
        return open(new File(fileName), comment);
    }

    /**
     * Reads all data lines from a specified file.
     *
     * @param file the file to read.
     *
     * @param comment the pattern that identifies the start of a
     * single-line or inline comment.
     *
     * @return a list containing all data lines in the file.
     *
     * @throws RuntimeException if the file cannot be opened for
     * reading.
     */
    public static List<String> read(File file, Pattern comment) {
        DataReader reader = open(file, comment);
        List<String> lines = new ArrayList<>();

        try {
            for (String line : reader)
                lines.add(line);
        }
        finally {
            reader.close();
        }

        return lines;
    }

    /**
     * Reads all data lines from a specified file.
     *
     * @param fileName the name of the file to read.
     *
     * @param comment the pattern that identifies the start of a
     * single-line or inline comment.
     *
     * @return a list containing all data lines in the file.
     *
     * @throws RuntimeException if the file cannot be opened for
     * reading.
     */
    public static List<String> read(String fileName, Pattern comment) {
        return read(new File(fileName), comment);
    }

    /**
     * Closes the underlying reader.
     */
    @Override public void close() {
        reader.close();
    }

    /**
     * Indicates whether the reader has more lines.
     *
     * @return {@code true} iff there is another line to read.
     */
    public boolean hasNext() {
        return !nextLine.isEmpty();
    }


    /**
     * Returns the next <em>data</em> line (stripped of comment text
     * and skipping blank lines) in the file.
     *
     * @return the next <em>data</em> line in the file.
     *
     * @throws NoSuchElementException if the reader has reached the
     * end of the file.
     */
    public String next() {
        if (!hasNext())
            throw new NoSuchElementException();

        String result = nextLine;
        advance();

        return result;
    }

    @Override public Iterator<String> iterator() {
        return new MyIterator(this);
    }

    private static class MyIterator implements Iterator<String> {
        private final DataReader reader;

        private MyIterator(DataReader reader) {
            this.reader = reader;
        }

        @Override public boolean hasNext() {
            return reader.hasNext();
        }

        @Override public String next() {
            return reader.next();
        }
    }
}
