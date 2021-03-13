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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.tipplerow.jam.app.JamLogger;

import org.apache.commons.io.LineIterator;

/**
 * Provides for {@code Iterable} file reading, line by line.
 */
public final class LineReader implements Closeable, Iterable<String>, Iterator<String> {
    private final LineIterator iterator;

    private LineReader(LineIterator iterator) {
        this.iterator = iterator;
    }

    private LineReader(BufferedReader reader) {
        this(new LineIterator(reader));
    }

    /**
     * Creates a new reader for a specified file.
     *
     * @param file the file to read.
     *
     * @return the line reader for the specified file.
     *
     * @throws RuntimeException if the file cannot be opened for
     * reading.
     */
    public static LineReader open(File file) {
        JamLogger.info(String.format("Opening LineReader(%s)...", file));
        return new LineReader(IOUtil.openReader(file));
    }

    /**
     * Creates a new reader for a specified file.
     *
     * @param fileName the name of the file to read.
     *
     * @return the line reader for the specified file.
     *
     * @throws RuntimeException if the file cannot be opened for reading.
     */
    public static LineReader open(String fileName) {
        return open(new File(fileName));
    }

    /**
     * Closes the underlying reader.
     */
    @Override
    public void close() {
        IOUtil.close(iterator);
    }

    /**
     * Indicates whether the reader has more lines.
     *
     * @return {@code true} iff there is another line to read.
     */
    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next line in the file.
     *
     * @return the next line in the file.
     *
     * @throws NoSuchElementException if the reader has reached the
     * end of the file.
     */
    @Override
    public String next() {
        return iterator.next();
    }

    /**
     * Throws an {@code UnsupportedOperationException} as removal is
     * not supported.
     *
     * @throws UnsupportedOperationException always.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }
}
