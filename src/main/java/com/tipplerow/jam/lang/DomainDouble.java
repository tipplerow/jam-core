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

import java.text.DecimalFormat;
import java.util.List;

import com.tipplerow.jam.math.DoubleComparator;
import com.tipplerow.jam.math.DoubleRange;
import com.tipplerow.jam.vector.VectorView;

/**
 * Represents floating-point quantities that have a specific meaning
 * in a particular problem domain and have a well-defined range of
 * valid values.
 *
 * <p>All {@code DomainDouble} objects are validated at the time of
 * creation. They are immutable, so they are always contain a valid
 * value.
 *
 * <p>All {@code DomainDouble} objects are expected to be scaled so
 * that differences below {@code 1.0E-12} can be ignored. Tests for 
 * exact equality can then be made by the {@code DoubleComparator}.
 *
 * @author Scott Shaffer
 */
public abstract class DomainDouble implements Formatted {
    private final double value;

    protected DomainDouble(double value, DoubleRange range) {
        range.validate(value);
        this.value = value;
    }

    /**
     * Compares two {@code DomainDouble} objects numerically, allowing
     * for the default floating-point tolerance.
     *
     * <p>This method should be used to implement {@code compareTo} in
     * the subclasses; it is declared as {@code protected} to disallow
     * comparisons of difference subclasses.
     *
     * @param d1 the first {@code DomainDouble} to be compared.
     *
     * @param d2 the second {@code DomainDouble} to be compared.
     *
     * @return an integer less than, equal to, or greater than zero
     * according to whether the underlying double value in {@code d1}
     * is less than, equal to, or greater than 
     */
    protected static int compare(DomainDouble d1, DomainDouble d2) {
        return DoubleComparator.DEFAULT.compare(d1.value, d2.value);
    }

    /**
     * Returns a vector view of the underlying double values from a
     * {@code DomainDouble} array.
     *
     * @param domainDoubles the array to view.
     *
     * @return a vector view of the underlying double values.
     */
    public static VectorView asVectorView(DomainDouble... domainDoubles) {
        return VectorView.of(doubleValues(domainDoubles));
    }

    /**
     * Returns the underlying double value.
     *
     * @return the underlying double value.
     */
    public final double doubleValue() {
        return value;
    }

    /**
     * Returns the underlying double values from a {@code DomainDouble} array.
     *
     * @param domainDoubles the array to convert.
     *
     * @return an array in which element {@code k} contains the underlying
     * double value from the {@code DomainDouble} in element {@code k} of
     * the input array.
     */
    public static double[] doubleValues(DomainDouble... domainDoubles) {
        double[] values = new double[domainDoubles.length];

        for (int index = 0; index < domainDoubles.length; index++)
            values[index] = domainDoubles[index].value;
        
        return values;
    }

    /**
     * Finds the maximum value in a sequence of {@code DomainDouble}
     * values.
     *
     * @param values the objects to examine.
     *
     * @return an object having the maximum value in the input
     * sequence.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DomainDouble> T max(T... values) {
        return max(List.of(values));
    }

    /**
     * Finds the maximum value in a sequence of {@code DomainDouble}
     * values.
     *
     * @param values the objects to examine.
     *
     * @return an object having the maximum value in the input
     * sequence.
     */
    public static <T extends DomainDouble> T max(Iterable<T> values) {
        T result = null;

        for (T value : values)
            if (result == null || value.doubleValue() > result.doubleValue())
                result = value;

        return result;
    }

    /**
     * Finds the minimum value in a sequence of {@code DomainDouble}
     * values.
     *
     * @param values the objects to examine.
     *
     * @return an object having the minimum value in the input
     * sequence.
     */
    @SuppressWarnings("unchecked")
    public static <T extends DomainDouble> T min(T... values) {
        return min(List.of(values));
    }

    /**
     * Finds the minimum value in a sequence of {@code DomainDouble}
     * values.
     *
     * @param values the objects to examine.
     *
     * @return an object having the minimum value in the input
     * sequence.
     */
    public static <T extends DomainDouble> T min(Iterable<T> values) {
        T result = null;

        for (T value : values)
            if (result == null || value.doubleValue() < result.doubleValue())
                result = value;

        return result;
    }

    /**
     * Executes a less-than test.
     *
     * @param value the value to test.
     *
     * @return {@code true} iff the value of this object is less than
     * the input value by an amount greater than the default tolerance
     * defined by the {@code DoubleComparator} class.
     */
    public boolean LT(double value) {
        return DoubleComparator.DEFAULT.LT(this.value, value);
    }

    /**
     * Executes a less-than test.
     *
     * @param that the value to test.
     *
     * @return {@code true} iff the value of this object is less than
     * the input value by an amount greater than the default tolerance
     * defined by the {@code DoubleComparator} class.
     *
     * @throws RuntimeException unless the input value is a member of
     * the same class as this value.
     */
    public boolean LT(DomainDouble that) {
        validateClass(that);
        return LT(that.value);
    }

    private void validateClass(DomainDouble that) {
        if (!this.getClass().equals(that.getClass()))
            throw JamException.runtime("Runtime class mismatch.");
    }

    /**
     * Executes a less-than-or-equal-to test.
     *
     * @param value the value to test.
     *
     * @return {@code true} iff the value of this object is less than
     * or equal to the input value within the default tolerance in the
     * {@code DoubleComparator} class.
     */
    public boolean LE(double value) {
        return DoubleComparator.DEFAULT.LE(this.value, value);
    }

