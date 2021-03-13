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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import com.tipplerow.jam.io.Delimiter;

/**
 * Provides static utility methods operating on integers.
 */
public final class IntUtil {
    /**
     * Counts the number of occurrences for each integer in a
     * sequence.
     *
     * @param values the integer sequence to process.
     *
     * @return a new {@code Multiset} containing the number of
     * occurrences for each integer in the input sequence.
     */
    public static Multiset<Integer> count(int... values) {
        Multiset<Integer> counts = HashMultiset.create();

        for (int value : values)
            counts.add(value);

        return counts;
    }

    /**
     * Counts the number of occurrences for each integer in a
     * collection.
     *
     * @param values the integer collection to process.
     *
     * @return a new {@code Multiset} containing the number of
     * occurrences for each integer in the input collection.
     */
    public static Multiset<Integer> count(Collection<Integer> values) {
        return HashMultiset.create(values);
    }

    /**
     * Computes the integer cube root of a perfect integer cube.
     *
     * @param cube the perfect integer cube.
     *
     * @return the integer cube root of the input argument (which must
     * be a perfect integer cube).
     *
     * @throws IllegalArgumentException unless the input arguent is a
     * perfect integer cube.
     */
    public static int cbrt(int cube) {
        int root = (int) Math.round(Math.cbrt(cube));

        if (root * root * root == cube)
            return root;
        else
            throw new IllegalArgumentException(String.format("Integer [%d] is not a perfect cube.", cube));
    }

    /**
     * Identifies integers that are perfect cubes.
     *
     * @param k the integer to examine.
     *
     * @return {@code true} iff the input value is a perfect cube.
     */
    public static boolean isCube(int k) {
        int root = (int) Math.round(Math.cbrt(k));
        return root * root * root == k;
    }

    /**
     * Determines whether one integer is evenly divisible by another.
     *
     * @param numer the numerator to examine.
     *
     * @param denom the denominator to examine.
     *
     * @return {@code true} iff the numerator is evenly divisible by
     * the denominator (has no remainder).
     */
    public static boolean isDivisible(int numer, int denom) {
        return numer % denom == 0;
    }

    /**
     * Identifies integers that are perfect squares.
     *
     * @param k the integer to examine.
     *
     * @return {@code true} iff the input value is a perfect square.
     */
    public static boolean isSquare(int k) {
        if (k < 0)
            return false;

        int root = (int) Math.round(Math.sqrt(k));
        return root * root == k;
    }

    /**
     * Finds the maximum value in a sequence of integers.
     *
     * @param elements the elements to examine.
     *
     * @return the maximum value in the input sequence.
     *
     * @throws IllegalArgumentException unless the sequence contains
     * at least one element.
     */
    public static int max(int... elements) {
        if (elements.length < 1)
            throw new IllegalArgumentException("At least one element is required.");

        int result = Integer.MIN_VALUE;

        for (int element : elements)
            result = Math.max(result, element);

        return result;
    }

    /**
     * Finds the maximum value in a list of integers.
     *
     * @param elements the elements to examine.
     *
     * @return the maximum value in the input list.
     *
     * @throws IllegalArgumentException unless the list contains
     * at least one element.
     */
    public static int max(List<Integer> elements) {
        if (elements.isEmpty())
            throw new IllegalArgumentException("At least one element is required.");

        int result = Integer.MIN_VALUE;

        for (int element : elements)
            result = Math.max(result, element);

        return result;
    }

    /**
     * Finds the minimum value in a sequence of integers.
     *
     * @param elements the elements to examine.
     *
     * @return the minimum value in the input sequence.
     *
     * @throws IllegalArgumentException unless the sequence contains
     * at least one element.
     */
    public static int min(int... elements) {
        if (elements.length < 1)
            throw new IllegalArgumentException("At least one element is required.");

        int result = Integer.MAX_VALUE;

        for (int element : elements)
            result = Math.min(result, element);

        return result;
    }

    /**
     * Finds the minimum value in a list of integers.
     *
     * @param elements the elements to examine.
     *
     * @return the minimum value in the input list.
     *
     * @throws IllegalArgumentException unless the list contains
     * at least one element.
     */
    public static int min(List<Integer> elements) {
        if (elements.isEmpty())
            throw new IllegalArgumentException("At least one element is required.");

        int result = Integer.MAX_VALUE;

        for (int element : elements)
            result = Math.min(result, element);

        return result;
    }

    /**
     * Parses a string representation of an integer value.
     *
     * <p>In addition to all formats accepted by the built-in 
     * {@code Integer.parseInt}, this method supports scientific
     * notation (e.g., {@code 1.23E9}) provided that the value is
     * an exact integer value (has no fractional part).
     *
     * @param string the string to parse.
     *
     * @return the integer value represented by the given string.
     *
     * @throws RuntimeException unless the string is properly
     * formatted.
     */
    public static int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            double result = DoubleUtil.parseDouble(string);

