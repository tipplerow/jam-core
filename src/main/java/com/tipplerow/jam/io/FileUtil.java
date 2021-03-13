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
import com.tipplerow.jam.regex.RegexUtil;

import java.io.File;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides utility methods for operating on files and file names.
 *
 * <p>All methods wrap checked exceptions ({@code IOException}s) in
 * runtime exceptions.
 */
@Slf4j
public final class FileUtil {
    private FileUtil() {}

    /**
     * Creates a required directory (including all nonexistent parent
     * directories) if it does not already exist.
     *
     * @param dir the required directory.
     *
     * @throws RuntimeException if the input directory does not exist
     * and cannot be created, or if the {@code dir} path does exist
     * but is not a directory.
     */
    public static void ensureDir(File dir) {
        if (!dir.exists()) {
            boolean success = dir.mkdirs();

            if (success) {
                log.info("Created directory [%s]...", dir.getAbsolutePath());
            }
            else {
                throw JamException.runtime("Failed to create directory [%s].", dir.getAbsolutePath());
            }
        }
        else if (!dir.isDirectory()) {
            throw JamException.runtime("Path [%s] exists but is not a directory.", dir.getAbsolutePath());
        }
    }

    /**
     * Creates a required directory (including all nonexistent parent
     * directories) if it does not already exist.
     *
     * @param dirName the name of the required directory.
     *
     * @throws RuntimeException if the input directory does not exist
     * and cannot be created, or if the {@code dir} path does exist
     * but is not a directory.
     */
    public static void ensureDir(String dirName) {
        ensureDir(new File(dirName));
    }

    /**
     * Creates the parent directories of a file if they do not already
     * exist (e.g., before attempting to open a new file for writing).
     *
     * @param file the file of interest.
     *
     * @throws RuntimeException if the parent directories did not
     * already exist and could not be created.
     */
    public static void ensureParentDirs(File file) {
        ensureDir(FileUtil.getParentFile(file));
    }

    /**
     * Determines whether a file exists.
     *
     * @param fileName the name of the file to check.
     *
     * @return {@code true} iff a file with the specified name exists.
     */
    public static boolean exists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * Returns the basename of a file: the last name in the path
     * sequence.
     *
     * @param file the file of interest.
     *
     * @return the basename of the specified file.
     */
    public static String getBaseName(File file) {
        return file.getName();
    }

    /**
     * Returns the prefix of the basename of a file: the substring of
     * the basename before the first dot.
     *
     * @param file the file of interest.
     *
     * @return the basename prefix of the specified file.
     */
    public static String getBaseNamePrefix(File file) {
        return getPrefix(getBaseName(file));
    }

    private static String getPrefix(String fileName) {
        return RegexUtil.DOT.split(fileName)[0];
    }

    /**
     * Returns the canonical form of the specified file but never
     * throws checked exceptions.
     *
     * @param file a file to examine.
     *
     * @return the canonical form of the specified file.
     *
     * @throws RuntimeException if {@code file.getCanonicalPath}
     * throws an {@code IOException}.
     */
    public static File getCanonicalFile(File file) {
        return new File(getCanonicalPath(file));
    }

    /**
     * Returns the canonical path of the specified file but never
     * throws checked exceptions.
     *
     * @param file a file to examine.
     *
     * @return the canonical path of the specified file.
     *
     * @throws RuntimeException if {@code file.getCanonicalPath}
     * throws an {@code IOException}.
     */
    public static String getCanonicalPath(File file) {
        String result = null;

        try {
            result = file.getCanonicalPath();
        }
        catch (IOException ioex) {
            throw JamException.runtime(ioex);
        }

        return result;
    }

    /**
     * Returns the canonical <em>prefix</em> of the specified file
     * (everything before the first dot in the canonical path) but
     * never throws checked exceptions.
     *
     * @param file a file to examine.
     *
     * @return the canonical prefix of the specified file.
     *
     * @throws RuntimeException if {@code file.getCanonicalPath}
     * throws an {@code IOException}.
     */
    public static String getCanonicalPrefix(File file) {
        return getPrefix(getCanonicalPath(file));
    }

    /**
     * Returns the name of the parent directory of a file, or
     * {@code .} (dot) if the file does not specify a parent.
     *
     * <p>Unlike the method {@code java.io.File.getParent}, this
     * method never returns {@code null}.
     *
     * @param file a file to examine.
     *
     * @return the name of the parent directory of the specified file,
     * or {@code .} (dot) if the file does not specify a parent.
     */
    public static String getDirName(File file) {
	String result = file.getParent();

	if (result == null)
	    result = ".";

	return result;
    }

    /**
     * Returns the parent directory of a file, or the "dot" directory
     * if the file does not specify a parent.
     *
     * <p>Unlike the method {@code java.io.File.getParentFile}, this
     * method never returns {@code null}.
     *
     * @param file a file to examine.
     *
     * @return the parent directory of the specified file, or the
     * "dot" directory if the file does not specify a parent.
     */
    public static File getParentFile(File file) {
	return new File(getDirName(file));
    }

    /**
     * Identifies canonical files.
     *
     * @param file a file to examine.
     *
     * @return {@code true} iff the specified file has a canonical
     * path name.
     */
    public static boolean isCanonicalFile(File file) {
        return file.equals(getCanonicalFile(file));
    }

    /**
     * Builds a composite file name by joining components with the
     * system-dependent name separator.
     *
     * @param dirName the first directory in the composite path.
     *
     * @param pathNames the subdirectories and final base name in
     * the composite path.
     *
     * @return the composite file name.
     */
    public static String join(String dirName, String... pathNames) {
        StringBuilder builder = new StringBuilder(dirName);

        for (String pathName : pathNames) {
            builder.append(File.separator);
            builder.append(pathName);
        }

        return builder.toString();
    }

    /**
     * Creates a new file by building a composite file name.
     *
     * @param dirName the first directory in the composite path.
     *
     * @param pathNames the subdirectories and final base name in
     * the composite path.
     *
     * @return a new file with the composite file name.
     */
    public static File newFile(String dirName, String... pathNames) {
        return new File(join(dirName, pathNames));
    }

    /**
     * Asserts that a file exists.
     *
     * @param fileName the name of the required file.
     *
     * @throws RuntimeException if the file does not exist.
     */
    public static void requireFile(String fileName) {
        requireFile(new File(fileName));
    }

    /**
     * Asserts that a file exists.
     *
     * @param file the required file.
     *
     * @throws RuntimeException if the file does not exist.
     */
    public static void requireFile(File file) {
        if (!file.exists())
            throw JamException.runtime("File [%s] does not exist.", getCanonicalPath(file));
    }
}