    /**
     * Executes a less-than-or-equal-to test.
     *
     * @param that the value to test.
     *
     * @return {@code true} iff the value of this object is less than
     * or equal to the input value within the default tolerance in the
     * {@code DoubleComparator} class.
     *
     * @throws RuntimeException unless the input value is a member of
     * the same class as this value.
     */
    public boolean LE(DomainDouble that) {
        validateClass(that);
        return LE(that.value);
    }

    /**
     * Executes a greater-than-or-equal-to test.
     *
     * @param value the value to test.
     *
     * @return {@code true} iff the value of this object is greater
     * than or equal to the input value within the default tolerance
     * defined by the {@code DoubleComparator} class.
     */
    public boolean GE(double value) {
        return DoubleComparator.DEFAULT.GE(this.value, value);
    }

    /**
     * Executes a greater-than test.
     *
     * @param that the value to test.
     *
     * @return {@code true} iff the value of this object is greater
     * than or equal to the input value within the default tolerance
     * defined by the {@code DoubleComparator} class.
     *
     * @throws RuntimeException unless the input value is a member of
     * the same class as this value.
     */
    public boolean GE(DomainDouble that) {
        validateClass(that);
        return GE(that.value);
    }

    /**
     * Executes a greater-than test.
     *
     * @param value the value to test.
     *
     * @return {@code true} iff the value of this object is greater than
     * the input value by an amount greater than the default tolerance
     * defined by the {@code DoubleComparator} class.
     */
    public boolean GT(double value) {
        return DoubleComparator.DEFAULT.GT(this.value, value);
    }

    /**
     * Executes a greater-than test.
     *
     * @param that the value to test.
     *
     * @return {@code true} iff the value of this object is greater than
     * the input value by an amount greater than the default tolerance
     * defined by the {@code DoubleComparator} class.
     *
     * @throws RuntimeException unless the input value is a member of
     * the same class as this value.
     */
    public boolean GT(DomainDouble that) {
        validateClass(that);
        return GT(that.value);
    }

    /**
     * Tests for equality with a bare {@code double} value.
     *
     * @param value the value to test.
     *
     * @return {@code true} iff the value of this object is equal to
     * the input value within the default tolerance defined by the
     * {@code DoubleComparator} class.
     */
    public boolean equals(double value) {
        return DoubleComparator.DEFAULT.equals(this.value, value);
    }

    /**
     * Tests for equality with a bare {@code double} value.
     *
     * @param value the value to test.
     *
     * @param tolerance the floating-point tolerance.
     *
     * @return {@code true} iff the value of this object is equal to
     * the input value within the specified tolerance.
     */
    public boolean equals(double value, double tolerance) {
        return DoubleComparator.equals(this.value, value, tolerance);
    }

    @Override public boolean equals(Object that) {
        return this.getClass().equals(that.getClass()) && equalsDomainDouble((DomainDouble) that);
    }

    private boolean equalsDomainDouble(DomainDouble that) {
        return equals(that.value);
    }

    /**
     * Formats this value as a string.
     *
     * @param fmt the format representation.
     *
     * @return this value formatted as described by {@code fmt}.
     */
    public String format(DecimalFormat fmt) {
        return fmt.format(value);
    }

    /**
     * Formats this value as a string.
     *
     * @param fmt the {@code String.format} representation.
     *
     * @return this value formatted as described by {@code fmt}.
     */
    public String format(String fmt) {
        return String.format(fmt, value);
    }

    /**
     * Identifies negative values.
     *
     * @return {@code true} iff this value is negative allowing for
     * the default {@code DoubleComparator} tolerance (is less than
     * the tolerance).
     */
    public boolean isNegative() {
        return DoubleComparator.DEFAULT.isNegative(value);
    }

    /**
     * Identifies positive values.
     *
     * @return {@code true} iff this value is positive allowing for
     * the default {@code DoubleComparator} tolerance (greater than
     * the tolerance).
     */
    public boolean isPositive() {
        return DoubleComparator.DEFAULT.isPositive(value);
    }

    /**
     * Identifies (nearly) zero values.
     *
     * @return {@code true} iff this value is equal to zero when
     * allowing for the default {@code DoubleComparator} tolerance
     * (is less than the tolerance in absolute value).
     */
    public boolean isZero() {
        return DoubleComparator.DEFAULT.isZero(value);
    }

    /**
     * Returns the sign of this value.
     *
     * @return {@code -1} if this is a negative value (in excess of
     * the default comparison tolerance), {@code 0} if this value is
     * equal to zero (within the default comparison tolerance), or
     * {@code +1} if this value is positive (in excess of the default
     * comparison tolerance).
     */
    public int sign() {
        return DoubleComparator.DEFAULT.sign(value);
    }

    @Override public String format() {
        return Double.toString(value);
    }

    /**
     * Forbids the use of {@code DomainDouble} objects as hash keys.
     *
     * <p>As floating-point quantities, {@code DomainDouble} objects
     * are not appropriate as hash keys, but the general contract for
     * {@link Object#hashCode} requires that we override this method
     * since we have implemented {@link DomainDouble#equals}.  There
     * is no straightforward way to generate equal hash codes for all
     * pairs of {@code DomainDouble} objects that are equal according
     * to {@link DomainDouble#equals}, so we will throw an exception
     * to prohibit the use of {@code DomainDouble} objects as hash
     * keys.
     *
     * @throws UnsupportedOperationException for all invocations.
     */
    @Override public int hashCode() {
        throw new UnsupportedOperationException("Cannot use DomainDouble objects as hash keys.");
    }

    @Override public String toString() {
        return debug();
    }
}