            if (DoubleUtil.isInt(result))
                return (int) result;
            else
                throw ex;
        }
    }

    /**
     * Parses a delimited string of integer values.
     *
     * @param string the delimited string to parse.
     *
     * @param delimiter the element delimiter.
     *
     * @return the integer array represented by the given string.
     *
     * @throws RuntimeException unless the string is properly
     * formatted.
     */
    public static int[] parseIntArray(String string, Delimiter delimiter) {
        String[] fields = delimiter.split(string);
        int[] elements = new int[fields.length];

        for (int index = 0; index < fields.length; index++)
            elements[index] = parseInt(fields[index].trim());

        return elements;
    }

    /**
     * Parses a delimited string of integer values.
     *
     * @param string the delimited string to parse.
     *
     * @param delimiter the element delimiter.
     *
     * @return the integer list represented by the given string.
     *
     * @throws RuntimeException unless the string is properly
     * formatted.
     */
    public static List<Integer> parseIntList(String string, Delimiter delimiter) {
        String[] fields = delimiter.split(string);
        List<Integer> elements = new ArrayList<Integer>(fields.length);

        for (String field : fields)
            elements.add(parseInt(field.trim()));

        return elements;
    }

    /**
     * Generates indexes required for a random sample of array or list
     * elements (using the global random number source).
     *
     * @param elementCount the number of elements to choose from (the
     * array length or list size).
     *
     * @param sampleSize the number of elements to choose.
     *
     * @return an array of length {@code sampleSize} containing the
     * indexes of the elements to choose.
     *
     * @throws IllegalArgumentException if the sample size is negative
     * or greater than the element count.
     */
    public static int[] sample(int elementCount, int sampleSize) {
        return sample(elementCount, sampleSize, JamRandom.global());
    }

    /**
     * Generates indexes required for a random sample of array or list
     * elements.
     *
     * @param elementCount the number of elements to choose from (the
     * array length or list size).
     *
     * @param sampleSize the number of elements to choose.
     *
     * @param random the random number source.
     *
     * @return an array of length {@code sampleSize} containing the
     * indexes of the elements to choose.
     *
     * @throws IllegalArgumentException if the sample size is negative
     * or greater than the element count.
     */
    public static int[] sample(int elementCount, int sampleSize, JamRandom random) {
        if (sampleSize < 0)
            throw new IllegalArgumentException("Sample size may not be negative.");

        if (sampleSize > elementCount)
            throw new IllegalArgumentException("Sample size may not exceed the element count.");

        int[] indexes = new int[elementCount];

        for (int k = 0; k < elementCount; ++k)
            indexes[k] = k;

        shuffle(indexes, random);
        return Arrays.copyOfRange(indexes, 0, sampleSize);
    }

    /**
     * Randomly reorders elements in an array (in place) using the
     * global random number source.
     *
     * @param elements the array to shuffle.
     */
    public static void shuffle(int[] elements) {
        shuffle(elements, JamRandom.global());
    }

    /**
     * Randomly reorders elements in an array (in place).
     *
     * @param elements the array to shuffle.
     *
     * @param random the random number source.
     */
    public static void shuffle(int[] elements, JamRandom random) {
        for (int k = elements.length - 1; k > 0; k--)
            swap(elements, k, random.nextInt(k));
    }

    /**
     * Computes the integer square root of a perfect integer square.
     *
     * @param square the perfect integer square.
     *
     * @return the integer square root of the input argument (which
     * must be a perfect integer square).
     *
     * @throws IllegalArgumentException unless the input arguent is a
     * perfect integer square.
     */
    public static int sqrt(int square) {
        int root = (int) Math.round(Math.sqrt(square));

        if (root * root == square)
            return root;
        else
            throw new IllegalArgumentException(String.format("Integer [%d] is not a perfect square.", square));
    }

    /**
     * Swaps two array elements.
     *
     * @param elements the array on which to operate.
     *
     * @param j the index of the first element to swap.
     *
     * @param k the index of the second element to swap.
     */
    public static void swap(int[] elements, int j, int k) {
        int tmp = elements[j];
        elements[j] = elements[k];
        elements[k] = tmp;
    }

    /**
     * Converts a collection of {@code Integer} objects into a bare array.
     *
     * @param collection the collection to convert.
     *
     * @return a new array filled with the integer values in the order
     * returned by the collection iterator.
     */
    public static int[] toArray(Collection<Integer> collection) {
        int index = 0;
        int[] result = new int[collection.size()];

        for (Integer value : collection)
            result[index++] = value.intValue();

        return result;
    }

    /**
     * Converts a sequence of integers into an array of doubles.
     *
     * @param ints the integers to convert.
     *
     * @return an array of {@code double} values converted from the
     * integers in the corresponding elements of the input array.
     */
    public static double[] toDouble(int... ints) {
        double[] doubles = new double[ints.length];

        for (int index = 0; index < ints.length; index++)
            doubles[index] = ints[index];

        return doubles;
    }

    /**
     * Converts a collection of integers into an array of doubles.
     *
     * @param ints the integers to convert.
     *
     * @return an array of {@code double} values converted from the
     * integers in the order returned by the collection iterator.
     */
    public static double[] toDouble(Collection<Integer> ints) {
        int index = 0;
        double[] doubles = new double[ints.size()];

        for (Integer value : ints)
            doubles[index++] = value.intValue();

        return doubles;
    }
}
