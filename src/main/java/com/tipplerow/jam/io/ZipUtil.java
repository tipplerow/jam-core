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

import com.tipplerow.jam.lang.JamException;
import com.tipplerow.jam.lang.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Provides utility methods for I/O operations on compressed files.
 */
public final class ZipUtil {
    /**
     * The suffix that identifies GZIP files.
     */
    public static final String GZIP_SUFFIX = ".gz";

    /**
     * Identifies valid GZIP files.
     *
     * @param file the file to test.
     *
     * @return {@code true} iff the file name ends with the GZIP
     * suffix.
     */
    public static boolean isGZipFile(File file) {
        return isGZipFileName(file.getName());
    }

    /**
     * Identifies valid names for GZIP files.
     *
     * @param fileName the file name to test.
     *
     * @return {@code true} iff the file name ends with the GZIP
     * suffix.
     */
    public static boolean isGZipFileName(String fileName) {
        return fileName.endsWith(GZIP_SUFFIX);
    }

    /**
     * Opens an input stream for a GZIP file.
     *
     * @param file the file to read.
     *
     * @return an input stream for the specified GZIP file.
     *
     * @throws RuntimeException unless the file is a valid GZIP file
     * and is open for reading.
     */
    public static InputStream openGZipInputStream(File file) {
        try {
            return new GZIPInputStream(new FileInputStream(file));
        }
        catch (IOException ioex) {
            throw JamException.runtime(ioex);
        }
    }

    /**
     * Opens an output stream for a GZIP file.
     *
     * @param file the file to write.
     *
     * @return an output stream for the specified GZIP file.
     *
     * @throws RuntimeException unless the file is a valid GZIP file
     * and is open for writing.
     */
    public static OutputStream openGZipOutputStream(File file) {
        try {
            return new GZIPOutputStream(new FileOutputStream(file));
        }
        catch (IOException ioex) {
            throw JamException.runtime(ioex);
        }
    }

    /**
     * Opens a reader for a GZIP file.
     *
     * @param file the file to read.
     *
     * @return a reader for the specified GZIP file.
     *
     * @throws RuntimeException unless the file is a valid GZIP file
     * and is open for reading.
     */
    public static BufferedReader openGZipReader(File file) {
        return new BufferedReader(new InputStreamReader(openGZipInputStream(file)));
    }

    /**
     * Opens a writer for a GZIP file and creates any subdirectories
     * in the file path that do not already exist; if the file already
     * exists its contents will be deleted.
     *
     * @param file the file to write.
     *
     * @return a writer for the specified GZIP file.
     *
     * @throws RuntimeException unless the file is a valid GZIP file
     * and is open for writing.
     */
    public static PrintWriter openGZipWriter(File file) {
        if (!isGZipFile(file))
            throw JamException.runtime("File [%s] is not a GZIP file.");

        FileUtil.ensureParentDirs(file);
        return new PrintWriter(openGZipOutputStream(file));
    }

    /**
     * Removes the {@code .gz} suffix from a file name (if present).
     *
     * @param fileName the file name to strip.
     *
     * @return the file name with the suffix removed (or the original
     * file name if the suffix was not present).
     */
    public static String removeSuffix(String fileName) {
        return StringUtil.removeSuffix(fileName, GZIP_SUFFIX);
    }
}
